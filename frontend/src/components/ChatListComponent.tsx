import React from "react";
import { Chat } from "../models/Chat";
import { Card, CardBody, ListGroup, Container, Progress } from "reactstrap";
import { DefaultComponentProps } from "../DefaultComponentProps";
import IChatsListener from "../logic/IChatsListener";
import { VoidLike } from "../logic/VoidLike";
import { ChatListItemComponent } from "./ChatListItemComponent";
import AChatsHandler from "../logic/AChatsHandler";
import { Points } from "../models/Points";
import setWindowTitle from "../logic/setWindowTitle";

export interface ChatListProps extends DefaultComponentProps {
    isOpen: boolean;
    chatsHandler: AChatsHandler;
}

export interface ChatListStates {
    chats: Chat[]
    currentChat?: Chat
    currentPoints: Points;
}

export class ChatsListComponent extends React.Component<ChatListProps, ChatListStates> implements IChatsListener {

    constructor(props: ChatListProps) {
        super(props);
        this.state = {
            chats: [],
            currentChat: undefined,
            currentPoints: {
                chefSatisfaction: 0,
                customerExperience: 0,
                budget: 0
            }
        };
    }

    onPointsChange(currentPoints: Points): VoidLike {
        this.setState({ currentPoints });
    }

    onCurrentChatChange(currentChat?: Chat): VoidLike {
        this.setState({ currentChat });
    }

    onChatChange(chats: Chat[]): VoidLike {
        this.setState({ chats });
    }

    componentDidMount() {
        setWindowTitle("Chats von William Walker");
        this.props.chatsHandler.attach(this);
    }

    componentWillUnmount() {
        this.props.chatsHandler.detatch(this);
    }

    render() {

        if (!this.props.isOpen) return <></>;

        const { chats, currentPoints } = this.state;

        return <Container>
            <Card color="secondary">
                <CardBody className="contact-profile">
                    <img className="contact-picture" src="http://emilcarlsson.se/assets/harveyspecter.png" alt="William Walker profile picture" />
                    <div className="contact-name">
                        William Walker
                    </div>
                </CardBody>
                <CardBody>
                    <div>
                        Kundenerfahrungen
                        <Progress value={currentPoints.customerExperience} />
                    </div>
                    <div>
                        Chefzufriedenheit
                        <Progress value={currentPoints.chefSatisfaction} />
                    </div>
                    <div>
                        Firmenbudget
                        <Progress value={currentPoints.budget} />
                    </div>
                </CardBody>
            </Card>
            <ListGroup>
                {
                    chats.map(c => <ChatListItemComponent chat={c} key={c.chatId} chatsHandler={this.props.chatsHandler} />)
                }
            </ListGroup>
        </Container>;
    }
}