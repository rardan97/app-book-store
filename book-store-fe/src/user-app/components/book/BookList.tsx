import { useNavigate } from "react-router";
import { useCart } from "../../context/CartProvider";
import { debounce } from "lodash";
import { useAuthUser } from "../../context/AuthProviderUser";
import { useBooks } from "../../context/BooksContext";
import type { BookCart } from "../../interfaces/BookCart.interface";
import { useMemo } from "react";



const BookList: React.FC = () => {

    const navigate = useNavigate();
    const { books } = useBooks();
    const { user } = useAuthUser();
    const { addToCart } = useCart();

     const debouncedAddToCart = useMemo(() => 
     debounce((book: BookCart) => {
        addToCart({
            bookId: book.bookId, 
            bookTitle: book.bookTitle, 
            price: book.price,
            bookImage: book.bookImage,
            bookImageFileName: book.bookImageFileName,
            quantity: 1 
        })
    }, 300), [addToCart]
    );

    const handleAddToCart = ((book: BookCart) => {
        if (!user) {
            navigate("/signin"); // arahkan ke halaman login
            return;
        }
        debouncedAddToCart(book); // jika sudah login, lanjutkan
    });

    const handleBuyDetail = ((bookId : number) => {    
        navigate("/buyNow", { state: {bookId} });
    });



    return (
        <section className="max-w-7xl mx-auto px-4 py-12 ">
        <h2 className="text-3xl font-bold text-gray-900 mb-8 text-center">Book List</h2>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8">
            {books.map((book) => (
            <div
                key={book.bookId}
                className="bg-gray-50 rounded-lg shadow-md hover:shadow-xl transition transform hover:scale-105 flex flex-col"
            >
                <img
                src={book.bookImage}
                alt={book.bookTitle}
                className="rounded-t-lg object-cover w-full h-64"
                loading="lazy"
                />
                <div className="p-5 flex flex-col flex-grow">
                <h3 className="text-lg font-semibold text-gray-900 mb-1">{book.bookTitle}</h3>
                <p className="text-sm text-gray-600 mb-4">by {book.author}</p>
                <div className="mt-auto flex items-center justify-between">
                    <span className="text-xl font-bold text-indigo-600">${book.price}</span>
                </div>
                <div className="flex space-x-2 justify-center md:justify-start">
                  <button
                    className="bg-indigo-600 text-white px-4 py-1.5 rounded-md hover:bg-indigo-700 transition flex-1 md:flex-none text-sm"
                    aria-label={`Buy`}
                    onClick={() => handleBuyDetail(book.bookId)}
                  >
                    Buy Now
                  </button>
                  <button
                    className="bg-green-600 text-white px-4 py-1.5 rounded-md hover:bg-green-700 transition flex-1 md:flex-none text-sm"
                    aria-label={`Add to Cart`}
                    onClick={() => handleAddToCart(book)}
                  >
                    Add Cart
                  </button>
                </div>
                </div>
            </div>
            ))}
        </div>
        </section>
    );
};

export default BookList;