import AChatsHandler from "./AChatsHandler";

export default interface IConnectionListener {
    onConnect(chatsHandler: AChatsHandler): void;
    onDisconnect(): void;
}