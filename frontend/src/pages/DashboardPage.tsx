import UserHamburger from "@/components/UserHamburger";
import mockData from "@/data/mock_data";

import {
    ResizableHandle,
    ResizablePanel,
    ResizablePanelGroup,
} from "@/components/ui/resizable"
  

export default function DashboardPage() {

    return (
        <ResizablePanelGroup direction="horizontal" className="rounded-lg border min-h-screen">
            <ResizablePanel defaultSize={15} minSize={15} maxSize={20}>
                <UserHamburger {...mockData} />
            </ResizablePanel>
            <ResizableHandle />
            <ResizablePanel>Other Content</ResizablePanel>
        </ResizablePanelGroup>
    );
}