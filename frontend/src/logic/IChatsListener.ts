import { VoidLike } from "./VoidLike";
import { Chat } from "../models/Chat";
import { Points } from "../models/Points";

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

    /**
     * Wird aufgerufen, wenn ein Chat ausgewählt wird
     * @param currentChat Aktueller Chat
     */
    onCurrentChatChange(currentChat?: Chat): VoidLike;

    /**
     * Wird aufgerufen, wenn sich die Punkte für den Kundensupport ändern
     * @param currentPoints Aktuelle Punkte
     */
    onPointsChange(currentPoints: Points): VoidLike;
}

export function isIChatsListener(obj: any) : obj is IChatsListener {
    if (typeof(obj) === "object") {
        if (obj.onChatChange !== undefined) {
            return true;
        }
    }

    return false;
}