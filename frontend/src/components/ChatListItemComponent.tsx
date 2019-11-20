import { Chat } from "../models/Chat";
import { ListGroupItem, ListGroupItemHeading, ListGroupItemText, Badge } from "reactstrap";
import React from "react";
import AChatsHandler from "../logic/AChatsHandler";

export interface ChatListItemProps {
    chat: Chat;
    chatsHandler: AChatsHandler;
}

interface ChatListItemStates {
    lastMessageDifference: string;
}

export class ChatListItemComponent extends React.Component<ChatListItemProps, ChatListItemStates> {

    private interval: number = 0;

    state = {
        lastMessageDifference: ""
    }

    componentDidMount() {
        this.interval = window.setInterval(this.setDifference.bind(this), 60000);
    }

    componentWillReceiveProps() {
        this.setDifference();
    }

    componentWillUnmount() {
        window.clearInterval(this.interval);
    }

    private setCurrentChat() {
        this.props.chatsHandler.currentChat = this.props.chat;
    }

    private setDifference() {
        let lastMessage = this.props.chat.getLastMessage();
        if (lastMessage) {
            this.setState({ lastMessageDifference: lastMessage!.created.fromNow() });
        }
    }

    render() {
        let message = this.props.chat.getLastMessage();
        
        let text : string | JSX.Element = message === null ? "Keine Nachricht" : message!.text;

        let unreadMessages = this.props.chat.unreadMessages;
        let unreadBadge = <></>;

        if (unreadMessages > 0) {
            unreadBadge = <Badge color="primary" pill>{unreadMessages}</Badge>
            text = <strong>{text}</strong>
        }

        return <ListGroupItem onClick={this.setCurrentChat.bind(this)}>
            <div className="d-flex w-100 justify-content-between">
                <ListGroupItemHeading>{this.props.chat.partner.name}</ListGroupItemHeading>
                <small>{this.state.lastMessageDifference}</small>
            </div>
            <ListGroupItemText className="d-flex w-100 justify-content-between">
                {text}
                {unreadBadge}
            </ListGroupItemText>
        </ListGroupItem>;
    }
}