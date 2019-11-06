import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { MockComponent } from './components/MockComponent';
import { Container, Col, Row, Navbar, NavbarBrand } from 'reactstrap';
import { Router, Switch, Route } from 'react-router';
import { HashRouter } from 'react-router-dom';
import { ChatsListComponent } from './components/ChatListComponent';
import AChatsHandler from './logic/AChatsHandler';
import { MockChatsHandler } from './logic/MockChatsHandler';
import {ChatMessageComponent} from "./components/ChatMessageComponent";

export class App extends React.Component<{}, {}> {

    private chatsHandler: AChatsHandler;

    constructor(props: {}) {
        super(props);
        this.chatsHandler = new MockChatsHandler();
        this.chatsHandler.connect();
    }

    render() {
        return <>
            <Navbar color="faded" dark style={{
                backgroundColor: "#c3c3c3"
            }} className="mb-3">
                <NavbarBrand href="/" className="mr-auto">
                    Telekilo
                </NavbarBrand>
            </Navbar>

            <Container fluid>
                <Row>
                    <Col md={4}>
                        <ChatsListComponent chatsHandler={this.chatsHandler} />
                    </Col>
                    <Col md={8}>
                        <HashRouter>
                            <Switch>
                                <Route path="/" exact>
                                    <ChatMessageComponent chatsHandler={this.chatsHandler} />
                                </Route>
                            </Switch>
                        </HashRouter>
                    </Col>
                </Row>
            </Container>
        </>
    }

}

// Idee: https://bootsnipp.com/snippets/exR5v
