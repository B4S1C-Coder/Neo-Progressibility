import { Outlet } from "react-router-dom";
import NavBar from "@/components/NavBar";

export default function ExternalSiteLayout() {
    return (
        <div className="flex flex-col min-h-screen">
            <NavBar />
            <main className="flex-grow">
                <Outlet />
            </main>
        </div>
    );
}