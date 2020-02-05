import {Button, ListGroup, ListGroupItem, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import React from "react";
import {Answer} from "../../models/Answer";
import {Message} from "../../models/Message";

export interface SelectAnswerComponentProps {
    isOpen: boolean;
    toggle: () => void;
    onAnswerChoose: (answer: Answer) => void;
    choosenAnswer: Answer | undefined;
    lastMessage: Message;
}

const SelectAnswerComponent: React.FC<SelectAnswerComponentProps> = (props: SelectAnswerComponentProps) => {
    return <div className="row">
        <Modal isOpen={props.isOpen} toggle={props.toggle}>
            <ModalHeader toggle={props.toggle}>Ihre Antwortmöglichkeiten</ModalHeader>
            <ModalBody>
                <ListGroup>
                    {
                        props.lastMessage.answers.map(a => {
                            return <ListGroupItem tag="button"
                                                  className={props.choosenAnswer === a ? 'active' : ''}
                                                  onClick={() => props.onAnswerChoose(a)}
                                                  id={"answer-" + a.id}
                                                  key={a.id}
                            >
                                {a.text}
                            </ListGroupItem>
                        })
                    }
                </ListGroup>
            </ModalBody>
            <ModalFooter>
                <Button color="secondary" onClick={props.toggle}>
                    Abbrechen
                </Button>
            </ModalFooter>
        </Modal>
    </div>;
};

export {SelectAnswerComponent};