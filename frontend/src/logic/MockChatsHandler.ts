import AChatsHandler from "./AChatsHandler";
import { SocketMessage } from "../models/SocketMessage";
import { VoidLike } from "./VoidLike";

export class MockChatsHandler extends AChatsHandler {

    public async connect(): Promise<boolean> {
        (window as any).simulateMessage = this.simulateMessage.bind(this);
        console.log("Globale Funktion simulateMessage(...) hinzugefügt");
        return true;
    }
    
    protected sendMessage(socketMessage: SocketMessage): VoidLike {
        console.log("Nachricht an Server", socketMessage);
    }

    public simulateMessage(message: SocketMessage) {
        this.onSocketMessage(message);
    }

}