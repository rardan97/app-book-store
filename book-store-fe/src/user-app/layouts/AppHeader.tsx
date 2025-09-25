import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuthUser } from "../context/AuthProviderUser";
import { useCart } from "../context/CartProvider";

const AppHeader: React.FC = () => {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [showDropdown, setShowDropdown] = useState(false);
    const navigate = useNavigate();

    const { isAuthenticated, user, logoutUser } = useAuthUser();

    const handleLogout = () => {
        logoutUser();
        setShowDropdown(false);
        navigate("/signin");
    };

    const getInitial = (name: string) => {
        return name.charAt(0).toUpperCase();
    };

    const { cart } = useCart();
    const totalItems = cart.reduce((sum, item) => sum + item.quantity, 0);
    // const totalItems = cart.length;



    return (
        <header className="bg-white shadow-md sticky top-0 z-50">
            <div className="container mx-auto px-4 py-4 flex items-center justify-between relative">
                {/* Brand */}
                <Link to="/" className="text-3xl font-extrabold text-indigo-600 select-none">
                    BlackCode
                </Link>

                {/* Hamburger for mobile */}
                <button
                    className="md:hidden text-indigo-600 focus:outline-none z-50"
                    onClick={() => setIsMenuOpen(!isMenuOpen)}
                    aria-label="Toggle Menu"
                >
                    <svg
                        className={`w-8 h-8 transition-transform duration-300 ${isMenuOpen ? "rotate-90" : "rotate-0"}`}
                        fill="none"
                        stroke="currentColor"
                        viewBox="0 0 24 24"
                    >
                        {isMenuOpen ? (
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                        ) : (
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
                        )}
                    </svg>
                </button>

                {/* Desktop Menu */}
                <nav className="hidden md:flex space-x-8 items-center font-medium text-gray-700 select-none">
                    <Link to="/" className="hover:text-indigo-600 transition">Home</Link>
                    <Link to="/about" className="hover:text-indigo-600 transition">About</Link>

                    {isAuthenticated ? (
                       <div className="flex items-center space-x-7">
                            {/* Cart Icon */}
                            <Link
                                to="/cart"
                                className="relative text-gray-700 hover:text-indigo-600 transition text-xl"
                                title="Keranjang"
                            >
                                🛒
                                <span className="absolute -top-2 -right-2 bg-red-500 text-white text-xs w-5 h-5 flex items-center justify-center rounded-full">
                                   {totalItems} {/* Ganti dengan cart.length dari context */}
                                </span>
                            </Link>
                       
                        
                        <div className="relative">
                            
                           
                            <button
                                onClick={() => setShowDropdown(!showDropdown)}
                                className="w-10 h-10 rounded-full bg-indigo-600 text-white font-bold flex items-center justify-center hover:bg-indigo-700 focus:outline-none"
                            >
                                {user?.avatarUrl ? (
                                <img src={user.avatarUrl} alt="Avatar" className="w-10 h-10 rounded-full object-cover" />
                                ) : (
                                user?.userName ? getInitial(user.userName) : "?"
                                )}
                            </button>
                            {showDropdown && (
                                <div className="absolute right-0 mt-2 w-44 bg-white border rounded-md shadow-lg z-50">
                                    <div className="px-4 py-2 text-sm text-gray-500">{user?.userName}</div>
                                    <Link
                                        to="/profile"
                                        onClick={() => setShowDropdown(false)}
                                        className="block px-4 py-2 text-gray-700 hover:bg-gray-100"
                                    >
                                        Profile
                                    </Link>
                                    <button
                                        onClick={handleLogout}
                                        className="w-full text-left px-4 py-2 text-gray-700 hover:bg-gray-100"
                                    >
                                        Logout
                                    </button>
                                </div>
                            )}
                        </div>
                         </div>
                    ) : (
                        <>
                        <Link
                            to="/signin"
                            className="bg-red-600 text-white px-5 py-2 rounded-lg shadow-md hover:bg-red-700 transition"
                        >
                            Sign In
                        </Link>
                        <Link
                            to="/signup"
                            className="bg-red-600 text-white px-5 py-2 rounded-lg shadow-md hover:bg-red-700 transition"
                        >
                            Sign Up
                        </Link>
                        </>
                    )}
                </nav>
            </div>

            {/* Mobile Menu */}
            <div className={`md:hidden fixed inset-x-0 top-[64px] bg-white bg-opacity-95 backdrop-blur-md shadow-lg overflow-hidden transition-all duration-300 ease-in-out ${isMenuOpen ? "max-h-[400px] py-6 px-6" : "max-h-0 px-6"}
            z-40 rounded-b-xl`}>
                <nav className="flex flex-col gap-4 font-medium text-gray-800 text-base select-none">
                    <Link
                        to="/home"
                        onClick={() => setIsMenuOpen(false)}
                        className="py-2 px-4 rounded-md hover:bg-indigo-100 transition"
                    >
                    Home
                    </Link>
                    <Link
                        to="/about"
                        onClick={() => setIsMenuOpen(false)}
                        className="py-2 px-4 rounded-md hover:bg-indigo-100 transition"
                    >
                    About
                    </Link>

                    {isAuthenticated ? (
                    <>
                        <Link
                            to="/cart"
                            onClick={() => setIsMenuOpen(false)}
                            className="py-2 px-4 rounded-md hover:bg-indigo-100 transition flex items-center gap-2 relative"
                        >
                            <span className="text-xl">🛒</span>
                            <span>Keranjang</span>
                            <span className="absolute top-1 right-3 bg-red-500 text-white text-xs w-5 h-5 flex items-center justify-center rounded-full">
                                3 {/* ganti dengan cart.length */}
                            </span>
                        </Link>
                        {/* Avatar & Nama User */}
                        <div className="flex items-center gap-3 px-4 py-3 bg-indigo-50 rounded-md">
                            <div className="w-10 h-10 bg-indigo-600 text-white rounded-full flex items-center justify-center font-semibold">
                                {user?.userName ? getInitial(user.userName) : "?"}
                            </div>
                            <div className="flex-1">
                                <p className="text-sm font-medium text-gray-800">{user?.userName}</p>
                                <Link
                                to="/profile"
                                onClick={() => setIsMenuOpen(false)}
                                className="text-xs text-indigo-600 hover:underline"
                                >
                                View Profile
                                </Link>
                            </div>
                        </div>

                        {/* Logout */}
                        <button
                            onClick={() => {
                                setIsMenuOpen(false);
                                handleLogout();
                            }}
                            className="mt-2 flex items-center gap-2 px-4 py-2 text-red-600 hover:text-red-800 hover:bg-red-100 rounded-md transition"
                        >
                            <svg
                                xmlns="http://www.w3.org/2000/svg"
                                className="w-5 h-5"
                                fill="none"
                                viewBox="0 0 24 24"
                                stroke="currentColor"
                            >
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 16l4-4m0 0l-4-4m4 4H7" />
                            </svg>
                            Logout
                        </button>
                    </>
                    ) : (
                    <>
                        <Link
                            to="/signin"
                            onClick={() => setIsMenuOpen(false)}
                            className="bg-red-600 text-white text-center py-2 rounded-md shadow hover:bg-red-700 transition"
                        >
                        Sign In
                        </Link>
                        <Link
                            to="/signup"
                            onClick={() => setIsMenuOpen(false)}
                            className="bg-red-600 text-white text-center py-2 rounded-md shadow hover:bg-red-700 transition"
                        >
                        Sign Up
                        </Link>
                    </>
                    )}
                </nav>
            </div>
        </header>
    );
};

export default AppHeader;
