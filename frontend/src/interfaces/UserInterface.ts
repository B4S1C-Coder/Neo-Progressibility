import TaskInterface from "./TaskInterface";
import TagInterface from "./TagInterface";
import TeamInterface from "./TeamInterface";

export default interface UserInterface {
    id: string,
    firstName: string | null,
    lastName: string | null,
    email: string,
    roles: string[],
    tasks: TaskInterface[],
    tags: TagInterface[],
    teams: TeamInterface[],
    teamInvitations: any[],
};