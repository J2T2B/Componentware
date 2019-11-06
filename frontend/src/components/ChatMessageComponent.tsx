import React from "react";
import { Container } from "reactstrap";
import { Chat } from "../models/Chat";
import IChatListener from "../logic/IChatListener";
import {DefaultComponentProps} from "../DefaultComponentProps";
import {Message} from "../models/Message";

export interface ChatMessageStates {
    chat?: Chat;
}

export class ChatMessageComponent extends React.Component<DefaultComponentProps, ChatMessageStates> implements IChatListener {

    constructor(props: DefaultComponentProps) {
        super(props);
        this.state = {
            chat: undefined
        };
    }

    onChatChange(chat: Chat): void | Promise<void> {
        this.setState({ chat });
    }

    onMessage(chat: Chat, message: Message): void | Promise<void> {
        this.setState({ chat });
    }

    componentDidMount() {
        this.props.chatsHandler.attach(this);
    }

    componentWillUnmount() {
        this.props.chatsHandler.detatch(this);
    }

    render() {
        return <Container>
                <div className="contact-profile">
                    <img className="contact-picture" src="http://emilcarlsson.se/assets/harveyspecter.png" alt="Harvey Specter" />
                    <div className="contact-name">Harvey Specter</div>
                </div>
                <div className="messages">
                    <div className="message">
                        Syn
                    </div>
                    <div className="message reply">
                        Ack
                    </div>
                    {/*{*/}
                    {/*    props.chat.messages.map(m => {*/}
                    {/*        return <div className={"message"+(m.answer ? " answer":" reply")}>*/}
                    {/*            {m.text}*/}
                    {/*        </div>*/}
                    {/*    })*/}
                    {/*}*/}
                </div>
                <div className="message-input"></div>
            </Container>
        ;
    }
}

// todo: Harvey Specter => conatct name
