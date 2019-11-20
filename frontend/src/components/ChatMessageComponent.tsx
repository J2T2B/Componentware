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
        if (this.state.chat === undefined)
            return <Container>Kein Chat</Container>
        else
            return <Container>
                <div className="contact-profile">
                    <img className="contact-picture" src={ this.state.chat!.partner.imageUrl } alt={ this.state.chat!.partner.name } />
                    <div className="contact-name">{ this.state.chat!.partner.name }</div>
                </div>
                <div className="messages col-md-12">
                    {
                        this.state.chat && this.state.chat.messages.map(m => {
                            return <div className="row">
                                    <div className="message">
                                        <div className={"bubble"+(m.isAnswer ? " reply":" receive")}>
                                            <p>{m.text}</p>
                                            <p className="time">{m.created.fromNow()}</p>
                                        </div>
                                    </div>
                                </div>
                        })
                    }
                </div>
                <div className="message-input"></div>
            </Container>
            ;
    }
}

// todo: Harvey Specter => conatct name
