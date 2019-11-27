import React from "react";
import {Button, Container, ListGroup, ListGroupItem} from "reactstrap";
import { Chat } from "../models/Chat";
import IChatListener from "../logic/IChatListener";
import {DefaultComponentProps} from "../DefaultComponentProps";
import {Message} from "../models/Message";
import moment from "moment";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPaperPlane, faSmile} from "@fortawesome/free-regular-svg-icons";

export interface ChatMessageStates {
    chat?: Chat;
    openAnswers: boolean;
    chosenAnswer?: number;
}

export class ChatMessageComponent extends React.Component<DefaultComponentProps, ChatMessageStates> implements IChatListener {

    private messagesEnd: any;

    constructor(props: DefaultComponentProps) {
        super(props);
        this.state = {
            chat: undefined,
            openAnswers: false,
        };
        this.openAnswers = this.openAnswers.bind(this)
        this.onAnswerChoose = this.onAnswerChoose.bind(this)
    }

    onChatChange(chat: Chat): void | Promise<void> {
        this.setState({ chat });
    }

    onMessage(chat: Chat, message: Message): void | Promise<void> {
        this.setState({ chat });
    }

    scrollToBottom() {
        this.messagesEnd.scrollIntoView({ behavior: "smooth" });
    }

    componentDidMount() {
        this.props.chatsHandler.attach(this);
        this.scrollToBottom();
    }

    componentWillUnmount() {
        this.props.chatsHandler.detatch(this);
        this.scrollToBottom();
    }

    openAnswers() {
        // let answers = this.state.chat!.getLastMessage()!.answers;
        // ('div.messages').
        this.setState(o => {
            return {openAnswers: !o.openAnswers};
        });
        if (this.state.chosenAnswer === undefined) {
            this.setState({chosenAnswer: 1});
        }
    }

    onAnswerChoose(id: number) {
        console.log(id);
       this.setState({chosenAnswer: id, openAnswers: false});
    }

    render() {
        if (this.state.chat === undefined) {
            return <Container>
                <div className="contact-profile">
                    <img className="contact-picture" />
                    <div className="contact-name">
                        <em>Kein Chat ausgewählt</em>
                    </div>
                </div>
                <div className="messages col-md-12">
                    <div className="row">
                        <div className="message">
                            <em>Keine Nachrichten</em>
                        </div>
                    </div>
                    <div style={{ float:"left", clear: "both" }}
                         ref={(el) => { this.messagesEnd = el; }}>
                    </div>
                </div>
            </Container>
        }
        else {
            return <Container>
                <div className="contact-profile">
                    <img className="contact-picture" src={this.state.chat!.partner.imageUrl}
                         alt={this.state.chat!.partner.name}/>
                    <div className="contact-name">{this.state.chat!.partner.name}</div>
                </div>
                <div className="messages col-md-12">
                    {
                        this.state.chat && this.state.chat.messages.map(m => {
                            return <div className="row">
                                <div className="message">
                                    <div className={"bubble" + (m.isAnswer ? " reply" : " receive")}>
                                        <p>{m.text}</p>
                                        <p className="time">
                                            {m.created.isBefore(moment().startOf('day')) && m.created.format('L')}
                                            &nbsp;
                                            {m.created.format('HH:mm')}
                                        </p>
                                    </div>
                                </div>
                            </div>
                        })
                    }
                    <div style={{ float:"left", clear: "both" }}
                         ref={(el) => { this.messagesEnd = el; }}>
                    </div>
                    {
                        this.state.openAnswers ?
                            <div className="row">
                                <div className="col-md-1">&nbsp;</div>
                                <div className="col-md-10">
                                    <ListGroup>
                                        <ListGroupItem tag="button" className={this.state.chosenAnswer === 1 ? 'active' : ''} onClick={() => this.onAnswerChoose(1)}>
                                            Antwort 1
                                        </ListGroupItem>
                                        <ListGroupItem tag="button" className={this.state.chosenAnswer === 2 ? 'active' : ''} onClick={() => this.onAnswerChoose(2)}>
                                            Antwort 2
                                        </ListGroupItem>
                                        <ListGroupItem tag="button" className={this.state.chosenAnswer === 3 ? 'active' : ''} onClick={() => this.onAnswerChoose(3)}>
                                            Antwort 3
                                        </ListGroupItem>
                                    </ListGroup>
                                </div>
                                <div className="col-md-1">&nbsp;</div>
                            </div>
                            :
                            ''
                        // && this.state.chat.getLastMessage() && this.state.chat.getLastMessage().answers.map()
                    }
                </div>
                <div className="message-input">
                    <div className="row text-center">
                        <div className="col-md-1">
                            <Button>
                                <FontAwesomeIcon icon={faSmile} />
                            </Button>
                        </div>
                        <div className="col-md-10">
                            <div className="form-control" onClick={this.openAnswers}>
                                {
                                    // todo: get chosen answer
                                    !this.state.chosenAnswer ? '' : (this.state.chosenAnswer === 1 ? 'Antwort 1' : 'was anderes')
                                }
                            </div>
                        </div>
                        <div className="col-md-1">
                            <Button color="primary">
                                <FontAwesomeIcon icon={faPaperPlane} />
                            </Button>
                        </div>
                    </div>
                </div>
            </Container>
                ;
        }
    }
}

// todo: Harvey Specter => conatct name
