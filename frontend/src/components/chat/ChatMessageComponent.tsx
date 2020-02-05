import React from "react";
import {Button, Container, ListGroup, ListGroupItem, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import {Chat} from "../../models/Chat";
import IChatListener from "../../logic/IChatListener";
import {DefaultComponentProps} from "../../DefaultComponentProps";
import {Message} from "../../models/Message";
import moment from "moment";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faPaperPlane, faSmile} from "@fortawesome/free-regular-svg-icons";
import {Answer} from "../../models/Answer";
import {text} from "@fortawesome/fontawesome-svg-core";

export interface ChatMessageStates {
    chat?: Chat;
    openAnswers: boolean;
    chosenAnswer?: Answer;
}

export class ChatMessageComponent extends React.Component<DefaultComponentProps, ChatMessageStates> implements IChatListener {

    private messagesEnd: any;

    constructor(props: DefaultComponentProps) {
        super(props);
        this.state = {
            chat: undefined,
            openAnswers: false,
        };
        this.openAnswers = this.openAnswers.bind(this);
        this.onAnswerChoose = this.onAnswerChoose.bind(this);
        this.onSendMessage = this.onSendMessage.bind(this);
    }

    onChatChange(chat: Chat): void | Promise<void> {
        this.setState({chat, chosenAnswer: undefined});
    }

    onMessage(chat: Chat, message: Message): void | Promise<void> {
        this.setState({chat, chosenAnswer: undefined});
    }

    scrollToBottom() {
        this.messagesEnd.scrollIntoView({behavior: "smooth"});
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
        let answer = this.state.chat!.getLastMessage()!.answers[0];
        this.setState(o => {
            return {openAnswers: !o.openAnswers};
        });
        if (this.state.chosenAnswer === undefined) {
            const chosenAnswer = (answer === undefined) ? undefined : answer;
            this.setState({chosenAnswer});
        }
    }

    onAnswerChoose(chosenAnswer: Answer) {
        this.setState({chosenAnswer, openAnswers: false});
    }

    toggleModal() {
        this.setState({openAnswers: !this.openAnswers});
    }

    onSendMessage() {
        if (this.state.chosenAnswer !== undefined) {
            const answer = this.state.chosenAnswer;
            this.props.chatsHandler.submitAnswer(answer!, this.state.chat!.chatId, this.state.chat!.getLastMessage()!.id);
        }
    }

    ln2br(text: string) {
        return {__html: text.replace("\n", "<br />")};
    }

    render() {
        if (this.state.chat === undefined) {
            return <Container>
                <div className="contact-profile">
                    <img className="contact-picture" alt=""/>
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
                    <div style={{float: "left", clear: "both"}}
                         ref={(el) => {
                             this.messagesEnd = el;
                         }}>
                    </div>
                </div>
            </Container>
        } else {
            return <Container>
                <div className="contact-profile">
                    <img className="contact-picture" src={this.state.chat!.partner.imageUrl != "" ? this.state.chat!.partner.imageUrl : "./default.jpg"}
                         alt=""/>
                    <div className="contact-name">{this.state.chat!.partner.name}</div>
                </div>
                <div className="messages col-md-12">
                    {
                        this.state.chat && this.state.chat.messages.map(m => {
                            return <div className="row">
                                <div className="message">
                                    <div className={"bubble" + (m.isAnswer ? " reply" : " receive")}>
                                        <div className="content">
                                            <p dangerouslySetInnerHTML={this.ln2br(m.text)} />
                                            <p className="time">
                                                {m.created.isBefore(moment().startOf('day')) && m.created.format('L')}
                                                &nbsp;
                                                {m.created.format('HH:mm')}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        })
                    }
                    <div style={{float: "left", clear: "both", margin: "10px"}}
                         ref={(el) => {
                             this.messagesEnd = el;
                         }}>
                    </div>
                    {
                        this.state.chat.getLastMessage() && (this.state.chat.getLastMessage()!.answers.length > 0) ?
                            <div className="row">
                                <Modal isOpen={this.state.openAnswers} toggle={() => this.toggleModal()}>
                                    <ModalHeader toggle={() => this.toggleModal()}>Ihre
                                        Antwortmöglichkeiten</ModalHeader>
                                    <ModalBody>
                                        <ListGroup>
                                            {
                                                this.state.chat.getLastMessage()!.answers.map(a => {
                                                    return <ListGroupItem tag="button"
                                                                          className={this.state.chosenAnswer === a ? 'active' : ''}
                                                                          onClick={() => this.onAnswerChoose(a)}
                                                                          id={"answer-" + a.id}>
                                                        {a.text}
                                                    </ListGroupItem>
                                                })
                                            }
                                        </ListGroup>
                                    </ModalBody>
                                    <ModalFooter>
                                        <Button color="secondary" onClick={() => this.toggleModal()}>Abbrechen
                                        </Button>
                                    </ModalFooter>
                                </Modal>
                            </div>
                            :
                            ''
                    }

                </div>
                <div className="message-input">
                    <div className="row text-center">
                        <div className="col-md-1">
                            <Button color="primary" onClick={() => console.log("I am useless. :^)")}>
                                <FontAwesomeIcon icon={faSmile}/>
                            </Button>
                        </div>
                        <div className="col-md-10">
                            <div className="form-control" onClick={this.openAnswers}>
                                {
                                    this.state.chat.getLastMessage()!.isAnswer ? '' : (
                                        !this.state.chosenAnswer ?
                                            <i>Bitte Antwort wählen</i>
                                            :
                                            this.state.chosenAnswer!.text
                                    )
                                }
                            </div>
                        </div>
                        <div className="col-md-1">
                            <Button color="primary" onClick={this.onSendMessage}>
                                <FontAwesomeIcon icon={faPaperPlane}/>
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
