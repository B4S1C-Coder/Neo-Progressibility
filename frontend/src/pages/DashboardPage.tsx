import UserHamburger from "@/components/UserHamburger";
import mockData from "@/data/mock_data";

export default function DashboardPage() {
    return (
        <div className="fixed">
            <UserHamburger {...mockData}/>
        </div>
    );
}