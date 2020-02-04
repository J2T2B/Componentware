import {Card, CardBody, Form, Button, ButtonGroup, CardHeader, CardTitle, Jumbotron, Container} from "reactstrap";
import "../../styles/elements/login.scss";
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
        document.getElementsByTagName("body")[0].classList.add("login-register-background");
        window.addEventListener("resize", this.onSizeChange);
    }

    componentWillUnmount() {
        document.getElementsByTagName("body")[0].classList.remove("login-register-background");
        window.removeEventListener("resize", this.onSizeChange);
    }

    onSizeChange() {
        this.setState({
            width: window.innerWidth
        })
    }

    render() {
        return <Container>
            <Jumbotron className="transparent text-center">
                <h2 className="display-4 headline-font">
                    Life-Work-Balance von William Walker
                </h2>
            </Jumbotron>
            <Card className={(this.state.width > 992 ? "mx-auto w-50" : "mx-auto") + " transparent"}>
                <CardHeader>
                    <CardTitle>
                        <h4 className="">
                            {this.props.mode === "login" && "Anmelden"}
                            {this.props.mode === "register" && "Registrieren"}
                        </h4>
                    </CardTitle>
                </CardHeader>
                <CardBody>
                    <ButtonGroup>
                        <Button color="primary" active={this.props.mode === "login"} href="#/login" tag="a">Anmelden</Button>
                        <Button color="primary" active={this.props.mode === "register"} href="#/register" tag="a">Registrieren</Button>
                    </ButtonGroup>
                    <Form>
                        {this.props.children}
                    </Form>
                </CardBody>
            </Card>
        </Container>
    }
}