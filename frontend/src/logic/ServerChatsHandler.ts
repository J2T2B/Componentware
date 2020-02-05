import AChatsHandler from "./AChatsHandler";
import {SocketMessage} from "../models/SocketMessage";
import IConnectionListener from "./IConnectionListener";

export class ServerChatsHandler extends AChatsHandler {

    private readonly baseUrl: string;
    private readonly token: string;
    private wsConnection?: WebSocket;
    private retrys: number = 0;

    constructor(connectionListener: IConnectionListener, baseUrl: string, token: string) {
        super(connectionListener);
        this.baseUrl = baseUrl;
        this.token = token;
    }

    connect(): Promise<boolean> {
        this.retrys++;
        const url = new URL(`${this.baseUrl}/game/${this.token}`);
        url.protocol = "ws:";

        const error = this.onCloseOrError.bind(this);

        return new Promise<boolean>((res) => {
            this.wsConnection = new WebSocket(url.toString());
            this.wsConnection.onclose = error;
            this.wsConnection.onerror = error;
            this.wsConnection.onmessage = this.onWebSocketMessage.bind(this);
            this.wsConnection.onopen = () => {
                console.log("Websocket verbunden!");
                res(true);
                this.connectionListener.onConnect(this);
                this.retrys = 0;
            };
        });
    }

    protected sendMessage(socketMessage: SocketMessage): void | Promise<void> {
        if (process.env.NODE_ENV !== "production") {
            console.info("%cAusgehende Nachricht: ", "color: white; background-color: red;", socketMessage);
        }

        // Socket steht nicht bereit. Try later again
        if (this.wsConnection === undefined || this.wsConnection.readyState !== 1) {
            window.setTimeout(() => this.sendMessage(socketMessage), 1000);
        } else {
            this.wsConnection.send(JSON.stringify(socketMessage));
        }
    }

    private onCloseOrError() {
        if (this.retrys < 5) {
            this.connectionListener.onDisconnect();
            console.log("Websocket getrennt. Versuche erneute Verbindung...");
            // Nicht rekursiv starten...
            window.setTimeout(this.connect.bind(this), 0);
        } else {
            // Neuer Loginstate
            this.connectionListener.onReset();
        }
    }

    private onWebSocketMessage(ev: MessageEvent) {
        const plainData = ev.data as string;
        const message = JSON.parse(plainData) as SocketMessage;
        this.onSocketMessage(message);
    }

}