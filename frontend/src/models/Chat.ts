import { Message } from "./Message";
import { Partner } from "./Partner";

export class Chat {
    messages: Message[];
    partner: Partner;

    constructor(partner: Partner) {
        this.partner = partner;
        this.messages = [];
    }

    addMessage(message: Message) {
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
}