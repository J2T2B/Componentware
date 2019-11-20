import IChatsListener, { isIChatsListener } from "./IChatsListener";
import IChatListener, { isIChatListener } from "./IChatListener";
import { Chat } from "../models/Chat";
import { SocketMessage } from "../models/SocketMessage";
import { VoidLike } from "./VoidLike";
import { IMessage, Message } from "../models/Message";
import { Answer } from "../models/Answer";

/**
 * Verwaltet alle Chats und Nachrichten und kommuniziert über die angehangenden Listener
 */
export default abstract class AChatsHandler {

    private chatsListener: IChatsListener[];
    private chatListener: IChatListener[];
    private chats: Chat[]
    private _currentChat?: Chat

    constructor() {
        this.chatListener = [];
        this.chatsListener = [];
        this.chats = [];
    }

    public get currentChat(): Chat | undefined {
        return this._currentChat;
    }

    public set currentChat(chat: Chat | undefined) {
        this._currentChat = chat;
        if (chat !== undefined) {
            this.readAllMessages();
            this.chatListener.forEach(c => c.onChatChange(this._currentChat!));
        }

        this.chatsListener.forEach(c => c.onCurrentChatChange(this._currentChat));
    }

    /**
     * Verbindet mit dem Server (oder dem Mock Server)
     */
    public abstract connect(): Promise<boolean>;

    /**
     * Sendet die Message an den Server
     * @param socketMessage Abzusendene Message
     */
    protected abstract sendMessage(socketMessage: SocketMessage): VoidLike;

    /**
     * Hängt den Listener an den Handler. Sollte in componentDidMount() aufgerufen werden
     * @param listener Listener
     */
    public attach(listener: IChatListener | IChatsListener): void {
        if (isIChatListener(listener)) {
            this.chatListener.push(listener);
        }
        else if (isIChatsListener(listener)) {
            this.chatsListener.push(listener);
        }
    }

    /**
     * Entfernt den Listener vom Handler. Sollte in componentWillUnmount() aufgerufen werden
     * @param listener Listener
     */
    public detatch(listener: IChatsListener | IChatListener): void {
        if (isIChatListener(listener)) {
            this.chatListener = this.detatchListener(listener, this.chatListener)
        }
        else if (isIChatsListener(listener)) {
            this.chatsListener = this.detatchListener(listener, this.chatsListener);
        }
    }

    /**
     * Sendet die Antwort an den Server
     * @param answer Antwort
     */
    public submitAnswer(answer: Answer | number) : void {
        let answerId : number;
        if (typeof(answer) === "number") {
            answerId = answer;
        }
        else {
            answerId = answer.id;
        }

        this.sendMessage({
            command: "SubmitAnswer",
            answerId
        });
    }

    /**
     * Makiert alle Nachrichten im aktuellen Chat als gelesen
     */
    public readAllMessages() {
        if (this._currentChat === undefined) {
            throw new Error("No current Chat.");
        }

        let targetMessages = this._currentChat.messages.filter(m => !m.userHasRead);
        for (let message of targetMessages) {
            message.userHasRead = true;
            this.sendMessage({
                command: "ReadMessage",
                messageId: message.id
            });
        }
        this.chatsListener.forEach(l => l.onChatChange(this.chats));
    }

    /**
     * Entfernt den Listener aus dem Array (gibt das neue Array zurück)
     * @param obj Listener
     * @param collection Listenercollection
     */
    private detatchListener<T>(obj: T, collection: T[]): T[] {
        return collection.filter(o => o !== obj);
    }

    /**
     * Fügt die Nachricht dem übergebenen Chat hinzu
     * @param chatId Betroffene ChatId
     * @param message Neue Nachricht
     */
    private onMessage(chatId: number, message: IMessage) {
        let useMessage = new Message(message);

        if (this._currentChat !== undefined && chatId === this._currentChat.chatId) {
            this._currentChat.addMessage(useMessage);
            this.chatListener.forEach(c => c.onMessage(this._currentChat!, useMessage));
        }
        else {
            let targetChat = this.chats.find(c => c.chatId === chatId);
            if (targetChat !== undefined) {
                targetChat.addMessage(useMessage);
            }
            else {
                throw new Error(`Chat ${chatId} not found. Fatal Error`);
            }
        }

        // Notification
        let audio = new Audio("eventually.mp3");
        audio.play().then(()=>audio.remove());

        this.chatsListener.forEach(c => c.onChatChange(this.chats));
    }

    /**
     * Fügt die übergebene Antwort der bestimmten Nachricht im bestimmen Chat hinzu und benachrichtigt
     * @param chatId Betroffener Chat
     * @param messageId Betroffene Nachricht
     * @param answer Gegebene Antwort
     */
    private onAnswer(chatId: number, messageId: string, answer: Answer) {
        let targetChat = this.chats.find(c => c.chatId === chatId);
        if (targetChat === undefined) {
            throw new Error(`Chat ${chatId} not found. Fatal Error`);
        }

        let targetMessage = targetChat.messages.find(m => m.id === messageId);
        if (targetMessage === undefined) {
            throw new Error(`Message ${messageId} on Chat ${chatId} not found. Fatal Error`);
        }

        targetMessage.answers.push(answer);

        if (this._currentChat !== undefined && this._currentChat.chatId === chatId) {
            this.chatListener.forEach(c => c.onMessage(this._currentChat!, targetMessage!));
        }
    }

    /**
     * Bearbeitet die vom Server bekommene Nachricht und führt Aktionen aus
     * @param message Vom Server bekommene Message
     */
    protected onSocketMessage(message: SocketMessage) {
        switch (message.command) {
            case "CreateChat":
                this.chats.push(new Chat(message.chat));
                this.chatsListener.forEach(c => c.onChatChange(this.chats));
                break;
            case "AddMessage":
                this.onMessage(message.chatId, message.message);
                break;
            case "AddAnswer":
                this.onAnswer(message.chatId, message.messageId, message.answer);
                break;
            default:
                throw new Error(`The Command ${message.command} is unknown`);
        }
    }

}