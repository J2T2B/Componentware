export class NotificationHandler {

    private static instance: NotificationHandler;

    private constructor() {
        // Nur prüfen, wenn Notifications zur Verfügung stehen
        if (Notification !== undefined && Notification.permission !== "granted") {
            Notification.requestPermission();
        }
    }

    private get isEnabled(): boolean {
        return Notification !== undefined && Notification.permission === "granted";
    }

    public sendNotification(title: string, body: string): void {
        if (this.isEnabled) {
            const notification = new Notification(title, {
                body
            });
        }
    }

    public static get Instance(): NotificationHandler {
        return this.instance || (this.instance = new this());
    }
}