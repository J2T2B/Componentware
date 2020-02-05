import AChatsHandler from "./AChatsHandler";
import { SocketMessage } from "../models/SocketMessage";
import { VoidLike } from "./VoidLike";

export class MockChatsHandler extends AChatsHandler {

    private intervalId: number | undefined;

    public async connect(): Promise<boolean> {
        (window as any).simulateMessage = this.simulateMessage.bind(this);
        console.log("Globale Funktion simulateMessage(...) hinzugefügt");

        window.setTimeout(this.quereMessage.bind(this), 2000);

        this.connectionListener.onConnect(this);
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
                        chatId: i.toString(),
                        partner: {
                            name: "Kunde " + (i + 1),
                            imageUrl: "http://emilcarlsson.se/assets/harveyspecter.png",
                        },
                        messages: [],
                    }
                });

            for (let j = 0; j < 10; j++) {
                let isAnswer = (Math.random() < 0.5);
                let answers = [];
                if (!isAnswer) {
                    let a = Math.random() * 4 + 1;
                    for (let k = 0; k < a; k++) {
                        answers.push({
                            id: j * 10 + k,
                            text: "Antwort " + j + '' + k
                        });
                    }
                }
                this.simulateMessage({
                    command: "AddMessage",
                    chatId: i.toString(),
                    message: {
                        id: ((i * 10) + j).toString(),
                        text: "Hallo Will " + ((i * 10) + j).toString(),
                        image: "",
                        answers: answers,
                        userHasRead: false,
                        created: (new Date().getTime() - 99999999),
                        isAnswer: isAnswer
                    }
                });
            }
        }

        this.intervalId = window.setInterval(this.upgradePoints.bind(this), 5000);
    }

    private upgradePoints() {
        const rand = () => Math.round(Math.random() * 100);
        this.simulateMessage({
            command: "ChangePoints",
            points: {
                budget: rand(),
                chefSatisfaction: rand(),
                customerExperience: rand()
            }
        })
    }

}
