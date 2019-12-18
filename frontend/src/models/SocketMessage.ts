import { IChat } from "./Chat";
import { IMessage } from "./Message";
import { Answer } from "./Answer";

export type SocketMessage =
{
    command: "CreateChat"
    chat: IChat
} |
{
    command: "AddMessage"
    chatId: number
    message: IMessage
} |
{
    command: "AddAnswer",
    chatId: number,
    messageId: string,
    answer: Answer
} | 
{
    command: "SubmitAnswer",
    answerId: number
} |
{
    command: "Reinit"
} |
{
    command: "ReadMessage",
    messageId: string
}