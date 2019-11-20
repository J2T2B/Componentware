import { DefaultComponentProps } from "../../DefaultComponentProps";
import React from "react";
import { LoginCardComponent } from "./LoginCard";
import { FormGroup, Input, Label } from "reactstrap";
import { SmartInputComponent } from "./SmartInputComponent";


interface RegisterStates {
    name: string
    username: string
    password: string
    repeatPassword: string

}

export class RegisterComponent extends React.Component<DefaultComponentProps, RegisterStates> {

    constructor(props: DefaultComponentProps) {
        super(props);
        this.state = {
            name: "William Walker",
            username: "",
            password: "",
            repeatPassword: ""
        };

        this.onInput = this.onInput.bind(this);
    }

    onInput(nValue: Partial<RegisterStates>) {
        this.setState(nValue as RegisterStates);
    }

    render() {
        return <LoginCardComponent mode="register">

            <FormGroup>
                <Label>Name</Label>
                <Input type="text" value={this.state.name} />
            </FormGroup>

            <SmartInputComponent
                label="Username"
                name="username"
                placeholder="william.walker"
                value={this.state.username}
                type="text"
                onValue={this.onInput}
            />

            <SmartInputComponent
                onValue={this.onInput}
                label="Passwort"
                name="password"
                placeholder="12345678"
                value={this.state.password}
                type="password"
            />

            <SmartInputComponent
                onValue={this.onInput}
                label="Passwort wiederholen"
                name="repeatPassword"
                placeholder="12345678"
                value={this.state.repeatPassword}
                type="password"
            />

        </LoginCardComponent>
    }

}