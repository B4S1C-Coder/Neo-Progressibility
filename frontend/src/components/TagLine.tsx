import { Button } from "./ui/button";

export default function TagLine() {
    return (
        <div className="max-w-md flex flex-col items-center justify-center p-3">
            <h1 className="text-3xl font-bold">Overly complex for an overly complex life.</h1>
            <p className="text-xs p-1">Tackle the complexities of life with the clone of the world's #1 task manager and to-do list app.</p>
            <Button>Get Started</Button>
        </div>
    )
}