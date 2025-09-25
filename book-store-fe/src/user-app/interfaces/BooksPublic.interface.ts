import type { Category } from "./CategoryPublic.interface";


// export interface BooksPublic {
//     bookId: number;
//     bookTitle: string;
//     author: string;
//     description: string;
//     price: string;
//     stock: number;
//     category: Category;
// }

export interface BooksPublic {
    bookId: number;
    bookTitle: string;
    author: string;
    description: string;
    price: string;
    stock: number;
    bookImage: string;
    category: Category;
    quantity: number;
}

