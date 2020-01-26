import React from "react";
import {Alert, Button} from "reactstrap";
import "../../styles/elements/login.scss";
import {LoginCardComponent} from "./LoginCard";
import {SmartInputComponent} from "./SmartInputComponent";
import setWindowTitle from "../../logic/setWindowTitle";
import {LoginRegisterProps} from "../../models/LoginRegisterProps";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faSync} from "@fortawesome/free-solid-svg-icons";

export interface LoginStates {
    error?: string;
    busy: boolean;
    username: string;
    password: string;
}

export class LoginComponent extends React.Component<LoginRegisterProps, LoginStates> {

    constructor(props: LoginRegisterProps) {
        super(props);
        this.state = {
            busy: false,
            username: "",
            password: ""
        };

        this.onInput = this.onInput.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onInput(nValue: Partial<LoginStates>) {
        this.setState(nValue as LoginStates);
    }

    componentDidMount() {
        setWindowTitle("Login");
    }

    async onSubmit(): Promise<void> {
        this.setState({busy: true, error: undefined});

        const {username, password} = this.state;

        try {
            await this.props.connector.login(username, password);
            window.location.href = "#/";
        } catch (e) {
            this.setState({
                busy: false,
                error: e.message
            });
        }
    }

    render() {
        return <LoginCardComponent mode="login">

            {this.state.error && <Alert color="danger">
                {this.state.error}
            </Alert>}

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

            <Button
                onClick={this.onSubmit}
                color="success"
                disabled={this.state.username.length === 0 || this.state.password.length === 0}
            >
                {this.state.busy && <FontAwesomeIcon icon={faSync} spin/>}
                Anmelden
            </Button>
        </LoginCardComponent>
    }
}