import TaskInterface from "./TaskInterface"

export default interface TagInterface {
    id: string,
    name: string,
    tasks: TaskInterface[]
};