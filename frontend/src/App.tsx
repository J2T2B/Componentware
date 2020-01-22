import React from 'react';
import { Container, Col, Row } from 'reactstrap';
import { Switch, Route } from 'react-router';
import { HashRouter } from 'react-router-dom';
import { ChatsListComponent } from './components/sidebar/ChatListComponent';
import AChatsHandler from './logic/AChatsHandler';
import { MockChatsHandler } from './logic/MockChatsHandler';
import { ChatMessageComponent } from "./components/chat/ChatMessageComponent";
import { LoginComponent } from './components/login/LoginComponent';
import { RegisterComponent } from './components/login/RegisterComponent';
import { GameOverPage } from './components/gameover/GameOverPage';
import { NavbarComponent } from './components/globals/NavbarComponent';

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
            <NavbarComponent toggleChatsList={this.toggleChatsList.bind(this)} />

            <Container fluid>
                <HashRouter>
                    <Switch>

                        <Route path="/" exact>
                            <Row>
                                <Col md={4}>
                                    <ChatsListComponent chatsHandler={this.chatsHandler} isOpen={this.state.isChatListOpen} />
                                </Col>
                                <Col md={8}>
                                    <ChatMessageComponent chatsHandler={this.chatsHandler} />
                                </Col>
                            </Row>
                        </Route>

                        <Route path="/login" exact render={() => <LoginComponent chatsHandler={this.chatsHandler} />} />

                        <Route path="/register" exact render={() => <RegisterComponent chatsHandler={this.chatsHandler} />} />

                        <Route path="/gameover" exact render={() => <GameOverPage />} />

                    </Switch>
                </HashRouter>
            </Container>
        </>
    }

}

// Idee: https://bootsnipp.com/snippets/exR5v
