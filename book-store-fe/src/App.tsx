// import { Route, Routes } from "react-router-dom";
import { useRoutes } from "react-router-dom";

import AppStaff from "./staff-app/AppStaff";
import AppUser from "./user-app/AppUser";

export default function App() {
    const routes = useRoutes([
      { path: "/staff/*", element: <AppStaff /> },
      { path: "/*", element: <AppUser /> },
  ]);

  return routes;
}