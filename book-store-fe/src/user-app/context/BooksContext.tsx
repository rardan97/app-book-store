import { createContext, useContext, useState, type ReactNode, useEffect } from "react";
import { getLoadImagePublicBook, getPublicListBooks } from "../api/BookPublic";

interface Book {
    bookId: number;
    bookTitle: string;
    author: string;
    description: string;
    price: string;
    bookImage: string;     // untuk ditampilkan
    bookImageFileName: string, // baru, untuk dikirim ke server
    quantity: number;

}

interface BooksContextType {
  books: Book[];
  fetchBooks: () => Promise<void>;
}

const BooksContext = createContext<BooksContextType | undefined>(undefined);

export const BooksProvider = ({ children }: { children: ReactNode }) => {
  const [books, setBooks] = useState<Book[]>([]);

  const fetchBooks = async () => {
  // Revoke old blob URLs to prevent memory leaks
    books.forEach(book => {
        if (book.bookImage && book.bookImage.startsWith("blob:")) {
        URL.revokeObjectURL(book.bookImage);
        }
    });

  try {
    const response = await getPublicListBooks();
     if(response && response.data){

    const updatedBooks = await Promise.all(
      response.data.map(async (book) => {
        let imageUrl = "";

        console.log("Data Book Context : "+book.bookImage);

        if (typeof book.bookImage === "string" && book.bookImage.trim() !== "") {
            console.log("Data Book Context CC: "+book.bookImage);
          try {
            const resImage = await getLoadImagePublicBook(book.bookImage);
            imageUrl = URL.createObjectURL(resImage);
          } catch (err) {
            console.error(`Failed to load image for book ${book.bookTitle}`, err);
            imageUrl = ""; // fallback bisa pasang default image URL
          }
        }else{
             imageUrl = "/fallback-book.png";
        }

        return {
          ...book,
          bookImage: imageUrl,
          bookImageFileName: book.bookImage || "",
          quantity: 1,
        };
      })
    );

    setBooks(updatedBooks);
    }
  } catch (error) {
    console.error("Failed to fetch books", error);
  }
};

  useEffect(() => {
    fetchBooks();
  });

  return (
    <BooksContext.Provider value={{ books, fetchBooks }}>
      {children}
    </BooksContext.Provider>
  );
};

export const useBooks = () => {
  const context = useContext(BooksContext);
  if (!context) {
    throw new Error("useBooks must be used within a BooksProvider");
  }
  return context;
};