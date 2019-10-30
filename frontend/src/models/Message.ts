import { Answer } from "./Answer";

export interface Message {
    id: string;
    text: string;
    image: string // Zu definieren
    answers: Answer[]
}