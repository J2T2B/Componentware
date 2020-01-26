import IConnectionListener from "./IConnectionListener";
import {ServerChatsHandler} from "./ServerChatsHandler";
import {MockChatsHandler} from "./MockChatsHandler";

export class Connector {

    private readonly listener: IConnectionListener;

    private readonly baseUrl: string;

    private readonly isTestMode: boolean;

    constructor(baseUrl: string, listener: IConnectionListener, testMode: boolean = false) {
        this.listener = listener;
        this.baseUrl = baseUrl;
        this.isTestMode = testMode;
    }

    private async connectToWebSocket(token: string) {
        const chatsHandler = this.isTestMode ? new MockChatsHandler(this.listener) : new ServerChatsHandler(this.listener, this.baseUrl, token);
        await chatsHandler.connect();
    }

    async login(username: string, password: string): Promise<void> {
        const url = this.baseUrl + "/api/login";
        const credentials = {username, password};

        const request = await fetch(url, {
            body: JSON.stringify(credentials),
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            }
        });

        const response = await request.text();

        if (request.status === 200) {
            await this.connectToWebSocket(response);
        } else {
            throw new Error(response);
        }
    }

    async register(username: string, password: string): Promise<void> {
        const url = this.baseUrl + "/api/register";
        const credentials = {username, password};

        const request = await fetch(url, {
            body: JSON.stringify(credentials),
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        });

        const response = await request.text();

        if (request.status === 200 || request.status === 204) {
            await this.login(username, password);
        } else {
            throw new Error(response);
        }
    }

}