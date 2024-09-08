import {
    Sheet,
    SheetContent,
    SheetHeader,
    SheetTitle,
    SheetTrigger,
} from "@/components/ui/sheet";

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"

import { Button } from "@/components/ui/button";

import { Calendar } from "lucide-react";

import { ListChecks } from 'lucide-react';

import { Star } from "lucide-react";

import UserInterface from "@/interfaces/UserInterface";

export default function UserHamburger(props: UserInterface) {

    const buttonVariant = "ghost";
    const buttonStyles = "mx-8 my-2 flex items-start";

    return (
        <Sheet>
            <SheetTrigger asChild>
                <Button variant="ghost">
                    <Avatar>
                        <AvatarImage src="https://github.com/shadcn.png" />
                        <AvatarFallback>CN</AvatarFallback>
                    </Avatar>
                </Button>
            </SheetTrigger>
            <SheetContent side="left">
                <SheetHeader>
                    <div className="flex flex-row">
                        <Avatar>
                            <AvatarImage src="https://github.com/shadcn.png" />
                            <AvatarFallback>CN</AvatarFallback>
                        </Avatar>
                        <SheetTitle>{ props.firstName } {props.lastName}</SheetTitle>
                    </div>
                </SheetHeader>
                <div className="flex flex-col">
                    <Button variant={buttonVariant} className={buttonStyles}><Calendar className="mr-5" />Calendar</Button>
                    <Button variant={buttonVariant} className={buttonStyles}><ListChecks className="mr-5" />Tasks</Button>
                    <Button variant={buttonVariant} className={buttonStyles}><Star className="mr-5" />Important</Button>
                </div>
            </SheetContent>
        </Sheet>
    );
}
  