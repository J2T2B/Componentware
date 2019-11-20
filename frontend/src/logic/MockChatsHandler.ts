import AChatsHandler from "./AChatsHandler";
import { SocketMessage } from "../models/SocketMessage";
import { VoidLike } from "./VoidLike";

export class MockChatsHandler extends AChatsHandler {

    public async connect(): Promise<boolean> {
        (window as any).simulateMessage = this.simulateMessage.bind(this);
        console.log("Globale Funktion simulateMessage(...) hinzugefügt");

        window.setTimeout(this.quereMessage.bind(this), 2000);

        return true;
    }

    protected sendMessage(socketMessage: SocketMessage): VoidLike {
        console.log("Nachricht an Server", socketMessage);
    }

    public simulateMessage(message: SocketMessage | SocketMessage[]) {
        if (Array.isArray(message)) {
            message.forEach(m => this.onSocketMessage(m));
        }
        else {
            this.onSocketMessage(message);
        }
    }

    private quereMessage() {
        for (let i = 0; i < 5; i++) {
            this.simulateMessage(
                {
                    command: "CreateChat",
                    chat: {
                        chatId: i,
                        partner: {
                            name: "Kunde " + (i + 1),
                            imageUrl: "",
                        },
                        messages: [],
                    }
                });

            for (let j = 0; j < 10; j++) {
                this.simulateMessage({
                    command: "AddMessage",
                    chatId: i,
                    message: {
                        id: ((i*10)+j).toString(),
                        text: "Hallo Will "+j,
                        image: "",
                        answers: [],
                        userHasRead: false,
                        created: (new Date().getTime() - 60000),
                        isAnswer: false
                    }
                });
            }
        }        
    }

}