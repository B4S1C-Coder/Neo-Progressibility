import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "@/components/ui/popover";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";

export default function LoginForm() {
    return (
        <div>
            <Popover>
                <PopoverTrigger>Sign In</PopoverTrigger>
                <PopoverContent>
                    <form>
                        <Input type="email" placeholder="Email"/>
                        <br/>
                        <Input type="password" placeholder="Password"/>
                        <br/>
                        <div className="flex justify-center">
                            <Button type="submit">Login</Button>
                        </div>
                    </form>
                </PopoverContent>
            </Popover>
        </div>
    );
}