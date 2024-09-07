import { Button } from "@/components/ui/button";

export default function TagLine() {
    return (
        <div className="max-w-md flex flex-col items-center justify-center p-5 selection:bg-red-400 selection:text-white">
            <h1 className="text-4xl font-bold"><span className="text-red-600">Empower</span> your progress, effortlessly.</h1>
            <p className="text-sm py-3 opacity-75">Achieve more with a task manager that simplifies your workflow and turns goals into progress—effortlessly.<br/><b>696K+ <span className="text-red-600">★★★★★</span> reviews</b></p>
            <Button>Get Started</Button>
        </div>
    )
}