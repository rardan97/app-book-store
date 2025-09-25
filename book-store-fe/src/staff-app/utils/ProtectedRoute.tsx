import { type ReactNode } from "react";
import { Navigate } from "react-router";
import { useAuthStaff } from "../context/AuthContextStaff";

interface ProtectedRouteProps {
  allowedRoles: string[]; // daftar role yang boleh akses
  children: ReactNode;    // komponen yang ingin ditampilkan jika akses diizinkan
}


export default function ProtectedRoute({ allowedRoles, children }: ProtectedRouteProps) {
  const { staff, isAuthenticated } = useAuthStaff();

  if (!isAuthenticated) {
    // Jika belum login, redirect ke halaman signin
    return <Navigate to="/staff/signin" replace />;
  }

  if (!staff || !allowedRoles.includes(staff.staffRoles[0])) {
    // Jika role user tidak ada di allowedRoles, redirect ke unauthorized
    return <Navigate to="/unauthorized" replace />;
  }

  // Kalau sudah login dan role sesuai, tampilkan children (komponen yang dibungkus)
  return <>{children}</>;
}