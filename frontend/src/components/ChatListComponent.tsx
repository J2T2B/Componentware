import React from "react";
import { Chat } from "../models/Chat";
import { Card, CardBody, ListGroup, CardTitle } from "reactstrap";
import { DefaultComponentProps } from "../DefaultComponentProps";
import IChatsListener from "../logic/IChatsListener";
import { VoidLike } from "../logic/VoidLike";
import { ChatListItemComponent } from "./ChatListItemComponent";

export interface ChatListStates {
    chats: Chat[]
    currentChat?: Chat
}

export class ChatsListComponent extends React.Component<DefaultComponentProps, ChatListStates> implements IChatsListener {
    
    constructor(props: DefaultComponentProps) {
        super(props);
        this.state = {
            chats: [],
            currentChat: undefined
        };
    }
    

    onCurrentChatChange(currentChat?: Chat): VoidLike {
        this.setState({ currentChat });
    }
    
    onChatChange(chats: Chat[]): VoidLike {
        this.setState({ chats });
    }

    componentDidMount() {
        this.props.chatsHandler.attach(this);
    }

    componentWillUnmount() {
        this.props.chatsHandler.detatch(this);
    }

    render() {
        return <Card>
            <CardBody>
                <CardTitle>William Walker</CardTitle>
            </CardBody>
            <ListGroup>
                {
                    this.state.chats.map(c => <ChatListItemComponent chat={c} key={c.chatId} />)
                }
            </ListGroup>
        </Card>;
    }
}