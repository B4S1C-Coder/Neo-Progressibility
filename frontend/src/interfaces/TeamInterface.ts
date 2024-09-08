import TaskInterface from "./TaskInterface";
import UserInterface from "./UserInterface";
import TagInterface from "./TagInterface";

export default interface TeamInterface {
    owner: {
        firstName: string | null,
        lastName: string | null,
        email: string
    },
    name: string,
    id: string,
    tasks: TaskInterface[],
    users: UserInterface[],
    tags: TagInterface[]
};