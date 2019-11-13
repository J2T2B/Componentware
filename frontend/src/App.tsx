import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { MockComponent } from './components/MockComponent';
import { Container, Col, Row, Navbar, NavbarBrand, Nav, NavItem, NavLink, Button } from 'reactstrap';
import { Router, Switch, Route } from 'react-router';
import { HashRouter } from 'react-router-dom';
import { ChatsListComponent } from './components/ChatListComponent';
import AChatsHandler from './logic/AChatsHandler';
import { MockChatsHandler } from './logic/MockChatsHandler';
import { ChatMessageComponent } from "./components/ChatMessageComponent";

interface AppStates {
    isChatListOpen: boolean;
}

export class App extends React.Component<{}, AppStates> {

    private chatsHandler: AChatsHandler;

    constructor(props: {}) {
        super(props);
        this.chatsHandler = new MockChatsHandler();
        this.chatsHandler.connect();

        this.state = {
            isChatListOpen: true
        };
    }

    toggleChatsList() {
        this.setState(o => {
            return {
                isChatListOpen: !o.isChatListOpen
            }
        })
    }

    render() {
        return <>
            <Navbar color="faded" dark style={{
                backgroundColor: "#c3c3c3"
            }} className="mb-3">
                <NavbarBrand href="/" className="mr-auto">
                    Telekilo
                </NavbarBrand>
                <Nav>
                    <NavItem className="d-md-none">
                        <Button color="primary" onClick={this.toggleChatsList.bind(this)}>
                            Chatliste
                        </Button>
                    </NavItem>
                </Nav>
            </Navbar>

            <Container fluid>
                <Row>
                    <Col md={4}>
                        <ChatsListComponent chatsHandler={this.chatsHandler} isOpen={this.state.isChatListOpen} />
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
