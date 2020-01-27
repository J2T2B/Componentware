import {DefaultComponentProps} from "../../DefaultComponentProps";
import {Alert, Button, Modal, ModalBody, ModalFooter, ModalHeader} from "reactstrap";
import * as React from "react";
import {IErrorHandler} from "../../logic/IErrorHandler";

interface ErrorStates {
    errors: string[];
}

export class ErrorComponent extends React.Component<DefaultComponentProps, ErrorStates> implements IErrorHandler{
    constructor(props: DefaultComponentProps) {
        super(props);

        this.state = {
            errors: []
        }
    }

    componentDidMount() {
        this.props.chatsHandler.attach(this);
    }

    componentWillUnmount() {
        this.props.chatsHandler.detatch(this);
    }

    onError(message: string) {
        this.setState(s => {
            return {
                errors: [...s.errors, message]
            };
        });
    }

    private onClose() {
        this.setState({
            errors: []
        });
    }

    render() {
        return <Modal isOpen={this.state.errors.length > 0}>
            <ModalHeader>Es sind Fehler aufgetreten</ModalHeader>
            <ModalBody>
                Folgende Fehler sind aufgetreten: <br/>
                {this.state.errors.map(e=><Alert color="danger" key={e}>{e}</Alert>)}
            </ModalBody>
            <ModalFooter>
                <Button color="primary" onClick={this.onClose.bind(this)}>Ok</Button>
            </ModalFooter>
        </Modal>
    }
}