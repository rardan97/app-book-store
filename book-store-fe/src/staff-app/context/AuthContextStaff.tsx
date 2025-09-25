import {
  createContext,
  useContext,
  useEffect,
  useState,
  type ReactNode,
} from "react";
import {jwtDecode} from "jwt-decode";

interface JwtPayload {
  sub: string;
  iat: number;
  exp: number;
}

// Struktur info user dari respons login
interface StaffInfo {
  staffId: number;
  staffUserName: string;
  staffRoles: string[];
}

interface AuthContextType {
  token: string | null;
  refreshToken: string | null;
  staff: StaffInfo | null;
  isAuthenticated: boolean;
  loginStaff: (authData: LoginResponse) => void;
  logoutStaff: () => void;
}

interface LoginResponse {
  token: string;
  refreshToken: string;
  staffId: number;
  staffUserName: string;
  staffRoles: string[];
  type: string;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  
  const [token, setToken] = useState<string | null>(() => localStorage.getItem("staff_accessToken"));
  const [refreshToken, setRefreshToken] = useState<string | null>(() => localStorage.getItem("staff_refreshToken"));
  
  const [staff, setStaff] = useState<StaffInfo | null>(() => {
    const stored = localStorage.getItem("staff_data");
    return stored ? JSON.parse(stored) : null;
  });

  // Fungsi logout
  const logoutStaff = () => {
    localStorage.removeItem("staff_accessToken");
    localStorage.removeItem("staff_refreshToken");
    localStorage.removeItem("staff_data");
    setToken(null);
    setRefreshToken(null);
    setStaff(null);
  };

  // Cek token expired dan logout otomatis
  useEffect(() => {
    if (token) {
      try {
        const decoded = jwtDecode<JwtPayload>(token);
        const isExpired = decoded.exp * 1000 < Date.now();
        if (isExpired) {
          logoutStaff();
        }
      } catch (e) {
        console.error("Invalid token", e);
        logoutStaff();
      }
    }
  }, [token]);

  // Sinkronisasi localStorage dengan state jika terjadi perubahan di luar React (optional)
  useEffect(() => {
    function syncStorage() {
      const localToken = localStorage.getItem("staff_accessToken");
      const localRefresh = localStorage.getItem("staff_refreshToken");
      const localUser = localStorage.getItem("staff_data");
      if (localToken !== token) setToken(localToken);
      if (localRefresh !== refreshToken) setRefreshToken(localRefresh);
      if (localUser !== JSON.stringify(staff)) setStaff(localUser ? JSON.parse(localUser) : null);
    }
    window.addEventListener("storage", syncStorage);
    return () => window.removeEventListener("storage", syncStorage);
  }, [token, refreshToken, staff]);

  const loginStaff = (authData: LoginResponse) => {
    const { token, refreshToken, staffId, staffUserName, staffRoles } = authData;

    const userInfo: StaffInfo = {
      staffId,
      staffUserName,
      staffRoles,
    };

    localStorage.setItem("staff_accessToken", token);
    localStorage.setItem("staff_refreshToken", refreshToken);
    localStorage.setItem("staff_data", JSON.stringify(userInfo));

    setToken(token);
    setRefreshToken(refreshToken);
    setStaff(userInfo);
  };

  const isAuthenticated = !!token && !!staff;

  return (
    <AuthContext.Provider
      value={{ token, refreshToken, staff, isAuthenticated, loginStaff, logoutStaff }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuthStaff = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};