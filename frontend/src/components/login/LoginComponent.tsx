import { DefaultComponentProps } from "../../DefaultComponentProps";
import React from "react";
import { FormGroup, Form, Label, Input, Button, CardBody, Card } from "reactstrap";
import "../../styles/elements/login.scss";
import { LoginCardComponent } from "./LoginCard";
import { SmartInputComponent } from "./SmartInputComponent";

export interface LoginStates {
    username: string;
    password: string;
}

export class LoginComponent extends React.Component<DefaultComponentProps, LoginStates> {

    constructor(props: DefaultComponentProps) {
        super(props);
        this.state = {
            username: "",
            password: ""
        };

        this.onInput = this.onInput.bind(this);
    }

    onInput(nValue: Partial<LoginStates>) {
        this.setState(nValue as LoginStates);
    }

    render() {
        return <LoginCardComponent mode="login">

            <SmartInputComponent
                label="Username"
                onValue={this.onInput}
                name="username"
                placeholder="william.walker1"
                value={this.state.username}
                type="text"
            />

            <SmartInputComponent
                label="Passwort"
                onValue={this.onInput}
                name="password"
                placeholder="132456789"
                value={this.state.password}
                type="password"
            />

            <Button>
                Login and go to Work!
            </Button>
        </LoginCardComponent>
    }
}