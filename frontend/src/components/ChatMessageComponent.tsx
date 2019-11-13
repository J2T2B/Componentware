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
                <div className="messages col-md-12">
                    <div className="row">
                        <div className="message">
                            <div className="bubble receive">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Atque facere fugit inventore pariatur voluptatibus. Ad blanditiis, corporis deserunt mollitia nobis numquam odio similique. Aliquid atque culpa deleniti dolor, necessitatibus recusandae!</p>
                                <p className="time">vor 10 min</p>
                            </div>
                        </div>
                    </div>
                    <div className="row">
                        <div className="message">
                            <div className="bubble reply">
                                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab at et ex exercitationem fuga in incidunt ipsa magnam molestiae mollitia neque nisi non optio placeat porro, suscipit unde. Reprehenderit, tenetur?</p>
                                <p className="time">vor 5 min</p>
                            </div>
                        </div>
                    </div>

                    {
                        this.state.chat && this.state.chat.messages.map(m => {
                            return <div className="row">
                                    <div className="message">
                                        <div className={"bubble"+(m.isAnswer ? " reply":" receive")}>
                                            <p>{m.text}</p>
                                            <p className="time">{m.created}</p>
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
