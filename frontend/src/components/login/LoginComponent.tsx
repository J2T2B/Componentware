import { DefaultComponentProps } from "../../DefaultComponentProps";
import React from "react";
import { Button } from "reactstrap";
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
        this.onSubmit = this.onSubmit.bind(this);
    }

    onInput(nValue: Partial<LoginStates>) {
        this.setState(nValue as LoginStates);
    }

    async onSubmit() : Promise<void> {
        
        let login = {
            username: this.state.username,
            password: this.state.password
        };

        console.log(
            "Login mit", login
        );
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
                autoComplete="username"
            />

            <SmartInputComponent
                label="Passwort"
                onValue={this.onInput}
                name="password"
                placeholder="132456789"
                value={this.state.password}
                type="password"
                autoComplete="current-password"
            />

            <Button onClick={this.onSubmit}>
                Anmelden und arbeiten
            </Button>
        </LoginCardComponent>
    }
}