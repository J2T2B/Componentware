import AChatsHandler from "./AChatsHandler";
import {SocketMessage} from "../models/SocketMessage";
import IConnectionListener from "./IConnectionListener";

export class ServerChatsHandler extends AChatsHandler {

    private readonly baseUrl: string;
    private readonly token: string;

    constructor(connectionListener: IConnectionListener, baseUrl: string, token: string) {
        super(connectionListener);
        this.baseUrl = baseUrl;
        this.token = token;
    }

    connect(): Promise<boolean> {
        // TODO: Connect to Server
        alert("Connect to WebSocket: " + this.baseUrl + "/game/" + this.token);
        return new Promise<boolean>((res, rej) => rej(true));
    }

    protected sendMessage(socketMessage: SocketMessage): void | Promise<void> {
        return undefined;
    }

    // Call onSocketMessage(MESSAGE)

}