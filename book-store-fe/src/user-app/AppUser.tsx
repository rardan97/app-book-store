import { Route, Routes } from "react-router";
import SignIn from "./pages/AuthPage/SignIn";
import SignUp from "./pages/AuthPage/SignUp";
import Home from "./pages/Home";
import BooksPage from "./pages/BooksPage";
import AboutPage from "./pages/AboutPage";
import BuyNowPage from "./pages/BuyNowPage";
import { AuthProviderUser } from "./context/AuthProviderUser";
import LogoutButton from "./components/auth/LogoutButton";
import PrivateRoute from "./utils/PrivateRoute";
import CartPage from "./pages/CartPage";
import { CartProvider } from "./context/CartProvider";
import CheckoutPage from "./pages/CheckoutPage";
import { BooksProvider } from "./context/BooksContext";
import { TransactionProvider } from "./context/TransactionContext";
import AppLayout from "./layouts/AppLayout";



const AppUser = () => {
    return (
        <> 
            <AuthProviderUser>   
                <CartProvider>   
                    <BooksProvider>
                        <TransactionProvider>
            <Routes>
                <Route path="/signin" element={<SignIn />} ></Route>
                <Route path="/signup" element={<SignUp />} ></Route>
                {/* <Route path="/logout" element={<LogoutButton />} ></Route> */}
                <Route path="/" element={
                // <PrivateRoute>
                    <AppLayout />
                // </PrivateRoute>
                } >
                    <Route index element={<Home />} />
                    <Route path="/book" element={<BooksPage />} />
                    <Route path="/about" element={<AboutPage />} />
                    <Route path="/cart" element={
                        <PrivateRoute>
                            <CartPage />
                        </PrivateRoute>
                        } />
                    <Route path="/checkout" element={
                        <PrivateRoute>
                            <CheckoutPage />
                        </PrivateRoute>
                        } />
                    <Route path="/buynow" element={
                        <PrivateRoute>
                            <BuyNowPage />
                        </PrivateRoute>
                        } />
                    <Route path="/logout" element={<LogoutButton />} />
                </Route>
            </Routes>
             </TransactionProvider>
            </BooksProvider>
             </CartProvider>
             
            </AuthProviderUser>
        </>
    );
};
export default AppUser;