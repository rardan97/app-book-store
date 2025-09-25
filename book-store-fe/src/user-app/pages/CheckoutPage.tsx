import { useRef, useState } from "react";
import { useTransaction } from "../context/TransactionContext";

// interface Item {
// bookId: number;
// bookTitle: string;
// price: string; // asumsi harga string
// quantity: number;
// }

declare global {
interface Window {
    snap: any;
}
}



const CheckoutPage: React.FC = () => {

    const snapContainerRef = useRef<HTMLDivElement>(null);
    const { transactionData } = useTransaction();
    const [isSnapOpen, setIsSnapOpen] = useState<boolean>(false);
    

    if (!transactionData) return <p>Tidak ada data transaksi.</p>;



    // const location = useLocation();


    // const { items } = location.state as { items: Item[] };
    const total = transactionData.itemDetails.reduce((acc, item) => acc + Number(item.price) * Number(item.quantity), 0);

    // Hitung total harga
    // const totalPrice = items.reduce(
    //     (sum, item) => sum + Number(item.price) * item.quantity,
    //     0
    // );


// const handlePayment = async () => {
//    // Tampilkan container Snap
//   // Lanjutkan logic Snap...
// };











    const handlePayment = () => {

       

        if (!window.snap || !transactionData.token) {
        alert("Snap not loaded or token missing");
        return;
        }

        if (isSnapOpen) return; // cegah pemanggilan berulang
    
        setIsSnapOpen(true);

        window.snap.embed(transactionData.token, {
        embedId: "snap-container",
        onSuccess: (result: any) => {
            alert("Payment success!");
            console.log(result);
            setIsSnapOpen(false);
        },
        onPending: (result: any) => {
            alert("Waiting for your payment!");
            console.log(result);
            setIsSnapOpen(false);
        },
        onError: (result: any) => {
            alert("Payment failed!");
            console.error(result);
            setIsSnapOpen(false);
        },
        onClose: () => {
            alert("You closed the popup without finishing the payment.");
            setIsSnapOpen(false);
        },
        });
    };




    return (
        <div className="max-w-screen-2xl mx-auto px-6 py-12">
                {/* <h1 className="text-3xl font-extrabold text-indigo-900 text-center mb-5">
                    Checkout
                </h1> */}

                {/* <div className="grid grid-cols-1 xl:grid-cols-10 gap-10"> */}
                    <div className="grid grid-cols-1 xl:grid-cols-10 gap-10 items-start">
                    {/* Kartu Produk – 6/10 */}
                        <div className="xl:col-span-4 bg-white shadow-xl rounded-xl p-8">
                            <h2 className="text-2xl font-bold text-indigo-800 mb-6 border-b pb-3">Detail Item</h2>

                            <div className="space-y-5 max-h-[700px] overflow-y-auto pr-2">
                                {transactionData.itemDetails.map((item) => (
                                <div
                                    key={item.id}
                                    className="flex justify-between items-start bg-indigo-50 rounded-lg p-4 border border-indigo-100"
                                >
                                    <div>
                                        <h3 className="text-base font-bold text-indigo-900">{item.name}</h3>
                                        <p className="text-sm text-indigo-700">Qty: {item.quantity}</p>
                                        <p className="text-sm text-indigo-700">Harga: Rp {Number(item.price).toLocaleString()}</p>
                                    </div>
                                    <div className="text-indigo-900 font-bold text-lg">
                                        Rp {(Number(item.price) * Number(item.quantity)).toLocaleString()}
                                    </div>
                                </div>
                                ))}
                            </div>
                        </div>

                        {/* Kartu Pembayaran – 4/10 */}
                        <div className="xl:col-span-6 bg-white shadow-xl rounded-xl p-8 flex flex-col justify-between">
                            <div>
                                <h2 className="text-2xl font-bold text-indigo-800 mb-6 border-b pb-3">
                                Payment
                                </h2>

                                <div className="space-y-3 mb-4">
                                <label className="text-lg font-semibold text-indigo-900">Customer Details</label>
                                <div className="space-y-1">
                                    <p className="text-sm  text-indigo-900">Budi Utomo</p>
                                    <p className="text-sm text-indigo-900">081808466410</p>
                                    <p className="text-sm text-indigo-700">Jl.Jend.Sudirman Kav.10-11, Jakarta 10220</p>
                                </div>
                                    
                                </div>

                                {/* Item List */}
                                <div className="mb-4 border-t">
                                    <label className="text-lg font-semibold text-indigo-900 ">Items</label>
                                    {/* <h3 className="text-lg font-semibold text-indigo-900 border-t">Items</h3> */}
                                    <ul className="divide-y divide-indigo-200 max-h-64 overflow-y-auto">
                                        {transactionData.itemDetails.map((item) => (
                                        <li
                                            key={item.id}
                                            className="flex justify-between py-2 text-indigo-700"
                                        >
                                            <div>
                                            <p className="font-medium">{item.name} (x{item.quantity})</p>
                                            <p className="text-sm text-indigo-500">Rp {item.price} </p>
                                            </div>
                                            <div className="font-semibold">
                                            Rp {(Number(item.price) * Number(item.quantity)).toLocaleString()}
                                            </div>
                                        </li>
                                        ))}
                                    </ul>
                                </div>

                                <div className="border-t border-indigo-200 pt-4 space-y-2 text-indigo-800 font-semibold">
                                    <div className="flex justify-between">
                                        <span>Subtotal</span>
                                        <span>
                                        Rp {Number(total).toLocaleString()}
                                        </span>
                                    </div>
                                    <div className="flex justify-between">
                                        <span>PPN (10%)</span>
                                        <span>
                                        Rp {(Number(total) * 0.1).toLocaleString()}
                                        </span>
                                    </div>
                                    <div className="flex justify-between text-indigo-900 text-xl font-bold pt-2">
                                        <span>Total</span>
                                        <span>
                                        Rp {(Number(total) * 1.1).toLocaleString()}
                                        </span>
                                    </div>
                                </div>

                                <button
                                onClick={handlePayment}
                                className="w-full mt-6 bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-3 rounded-lg shadow transition duration-300"
                                >
                                Bayar Sekarang
                                </button>

                                <div className="mt-6">
                                    <div
                                        className="w-full max-w-3xl mx-auto"
                                        id="snap-container"
                                        ref={snapContainerRef}
                                    ></div>
                                </div>
                        </div>
                    </div>  
                </div>
        </div>

    );
};

export default CheckoutPage;