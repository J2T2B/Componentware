import { Chat } from "./Chat";
import { Message } from "./Message";
import { Answer } from "./Answer";

export type SocketMessage =
{
    command: "CreateChat"
    chat: Chat
} |
{
    command: "AddMessage"
    chatId: number
    message: Message
} |
{
    command: "AddAnswer",
    chatId: number,
    messageId: number,
    answer: Answer
} | 
{
    command: "SubmitAnswer",
    answerId: number
} |
{
    command: "Reinit"
}