import { VoidLike } from "./VoidLike";
import { Chat } from "../models/Chat";

/**
 * Beobachtet alle Chats
 */
export default interface IChatsListener {
    /**
     * Wird aufgerufen wenn:
     *  - Eine neue Nachricht eingeht
     *  - Ein neuer Chat erstellt wurde
     * @param chats Alle bekannten Chats
     */
    onChatChange(chats: Chat[]): VoidLike;
}

export function isIChatsListener(obj: any) : obj is IChatsListener {
    if (typeof(obj) === "object") {
        if (obj.onChatChange !== undefined) {
            return true;
        }
    }

    return false;
}