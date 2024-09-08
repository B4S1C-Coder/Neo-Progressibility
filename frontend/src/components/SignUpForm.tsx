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

export default function SignUpForm() {
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
                <CardTitle className="text-red-600">Welcome aboard!</CardTitle>
                <CardDescription>Let's create your Progressibility Account.</CardDescription>
            </CardHeader>
            <CardContent>
                <form>
                    <div className="grid w-full items-center gap-4">
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="firstname">First Name</Label>
                            <Input id="firstname" type="text" placeholder="First Name"/>
                        </div>
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="lastname">Last Name</Label>
                            <Input id="last" type="text" placeholder="Last Name"/>
                        </div>
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="email">Email</Label>
                            <Input id="email" type="email" placeholder="Email"/>
                        </div>
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="password">Password</Label>
                            <Input id="password" type="password" placeholder="Password"/>
                        </div>
                        <div className="flex justify-center">
                            <Button type="submit">Let's Go <ArrowRight size={26} className="pl-2" /></Button>
                        </div>
                    </div>
                </form>
            </CardContent>
        </Card>
    );
}