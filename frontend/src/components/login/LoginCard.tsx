import { Card, CardBody, Form, FormGroup, Label, Input, Button, ButtonGroup } from "reactstrap";

import React from "react";

export interface LoginCardProps {
    mode: "login" | "register"
}

interface LoginCardStates {
    width: number;
}

export class LoginCardComponent extends React.Component<React.PropsWithChildren<LoginCardProps>, LoginCardStates> {

    constructor(props: React.PropsWithChildren<LoginCardProps>) {
        super(props)

        this.state = {
            width: window.innerWidth
        }

        this.onSizeChange = this.onSizeChange.bind(this)
    }

    componentDidMount() {
        this.onSizeChange();
        window.addEventListener("resize", this.onSizeChange);
    }

    componentWillUnmount() {
        window.removeEventListener("resize", this.onSizeChange);
    }

    onSizeChange() {
        this.setState({
            width: window.innerWidth
        })
    }

    render() {
        return <Card className={this.state.width > 992 ? "mx-auto w-50" : "mx-auto"} style={{ backdropFilter: "blur(10px)", backgroundColor: "#FFFFFF88" }}>
            <CardBody>
                <ButtonGroup>
                    <Button color="primary" active={this.props.mode === "login"}>Anmelden</Button>
                    <Button color="primary" active={this.props.mode === "register"}>Registrieren</Button>
                </ButtonGroup>
                <Form>
                    {this.props.children}
                </Form>
            </CardBody>
        </Card>
    }
}