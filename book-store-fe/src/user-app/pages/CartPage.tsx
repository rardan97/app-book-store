import React, { useEffect, useRef, useState } from 'react';
import { useCart } from '../context/CartProvider';
import { useNavigate } from 'react-router';
import { useTransaction } from '../context/TransactionContext';
import { checkoutTransaction } from '../api/Checkout';
import type { CheckoutPayload } from '../interfaces/Checkout.interface';

interface CartItem {
    bookId: number;
    bookTitle: string;
    price: string;
    bookImage:string;
    quantity: number;
}

const CartPage: React.FC = () => {
const [cartItems, setCartItems] = useState<CartItem[]>([]);
const [total, setTotal] = useState(0);

const { cart, removeFromCart, updateQuantity } = useCart();

const debounceTimeout = useRef<number | null>(null);

const [checkedItems, setCheckedItems] = useState<number[]>([]);

const { setTransactionData } = useTransaction();


const navigate = useNavigate();



const handleProceedToCheckout = async () => {
    


 const payload: CheckoutPayload = {
            dataProductTransaksi: {
                transaksiId: 102,
                transaksiKode: "ORDER-20250719-002",
                transaksiTotal: "30000"
            },
            dataProductPembelian: [
                {
                productId: 211,
                productNama: "JavaScript for Beginners",
                productHarga: "10000",
                productQty: "2",
                productTotalHarga: "20000"
                },
                {
                productId: 212,
                productNama: "Python for Beginners",
                productHarga: "10000",
                productQty: "1",
                productTotalHarga: "10000"
                }
            ]
            };

// Kirim ke halaman checkout, misal /checkout
const result = await checkoutTransaction(payload);
                setTransactionData(result);

navigate("/checkout");


};



// Sync cartItems dan hitung total awal setiap cart berubah
useEffect(() => {
    setCartItems(cart);
    const initialTotal = cart.reduce((sum, item) => sum + Number(item.price) * item.quantity, 0);
    setTotal(initialTotal);
}, [cart]);

const toggleCheck = (id: number) => {
    setCheckedItems((prev) =>
        prev.includes(id) ? prev.filter((itemId) => itemId !== id) : [...prev, id]
    );
};

useEffect(() => {
const newTotal = cartItems
    .filter((item) => checkedItems.includes(item.bookId))
    .reduce((sum, item) => sum + Number(item.price) * item.quantity, 0);

setTotal(newTotal);
}, [cartItems, checkedItems]);

const handleQuantityChange = (id: number, amount: number) => {
    const newCartItems = cartItems.map((item) => {
    if (item.bookId === id) {
        const newQty = Math.max(item.quantity + amount, 1);
        return { ...item, quantity: newQty };
    }
    return item;
    });

    // Hitung perubahan total secara incremental
    const oldItem = cartItems.find((item) => item.bookId === id);
    const updatedItem = newCartItems.find((item) => item.bookId === id);

    if (oldItem && updatedItem) {
    const oldTotalItem = Number(oldItem.price) * oldItem.quantity;
    const newTotalItem = Number(updatedItem.price) * updatedItem.quantity;
    const diff = newTotalItem - oldTotalItem;
    setTotal((prev) => prev + diff);
    }

    setCartItems(newCartItems);

    // Debounce update ke backend
    if (debounceTimeout.current) {
    clearTimeout(debounceTimeout.current);
    }
    debounceTimeout.current = window.setTimeout(() => {
    updateQuantity(id, updatedItem?.quantity ?? 1);
    }, 500);
};

const handleRemove = (id: number) => {
    const removedItem = cartItems.find((item) => item.bookId === id);
    if (removedItem) {
    const itemTotal = Number(removedItem.price) * removedItem.quantity;
    setTotal((prev) => prev - itemTotal);
    }
    removeFromCart(id);
    setCartItems((prev) => prev.filter((item) => item.bookId !== id));
};

return (
    <div className="max-w-4xl mx-auto p-4 bg-gradient-to-br py-12 px-6 flex justify-center">
{/* <div className="min-h-screen bg-gray-100 flex items-center justify-center px-4 py-10"> */}
<div className="w-full max-w-4xl bg-white rounded-xl shadow-xl p-6 sm:p-8 space-y-6">
    <h1 className="text-2xl sm:text-3xl font-bold text-indigo-800 text-center mb-6">Shopping Cart</h1>

    {cartItems.length === 0 ? (
    <p className="text-gray-500 text-center">Your cart is empty.</p>
    ) : (
    <>
        <div className="space-y-4">
        {cartItems.map((item) => (
            <div
            key={item.bookId}
            className="flex flex-col sm:flex-row sm:items-center justify-between bg-gray-50 border rounded-lg p-4 shadow-sm"
            >
            <div className="flex items-start sm:items-center gap-4 w-full sm:w-2/3">
                <input
                type="checkbox"
                checked={checkedItems.includes(item.bookId)}
                onChange={() => toggleCheck(item.bookId)}
                className="mt-1 sm:mt-0"
                />
                <img
                src={item.bookImage}
                alt={item.bookTitle}
                className="w-20 h-20 object-cover rounded"
                />
                <div>
                <h2 className="font-semibold text-lg">{item.bookTitle}</h2>
                <p className="text-sm text-gray-600">
                    Rp {Number(item.price).toLocaleString()}
                </p>
                </div>
            </div>

            <div className="flex items-center justify-end gap-2 mt-4 sm:mt-0 sm:w-1/3">
                <button
                className="px-2 py-1 text-white bg-gray-600 rounded"
                onClick={() => handleQuantityChange(item.bookId, -1)}
                >
                −
                </button>
                <span>{item.quantity}</span>
                <button
                className="px-2 py-1 text-white bg-gray-600 rounded"
                onClick={() => handleQuantityChange(item.bookId, 1)}
                >
                +
                </button>
                <button
                className="ml-2 text-red-500 hover:underline"
                onClick={() => handleRemove(item.bookId)}
                >
                Remove
                </button>
            </div>
            </div>
        ))}
        </div>

        <div className="border-t pt-4 flex flex-col sm:flex-row justify-between items-center">
            <p className="text-lg font-bold text-gray-800">
                Total: Rp {total.toLocaleString()}
            </p>
            <button
                className="mt-4 sm:mt-0 px-6 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 disabled:opacity-50 transition"
                onClick={handleProceedToCheckout}
                disabled={checkedItems.length === 0}
            >
                Proceed to Checkout
            </button>
        </div>
    </>
    )}
</div>
</div>
);
};

export default CartPage;