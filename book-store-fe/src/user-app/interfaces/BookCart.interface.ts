export interface BookCart {
    bookId: number;
    bookTitle: string;
    author: string;
    description: string;
    price: string;
    bookImage: string;
    bookImageFileName: string, // baru, untuk dikirim ke server
    quantity: number;
}
