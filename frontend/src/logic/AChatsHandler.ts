import IChatsListener, {isIChatsListener} from "./IChatsListener";
import IChatListener, {isIChatListener} from "./IChatListener";
import {Chat} from "../models/Chat";
import {SocketMessage} from "../models/SocketMessage";
import {VoidLike} from "./VoidLike";
import {IMessage, Message} from "../models/Message";
import {Answer} from "../models/Answer";
import IConnectionListener from "./IConnectionListener";
import {IErrorHandler, isIErrorHandler} from "./IErrorHandler";
import {NotificationHandler} from "./NotificationHandler";
import moment from "moment";
import {IGameOverListener, isGameOverListener} from "./IGameOverListener";

/**
 * Verwaltet alle Chats und Nachrichten und kommuniziert über die angehangenden Listener
 */
export default abstract class AChatsHandler {

    private chatsListener: IChatsListener[];
    private chatListener: IChatListener[];
    private chats: Chat[];
    private _currentChat?: Chat;
    private errorHandlers: IErrorHandler[];
    protected connectionListener: IConnectionListener;
    protected gameOverListeners: IGameOverListener[];

    constructor(connectionListener: IConnectionListener) {
        this.chatListener = [];
        this.chatsListener = [];
        this.chats = [];
        this.errorHandlers = [];
        this.gameOverListeners = [];
        this.connectionListener = connectionListener;
    }

    private sortChats(): void {
        this.chats = this.chats.sort((a, b) => {
            let timeA = Number.MAX_VALUE,
                timeB = Number.MAX_VALUE;

            if (a.getLastMessage() !== undefined) {
                timeA = a.getLastMessage()!.created.unix();
            }
            if (b.getLastMessage() !== undefined) {
                timeB = b.getLastMessage()!.created.unix();
            }

            return timeB - timeA;
        });
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
     * Bittet den Server, alle Nachrichten neu zu senden
     */
    public initMessages() {
        this.sendMessage({
            command: "Reinit"
        });
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
    public attach(listener: IChatListener | IChatsListener | IErrorHandler | IGameOverListener): void {
        if (isIChatListener(listener)) {
            this.chatListener.push(listener);
        } else if (isIChatsListener(listener)) {
            this.chatsListener.push(listener);
        } else if (isIErrorHandler(listener)) {
            this.errorHandlers.push(listener);
        } else if (isGameOverListener(listener)) {
            this.gameOverListeners.push(listener);
        }
    }

    /**
     * Entfernt den Listener vom Handler. Sollte in componentWillUnmount() aufgerufen werden
     * @param listener Listener
     */
    public detatch(listener: IChatsListener | IChatListener | IErrorHandler): void {
        if (isIChatListener(listener)) {
            this.chatListener = this.detatchListener(listener, this.chatListener)
        } else if (isIChatsListener(listener)) {
            this.chatsListener = this.detatchListener(listener, this.chatsListener);
        } else if (isIErrorHandler(listener)) {
            this.errorHandlers = this.detatchListener(listener, this.errorHandlers);
        }
    }

    /**
     * Sendet die Antwort an den Server
     * @param answer Antwort
     */
    public submitAnswer(answer: Answer, chatId: string, messageId: string): void {
        const answerId: number = answer.id;

        this.sendMessage({
            command: "SubmitAnswer",
            answerId,
            chatId,
            messageId
        });

        this.onMessage(chatId, {
            answers: [],
            created: moment(),
            id: messageId + "/" + answerId.toString(),
            isAnswer: true,
            image: "",
            text: answer.text,
            userHasRead: true
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
                messageId: message.id,
                chatId: this._currentChat.chatId
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
    private onMessage(chatId: string, message: IMessage) {
        let useMessage = new Message(message);
        const targetChat = this.chats.find(c => c.chatId === chatId);

        if (targetChat === undefined) {
            throw new Error(`Chat ${chatId} not found. Fatal Error`);
        }

        targetChat.addMessage(useMessage);

        // Nur Benachrichtigen, wenn Nachricht neu ist
        if (!message.userHasRead) {
            NotificationHandler.Instance.sendNotification(targetChat.partner.name, message.text);
            let audio = new Audio("eventually.mp3");
            audio.play().then(() => audio.remove());
        }

        this.sortChats();

        if (this._currentChat !== undefined && chatId === this._currentChat.chatId) {
            this.chatListener.forEach(c => c.onMessage(this._currentChat!, useMessage));
            this.readAllMessages();
        }
        this.chatsListener.forEach(c => c.onChatChange(this.chats));
    }

    /**
     * Fügt die übergebene Antwort der bestimmten Nachricht im bestimmen Chat hinzu und benachrichtigt
     * @param chatId Betroffener Chat
     * @param messageId Betroffene Nachricht
     * @param answer Gegebene Antwort
     */
    private onAnswer(chatId: string, messageId: string, answer: Answer) {
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
        if (process.env.NODE_ENV !== "production") {
            console.info("%cEingehende Nachricht: ", "color: white; background-color: green;", message);
        }

        switch (message.command) {
            case "CreateChat":
                this.chats.push(new Chat(message.chat));
                if (message.chat.messages !== undefined && message.chat.messages.length > 0) {
                    for (let m of message.chat.messages) {
                        this.onMessage(message.chat.chatId, m);
                    }
                }
                this.sortChats();
                this.chatsListener.forEach(c => c.onChatChange(this.chats));
                break;
            case "AddMessage":
                this.onMessage(message.chatId, message.message);
                break;
            case "AddAnswer":
                this.onAnswer(message.chatId, message.messageId, message.answer);
                break;
            case "ChangePoints":
                this.chatsListener.forEach(c => c.onPointsChange(message.points));
                break;
            case "HandleError":
                this.errorHandlers.forEach(e => e.onError(message.message));
                break;
            case "WebSocketCreated":
                console.log("WebSocket erzeugt. Juhu");
                break;
            case "GameOver":
                console.log("GameOver....");
                this.gameOverListeners.forEach(l => l.onGameOver());
                break;
            default:
                throw new Error(`The Command ${message.command} is unknown`);
        }
    }

}