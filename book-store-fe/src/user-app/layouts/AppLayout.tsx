
import { Outlet } from "react-router-dom";
import AppFooter from "./AppFooter";
import AppHeader from "./AppHeader";

const LayoutContent: React.FC = () => {

    return (
        <div className="flex flex-col min-h-screen">
            <AppHeader />
            <main className="flex-grow">
                <Outlet />
            </main>
            <AppFooter />
        </div>
    );
};

const AppLayout: React.FC = () => {
    return (
            <LayoutContent />
    );
};

export default AppLayout;
