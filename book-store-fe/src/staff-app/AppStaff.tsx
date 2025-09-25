import { Route, Routes } from "react-router-dom";
import AppLayout from "./layout/AppLayout";
import Home from "./pages/Dashboard/Home";
import { HelmetProvider } from "react-helmet-async";
import UserProfiles from "./pages/UserProfiles";
import Blank from "./pages/Blank";
import SignIn from "./pages/AuthPages/SignIn";
import SignUp from "./pages/AuthPages/SignUp";
import Category from "./pages/CategoryPages/Category";
import Books from "./pages/BooksPages/Books";
import UserManagement from "./pages/UserManagementPages/UserManagement";
import Role from "./pages/RolePages/Role";
import { AuthProvider } from "./context/AuthContextStaff";
import PrivateRoute from "./utils/PrivateRoute";
import LogoutButton from "./components/auth/LogoutButton";
import ProtectedRoute from "./utils/ProtectedRoute";
import OrderManagement from "./pages/OrderManagementPages/OrderManagement";

const AppStaff = () => {
return (
    <> 
        <HelmetProvider>
            <AuthProvider>      
                <Routes>
                    <Route path="/signin" element={<SignIn />} ></Route>
                    <Route path="/signup" element={<SignUp />} ></Route>
                    <Route path="/logout" element={<LogoutButton />} ></Route>
                    <Route 
                    path="/" element={
                    <PrivateRoute>
                        <AppLayout />
                    </PrivateRoute>
                    
                    }>
                        <Route index element={<Home />} />
                        <Route path="profile" element={<UserProfiles />} />
                        <Route path="blank" element={<Blank />} />
                        <Route path="category" element={<Category />}/>
                        <Route path="ordermanagement" element={<OrderManagement />}/>
                        <Route path="books" element={<Books />} />
                        <Route path="usermanagement" element={<UserManagement />} />
                        <Route path="role" element={
                            <ProtectedRoute allowedRoles={['ROLE_ADMIN']}>
                                <Role />
                            </ProtectedRoute>
                        }
                        />
                    </Route>
                </Routes>
            </AuthProvider>
        </HelmetProvider>
    </>
);
};

export default AppStaff;