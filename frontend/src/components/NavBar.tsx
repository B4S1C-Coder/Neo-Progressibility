import { useState } from "react";

import { NavLink } from "react-router-dom";

import {
    NavigationMenu,
    NavigationMenuItem,
    NavigationMenuLink,
    NavigationMenuList,
    navigationMenuTriggerStyle,
} from "@/components/ui/navigation-menu";

import {
    Sheet,
    SheetContent,
    SheetTrigger,
} from "@/components/ui/sheet";

import { Button } from "@/components/ui/button";
import { Menu } from "lucide-react";

import { ModeToggle } from "@/components/mode-toggle";

export default function NavBar() {

    const [isOpen, setIsOpen] = useState(false);

    const NavItems = () => (
        <>
            <NavigationMenuItem>
                <NavigationMenuLink className={navigationMenuTriggerStyle()}>
                    <NavLink to="/">Home</NavLink>
                </NavigationMenuLink>
            </NavigationMenuItem>
            <NavigationMenuItem>
                <NavigationMenuLink className={navigationMenuTriggerStyle()}>
                    <NavLink to="/about">About</NavLink>
                </NavigationMenuLink>
            </NavigationMenuItem>
            <NavigationMenuItem>
                <NavigationMenuLink className={navigationMenuTriggerStyle()}>
                    <NavLink to="/signin">Sign In</NavLink>
                </NavigationMenuLink>
            </NavigationMenuItem>
            <NavigationMenuItem>
                <ModeToggle />
            </NavigationMenuItem>
        </>
    );

    return (
        <div className="p-5 fixed shadow-md hover:shadow-sm hover:shadow-red-600 w-full transition ease-in-out duration-300">
            <NavigationMenu>
                <NavigationMenuList className="w-full flex items-center justify-between">
                    <NavigationMenuItem>
                        <NavigationMenuLink className={navigationMenuTriggerStyle()}>
                            <span className="text-red-600 text-base font-bold uppercase">Progressibility</span>
                        </NavigationMenuLink>
                    </NavigationMenuItem>

                    {/* Desktop Menu */}
                    <div className="hidden md:flex space-x-2">
                        <NavItems />
                    </div>
                    {/* Mobile Menu */}
                    <div className="md:hidden ml-x-6">
                        <Sheet open={isOpen} onOpenChange={setIsOpen}>
                            <SheetTrigger asChild>
                                <Button variant="ghost" size="icon">
                                    <Menu className="h-6 w-6" />
                                </Button>
                            </SheetTrigger>
                            <SheetContent side="left">
                                <NavigationMenu className="w-full">
                                    <NavigationMenuList className="flex flex-col space-y-4 w-full">
                                        <NavItems />
                                    </NavigationMenuList>
                                </NavigationMenu>
                            </SheetContent>
                        </Sheet>
                    </div>
                </NavigationMenuList>
            </NavigationMenu>
        </div>
    );
}
