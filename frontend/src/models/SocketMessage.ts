import { IChat } from "./Chat";
import { IMessage } from "./Message";
import { Answer } from "./Answer";
import { Points } from "./Points";

export type SocketMessage =
    {
        command: "CreateChat"
        chat: IChat
    } |
    {
        command: "AddMessage"
        chatId: string
        message: IMessage
    } |
    {
        command: "AddAnswer",
        chatId: string,
        messageId: string,
        answer: Answer
    } |
    {
        command: "SubmitAnswer",
        answerId: number,
        chatId: number,
        messageId: string
    } |
    {
        command: "Reinit"
    } |
    {
        command: "ReadMessage", //TODO was der Sinn ?
        messageId: string,
        chatId: number
    } |
    ({
        command: "ChangePoints",
    } & Points) | // Enthält alle Felder aus Points 
    {
        command: "GameOver"
    } |
    {
        command: "HandleError",
        message: string
    }