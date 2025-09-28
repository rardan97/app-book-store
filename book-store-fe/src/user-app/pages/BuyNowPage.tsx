import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { useBooks } from "../context/BooksContext";
import type { CheckoutPayload } from "../interfaces/Checkout.interface";
import { checkoutTransaction } from "../api/Checkout";
import { useTransaction } from "../context/TransactionContext";

export interface Book {
    bookId: number;
    bookTitle: string;
    author: string;
    description: string;
    price: string;
    bookImage: string;
    quantity: number;
}


const BuyNowPage: React.FC = () => {
    const { books } = useBooks();
    const location = useLocation();
    const navigate = useNavigate();

    const [bookId, setBookId] = useState<number | null>(null);
    const [quantity, setQuantity] = useState<number>(1);

    const { setTransactionData } = useTransaction();

    useEffect(() => {
        window.scrollTo(0, 0);
    }, []);

    useEffect(() => {
        const state = location.state as { bookId: number } | undefined;
        if (state?.bookId) {
            setBookId(state.bookId);
        } else {
            navigate("/", { replace: true });
        }
    }, [location.state, navigate]);

    const book = books.find((b) => b.bookId === bookId);

    const handleQuantityChange = (amount: number) => {
        setQuantity((prev) => Math.max(prev + amount, 1));
    };

    

    const handleProceedToCheckout = async () => {
    // const handleProceedToCheckout = () => {
        if (!book) return;

        const payload: CheckoutPayload = {
            dataProductTransaksi: {
                transaksiId: 101,
                transaksiKode: "ORDER-20250719-001",
                transaksiTotal: "10000"
            },
            dataProductPembelian: [
                {
                productId: 211,
                productNama: "JavaScript for Beginners",
                productHarga: "10000",
                productQty: "1",
                productTotalHarga: "10000"
                }
            ]
            };


            try {
                const result = await checkoutTransaction(payload);
                setTransactionData(result);
                
                console.log("data result checkout: ", result.token);

                navigate("/checkout");
            } catch (error) {
                console.error("Checkout failed:", error);
            }
    };

    if (!book) return null;

    return (
        <div className="max-w-4xl mx-auto p-4 bg-gradient-to-br py-12 px-6 flex justify-center">
            <div className="w-full max-w-3xl bg-white rounded-xl shadow-xl p-6 sm:p-8 space-y-6">
                <h2 className="text-2xl sm:text-3xl font-bold text-indigo-800 text-center">Detail Pembelian</h2>

                <div className="flex flex-col md:flex-row gap-6">
                <img
                    src={book.bookImage}
                    alt={book.bookTitle}
                    className="w-full md:w-40 h-auto object-cover rounded-lg shadow-md"
                />
                <div className="flex-1 text-left space-y-2">
                    <h1 className="text-xl sm:text-2xl font-bold text-gray-800">{book.bookTitle}</h1>
                    <p className="text-gray-600 text-sm sm:text-base">by {book.author}</p>
                    <p className="text-gray-600 text-sm sm:text-base">{book.description}</p>
                    <p className="text-indigo-700 text-lg font-semibold">
                    Rp {Number(book.price).toLocaleString()}
                    </p>

                    <div className="flex items-center gap-2 mt-3">
                    <button
                        className="px-3 py-1 bg-gray-600 text-white rounded"
                        onClick={() => handleQuantityChange(-1)}
                    >
                        −
                    </button>
                    <span className="px-2 font-medium">{quantity}</span>
                    <button
                        className="px-3 py-1 bg-gray-600 text-white rounded"
                        onClick={() => handleQuantityChange(1)}
                    >
                        +
                    </button>
                    </div>
                </div>
                </div>

                <div className="flex items-center justify-between text-lg font-semibold border-t pt-4 text-gray-700">
                <span>Total</span>
                <span className="text-indigo-700 font-bold">
                    Rp {(Number(book.price) * quantity).toLocaleString()}
                </span>
                </div>

                <button
                    className="w-full py-3 bg-blue-600 hover:bg-blue-700 text-white font-bold rounded-lg transition"
                    onClick={() => handleProceedToCheckout()}
                >
                Proceed to Checkout
                </button>
            </div>
        </div>
    );
};

export default BuyNowPage;