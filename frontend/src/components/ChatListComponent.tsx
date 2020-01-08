import React from "react";
import { Chat } from "../models/Chat";
import {Card, CardBody, ListGroup, CardTitle, Container} from "reactstrap";
import { DefaultComponentProps } from "../DefaultComponentProps";
import IChatsListener from "../logic/IChatsListener";
import { VoidLike } from "../logic/VoidLike";
import { ChatListItemComponent } from "./ChatListItemComponent";
import AChatsHandler from "../logic/AChatsHandler";

export interface ChatListProps extends DefaultComponentProps {
    isOpen: boolean;
    chatsHandler: AChatsHandler;
}

export interface ChatListStates {
    chats: Chat[]
    currentChat?: Chat
}

export class ChatsListComponent extends React.Component<ChatListProps, ChatListStates> implements IChatsListener {
    
    constructor(props: ChatListProps) {
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
        
        if (!this.props.isOpen) return <></>;

        return <Container>
            <Card color="secondary">
                <CardBody>
                    <div className="contact-profile">
                        <img className="contact-picture" src="http://emilcarlsson.se/assets/harveyspecter.png" alt="William Walker profile picture" />
                        <div className="contact-name">
                            William Walker
                        </div>
                    </div>
                </CardBody>
            </Card>
            <ListGroup>
                {
                    this.state.chats.map(c => <ChatListItemComponent chat={c} key={c.chatId} chatsHandler={this.props.chatsHandler} />)
                }
            </ListGroup>
        </Container>;
    }
}