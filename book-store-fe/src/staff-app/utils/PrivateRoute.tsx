import { Navigate } from "react-router-dom";
import type { JSX } from "react";
import { useAuthStaff } from "../context/AuthContextStaff";


interface PrivateRouteProps {
  children: JSX.Element;
}

const PrivateRoute = ({ children }: PrivateRouteProps) => {
  const { isAuthenticated } = useAuthStaff();

  return isAuthenticated ? children : <Navigate to="/staff/signin" />;
};

export default PrivateRoute;