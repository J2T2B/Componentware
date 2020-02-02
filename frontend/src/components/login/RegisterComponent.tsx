import React from "react";
import { LoginCardComponent } from "./LoginCard";
import { Alert, Button, FormGroup, Input, Label } from "reactstrap";
import { SmartInputComponent } from "./SmartInputComponent";
import setWindowTitle from "../../logic/setWindowTitle";
import { LoginRegisterProps } from "../../models/LoginRegisterProps";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSync } from "@fortawesome/free-solid-svg-icons";
import OnEnterPress from "../../logic/OnEnterPress";

const ERRORS = {
    usernameToShort: "Nutzername darf nicht leer sein",
    passwordToShort: "Passwort darf nicht leer sein",
    passwordMatch: "Passwörter stimmen nicht überein"
};

interface RegisterStates {
    error?: string
    busy: boolean
    name: string
    username: string
    usernameError: string
    password: string
    passwordError?: string
    repeatPassword: string
    repeatPasswordError?: string
}

export class RegisterComponent extends React.Component<LoginRegisterProps, RegisterStates> {

    constructor(props: LoginRegisterProps) {
        super(props);
        this.state = {
            busy: false,
            name: "William Walker",
            username: "",
            usernameError: ERRORS.usernameToShort,
            password: "",
            passwordError: ERRORS.passwordToShort,
            repeatPassword: ""
        };

        this.onInput = this.onInput.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        setWindowTitle("Registrieren");
    }

    onInput(nValue: Partial<RegisterStates>) {

        nValue.repeatPasswordError = undefined;
        nValue.passwordError = undefined;
        nValue.usernameError = undefined;

        // Warnung wenn Passwörter nicht matchen
        if (
            (nValue.password !== undefined && nValue.password !== this.state.repeatPassword)
            || (nValue.repeatPassword !== undefined && nValue.repeatPassword !== this.state.password)
        ) {
            nValue.repeatPasswordError = ERRORS.passwordMatch;
        }

        // Warnung wenn Passwort leer ist
        if (nValue.password !== undefined && nValue.password.length < 1) {
            nValue.passwordError = ERRORS.passwordToShort;
        }

        // Wenn Nutzername zu kurz
        if (nValue.username !== undefined && nValue.username.length < 1) {
            nValue.usernameError = ERRORS.usernameToShort;
        }

        this.setState(nValue as RegisterStates);
    }

    async onSubmit() {
        this.setState({ busy: true, error: undefined });

        const { username, password } = this.state;

        try {
            await this.props.connector.register(username, password);
            window.location.href = "#/";
        } catch (e) {
            this.setState({
                busy: false,
                error: e.message
            });
        }
    }

    render() {
        return <LoginCardComponent mode="register">

            {this.state.error && <Alert color="danger">
                {this.state.error}
            </Alert>}

            <FormGroup>
                <Label>Name</Label>
                <Input type="text" value={this.state.name} autoComplete="off" readOnly />
            </FormGroup>

            <SmartInputComponent
                label="Username"
                name="username"
                placeholder="william.walker"
                value={this.state.username}
                type="text"
                onValue={this.onInput}
                error={this.state.usernameError}
                autoComplete="username"
            />

            <SmartInputComponent
                onValue={this.onInput}
                label="Passwort"
                name="password"
                placeholder="12345678"
                value={this.state.password}
                type="password"
                error={this.state.passwordError}
                autoComplete="new-password"
            />

            <SmartInputComponent
                onValue={this.onInput}
                label="Passwort wiederholen"
                name="repeatPassword"
                placeholder="12345678"
                value={this.state.repeatPassword}
                type="password"
                error={this.state.repeatPasswordError}
                autoComplete="new-password"
                onKeyPress={OnEnterPress(this.onSubmit)}
            />

            <Button
                color="success"
                onClick={this.onSubmit}
                disabled={!checkUndefined(this.state.repeatPasswordError, this.state.passwordError, this.state.usernameError)}
            >
                {this.state.busy && <FontAwesomeIcon icon={faSync} spin />}
                Registrieren
            </Button>

        </LoginCardComponent>
    }

}

function checkUndefined(...items: any[]): boolean {
    for (let item of items) {
        if (item !== undefined) {
            return false;
        }
    }

    return true;
}