import moment from "moment";

export class NotificationHandler {

    private static readonly DURATION_BETWEEN_NOTIFICATIONS = 10;

    private static instance: NotificationHandler;
    private lastNotification: moment.Moment | undefined;

    private constructor() {
        // Nur prüfen, wenn Notifications zur Verfügung stehen
        if (Notification !== undefined && Notification.permission !== "granted") {
            Notification.requestPermission();
        }
    }

    private get isEnabled(): boolean {
        return Notification !== undefined && Notification.permission === "granted";
    }

    private get allowed(): boolean {
        if (this.lastNotification === undefined) {
            this.lastNotification = moment();
            return true;
        }

        const duration = moment.duration(moment().from(this.lastNotification));
        if (duration.asSeconds() > NotificationHandler.DURATION_BETWEEN_NOTIFICATIONS) {
            this.lastNotification = moment();
            return true;
        }

        return false;
    }

    public sendNotification(title: string, body: string): void {
        if (this.isEnabled && this.allowed) {
            const notification = new Notification(title, {
                body
            });
        }
    }

    public static get Instance(): NotificationHandler {
        return this.instance || (this.instance = new this());
    }
}