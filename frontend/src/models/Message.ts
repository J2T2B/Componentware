import { Answer } from "./Answer";
import moment from "moment";

export interface IMessage {
    id: string;
    text: string;
    image: string // Zu definieren
    answers: Answer[]
    userHasRead: boolean;
    created: number | moment.Moment;
}

export class Message implements IMessage {
    id: string;   
    text: string;
    image: string;
    answers: Answer[];
    userHasRead: boolean;
    created: moment.Moment;

    constructor(init: IMessage) {
        this.id = init.id;
        this.text = init.text;
        this.image = init.image;
        this.answers = init.answers;
        this.userHasRead = init.userHasRead;
        this.created = moment(init.created);
    }
}