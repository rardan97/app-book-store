import { createContext, useCallback, useContext, useEffect, useState, type ReactNode } from "react";
import { useAuthUser } from "./AuthProviderUser";
import { addCart, clearCart, getCart, removeCart, updateCartQty } from "../api/Cart";
import { getLoadImagePublicBook } from "../api/BookPublic";

interface CartItem {
    bookId: number;
    bookTitle: string;
    price: string;
    bookImage: string;     // untuk ditampilkan
    bookImageFileName: string, // baru, untuk dikirim ke server
    quantity: number;
}


type CartItemPayload = {
  bookId: number;
  bookTitle: string;
  price: string;
  quantity: number;
  bookImage: string;
};

interface CartContextType {
    cart: CartItem[];
    addToCart: (item: CartItem) => void;
    removeFromCart: (productId: number) => void;
    clearFromCart: () => void;
    updateQuantity: (bookId: number, quantity: number) => void;
}

const CartContext = createContext<CartContextType | undefined>(undefined);

export const CartProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const { user } = useAuthUser();
    const userId = user?.userId;
    const [cart, setCart] = useState<CartItem[]>([]);

    useEffect(() => {
  // Cleanup old blob URLs setiap kali cart berubah atau saat unmount
  return () => {
    cart.forEach(book => {
      if (book.bookImage && typeof book.bookImage === "string" && book.bookImage.startsWith("blob:")) {
        URL.revokeObjectURL(book.bookImage);
        console.log("Revoked URL for:", book.bookImage);
      }
    });
  };
}, [cart]);


    const fetchCart = useCallback(async () => {
         setCart(prevCart => {
            // Revoke semua blob URLs di cart lama
            prevCart.forEach(book => {
                if (book.bookImage && book.bookImage.startsWith("blob:")) {
                URL.revokeObjectURL(book.bookImage);
                }
            });
            return prevCart;
        });
        
        const token = localStorage.getItem("user_accessToken");
        if (!token || !userId) return;

        try {
            const result = await getCart(token, userId);

            const updatedBooks = await Promise.all(
                result.map(async (book) => {
                    let imageUrl = "";

                    if (typeof book.bookImage === "string" && book.bookImage.trim() !== "") {
                        console.log("Data Cart Context CC: "+book.bookImage);
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
                        // quantity: 1,
                    };
                })
            );

            setCart(updatedBooks);
        } catch (error) {
            console.error("Fetch cart error", error);
        }
    }, [userId]);

    useEffect(() => {
        fetchCart();
        
    }, [fetchCart]);

    const addToCart = async (item: CartItem) => {
        console.log("Add Cart BookImage : "+item.bookImageFileName);

       

        const newCartItem: CartItemPayload = {
            bookId: item.bookId,
            bookTitle: item.bookTitle,
            price: item.price,
            quantity: item.quantity,
            bookImage: item.bookImageFileName,
                            
        };

        const token = localStorage.getItem("user_accessToken");
        if (!token || !userId) return;
        try {
            await addCart(token, userId, newCartItem);
            fetchCart();
        } catch (error) {
            console.error("Add to cart error", error);
        }
    };

    const removeFromCart = async (bookId: number) => {
        const token = localStorage.getItem("user_accessToken");
         if (!token || !userId) return;
        try {
            await removeCart(token, userId, bookId);
            fetchCart();
        } catch (error) {
            console.error("Remove cart error", error);
        }
    };

    const clearFromCart = async () => {
        const token = localStorage.getItem("user_accessToken");
         if (!token || !userId) return;
        try {
            await clearCart(token, userId);
            setCart([]);
        } catch (error) {
            console.error("Clear cart error", error);
        }
    };

      // Fungsi baru untuk update quantity
  const updateQuantity = async (bookId: number, quantity: number) => {
    const token = localStorage.getItem("user_accessToken");
    if (!token || !userId) return;

     const prevCart = [...cart];

    // Update state lokal dulu supaya UI responsif
     setCart(prevCart.map(item => item.bookId === bookId ? { ...item, quantity } : item));

    try {
      await updateCartQty(token, userId, bookId, quantity); // pastikan API ini ada
      // Bisa panggil fetchCart() lagi kalau perlu sinkronisasi
    //    fetchCart();
    } catch (error) {
      console.error("Update quantity error", error);
      // Opsional: rollback state atau tampilkan notifikasi error
      setCart(prevCart);
    }
  };

    return (
        <CartContext.Provider value={{ cart, addToCart, removeFromCart, clearFromCart, updateQuantity}}>
            {children}
        </CartContext.Provider>
    );
};

// Hook custom untuk akses cart
export const useCart = (): CartContextType => {
    const context = useContext(CartContext);
    if (!context) {
        throw new Error("useCart must be used within a CartProvider");
    }
    return context;
};