import { DefaultComponentProps } from "../../DefaultComponentProps";
import React from "react";
import { FormGroup, Form, Label, Input, Button, CardBody, Card } from "reactstrap";
import "../../styles/elements/login.scss";
import { LoginCardComponent } from "./LoginCard";

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

        this.onUserInput = this.onUserInput.bind(this);
    }

    componentDidMount() {
        document.getElementsByTagName("body")[0].classList.add("login-register-background");
    }

    componentWillUnmount() {
        document.getElementsByTagName("body")[0].classList.remove("login-register-background");
    }

    onUserInput(event: React.ChangeEvent<HTMLInputElement>) {
        let target = event.target;
        let field = target.name as "username" | "password";
        let nState: LoginStates = Object.assign({}, this.state); // Kopie erstellen
        nState[field] = target.value;
        this.setState(nState);
    }

    render() {
        return <LoginCardComponent mode="login">
            <FormGroup>
                <Label>Username</Label>
                <Input type="text" placeholder="william.walker1" value={this.state.username} onChange={this.onUserInput} name="username" />
            </FormGroup>
            <FormGroup>
                <Label>Passwort</Label>
                <Input type="password" placeholder="passwort" value={this.state.password} onChange={this.onUserInput} name="password" />
            </FormGroup>
            <Button>
                Login and Work!
            </Button>
        </LoginCardComponent>
    }
}