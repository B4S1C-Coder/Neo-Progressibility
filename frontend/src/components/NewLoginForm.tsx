import {
    Card,
    CardHeader,
    CardContent,
    CardDescription,
    CardTitle,
} from "@/components/ui/card"

import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label"

import { ArrowRight } from "lucide-react";

import { ArrowLeft } from "lucide-react";
import { ModeToggle } from "@/components/mode-toggle";

import { NavLink } from "react-router-dom";

export default function NewLoginForm() {
    return (
        <Card className="md:w-[350px]">
            <div className="flex justify-between items-center">
                <NavLink to="/home">
                    <Button variant="ghost">
                        <ArrowLeft size={20} />
                    </Button>
                </NavLink>
                <ModeToggle />
            </div>
            <CardHeader>
                <CardTitle className="text-red-600">Welcome back!</CardTitle>
                <CardDescription>Sign In to continue to Progressibility.</CardDescription>
            </CardHeader>
            <CardContent>
                <form>
                    <div className="grid w-full items-center gap-4">
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="email">Email</Label>
                            <Input id="email" type="email" placeholder="Email"/>
                        </div>
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="password">Password</Label>
                            <Input id="password" type="password" placeholder="Password"/>
                        </div>
                        <div className="flex justify-center">
                            <Button type="submit">Sign In <ArrowRight size={26} className="pl-2" /></Button>
                        </div>
                    </div>
                </form>
            </CardContent>
        </Card>
    );
}