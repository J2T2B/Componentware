import { IMessage, Message } from "./Message";
import { Partner } from "./Partner";

export interface IChat {
    messages: IMessage[] | Message[];
    partner: Partner;
    chatId: number;
}

export class Chat implements IChat {
    messages: Message[];
    partner: Partner;
    chatId: number;

    constructor(init: IChat) {
        this.chatId = init.chatId;
        this.partner = init.partner;
        this.messages = [];
    }

    addMessage(message: Message) {
        this.messages = this.messages.sort((a,b) => b.created.unix() - a.created.unix());
        this.messages.push(message);
    }

    getLastMessage() : Message | null {
        if (this.messages.length > 0) {
            return this.messages[this.messages.length-1];
        }
        else {
            return null;
        }
    }

    public get unreadMessages() : number {
        let unreadMessages = 0;

        this.messages.forEach(m => {
            if (!m.userHasRead) {
                unreadMessages++;
            }
        });

        return unreadMessages;
    }
}