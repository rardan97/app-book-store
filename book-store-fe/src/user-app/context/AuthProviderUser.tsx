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
interface UserInfo {
  userId: number;
  userName: string;
  avatarUrl?: string;
}

interface AuthContextType {
  token: string | null;
  refreshToken: string | null;
  user: UserInfo | null;
  isAuthenticated: boolean;
  loginUser: (authData: LoginResponse) => void;
  logoutUser: () => void;
}

interface LoginResponse {
  token: string;
  refreshToken: string;
  userId: number;
  userName: string;
  type: string;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProviderUser = ({ children }: { children: ReactNode }) => {
  const [token, setToken] = useState<string | null>(() => localStorage.getItem("user_accessToken"));
  const [refreshToken, setRefreshToken] = useState<string | null>(() => localStorage.getItem("user_refreshToken"));
  
  const [user, setUser] = useState<UserInfo | null>(() => {
    const stored = localStorage.getItem("user_data");
    return stored ? JSON.parse(stored) : null;
  });

  // Fungsi logout
  const logoutUser = () => {
    localStorage.removeItem("user_accessToken");
    localStorage.removeItem("user_refreshToken");
    localStorage.removeItem("user_data");
    setToken(null);
    setRefreshToken(null);
    setUser(null);
  };

  // Cek token expired dan logout otomatis
  useEffect(() => {
    if (token) {
      try {
        const decoded = jwtDecode<JwtPayload>(token);
        const isExpired = decoded.exp * 1000 < Date.now();
        if (isExpired) {
          logoutUser();
        }
      } catch (e) {
        console.error("Invalid token", e);
        logoutUser();
      }
    }
  }, [token]);

  // Sinkronisasi localStorage dengan state jika terjadi perubahan di luar React (optional)
  useEffect(() => {
    function syncStorage() {
      const localToken = localStorage.getItem("user_accessToken");
      const localRefresh = localStorage.getItem("user_refreshToken");
      const localUser = localStorage.getItem("user_data");
      if (localToken !== token) setToken(localToken);
      if (localRefresh !== refreshToken) setRefreshToken(localRefresh);
      if (localUser !== JSON.stringify(user)) setUser(localUser ? JSON.parse(localUser) : null);
    }
    window.addEventListener("storage", syncStorage);
    return () => window.removeEventListener("storage", syncStorage);
  }, [token, refreshToken, user]);

  const loginUser = (authData: LoginResponse) => {
    const { token, refreshToken, userId, userName } = authData;

    const userInfo: UserInfo = {
      userId,
      userName
    };

    localStorage.setItem("user_accessToken", token);
    localStorage.setItem("user_refreshToken", refreshToken);
    localStorage.setItem("user_data", JSON.stringify(userInfo));

    setToken(token);
    setRefreshToken(refreshToken);
    setUser(userInfo);
  };

  const isAuthenticated = !!token && !!user;

  return (
    <AuthContext.Provider
      value={{ token, refreshToken, user, isAuthenticated, loginUser, logoutUser }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuthUser = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};