import { Chat } from "../models/Chat";
import { VoidLike } from "./VoidLike";
import { Message } from "../models/Message";

/**
 * Beobachtet einen Chat
 */
export default interface IChatListener {

    /**
     * Wird aufgerufen, wenn sich der aktuelle Chat ändert
     * @param currentChat Aktueller Chat
     */
    onChatChange(currentChat: Chat): VoidLike;

    /**
     * Wird aufgerufen, wenn eine Nachricht empfangen wird oder es neue Antworten gibt
     * @param currentChat Aktueller Chat
     * @param message Eingegangende Message
     */
    onMessage(currentChat: Chat, message: Message): VoidLike;
}

export function isIChatListener(obj: any) : obj is IChatListener {
    if (typeof(obj) === "object") {
        if (obj.onChatChange !== undefined && obj.onMessage !== undefined) {
            return true;
        }
    }

    return false;
}