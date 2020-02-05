import React from 'react';
import AChatsHandler from "./logic/AChatsHandler";
import {AppStateMode} from "./models/AppStateMode";
import {Connector} from "./logic/Connector";
import IConnectionListener from "./logic/IConnectionListener";
import {LoginComponent} from "./components/login/LoginComponent";
import {RegisterComponent} from "./components/login/RegisterComponent";
import {Redirect, Route, Switch} from "react-router";
import {ChatsListComponent} from "./components/sidebar/ChatListComponent";
import {Col, Container, Row} from "reactstrap";
import {ChatMessageComponent} from "./components/chat/ChatMessageComponent";
import {NavbarComponent} from "./components/globals/NavbarComponent";
import {HashRouter} from "react-router-dom";
import Error404Component from "./components/Error404Component";
import ReconnectingComponent from "./components/ReconnectingComponent";
import {GameOverPage} from "./components/gameover/GameOverPage";
import {ErrorComponent} from "./components/globals/ErrorComponent";
import {IGameOverListener} from "./logic/IGameOverListener";

interface AppStates {
    isChatListOpen: boolean;
    chatsHandler?: AChatsHandler;
    mode: AppStateMode;
    connector: Connector;
}

export class App extends React.Component<{}, AppStates> implements IConnectionListener, IGameOverListener {

    constructor(props: {}) {
        super(props);

        this.state = {
            isChatListOpen: true,
            connector: new Connector("http://localhost:8080/server-1.0-SNAPSHOT", this, false),
            mode: AppStateMode.LOGIN
        };

        this.renderLoginBody = this.renderLoginBody.bind(this);
        this.renderNormalBody = this.renderNormalBody.bind(this);
    }

    onConnect(chatsHandler: AChatsHandler): void {
        this.setState({
            chatsHandler,
            mode: AppStateMode.GAME
        });
        chatsHandler.attach(this);
    }

    onDisconnect(): void {
        this.setState({
            chatsHandler: undefined,
            mode: AppStateMode.CONNECTING
        });
    }

    toggleChatsList() {
        this.setState(o => {
            return {
                isChatListOpen: !o.isChatListOpen
            }
        })
    }

    private renderLoginBody() {
        return <>
            <Route path="/login" exact render={() => <LoginComponent connector={this.state.connector}/>}/>
            <Route path="/register" exact render={() => <RegisterComponent connector={this.state.connector}/>}/>
            <Redirect from="*" to="/login"/>
        </>;
    }

    private renderNormalBody() {
        return <>
            <Route path="/" exact>
                <Row>
                    <Col md={4}>
                        <ChatsListComponent chatsHandler={this.state.chatsHandler!} isOpen={this.state.isChatListOpen}/>
                    </Col>
                    <Col md={8}>
                        <ChatMessageComponent chatsHandler={this.state.chatsHandler!}/>
                    </Col>
                </Row>
            </Route>
            <ErrorComponent chatsHandler={this.state.chatsHandler!}/>
        </>;
    }

    render() {
        return <>
            <NavbarComponent toggleChatsList={this.toggleChatsList.bind(this)}/>

            <Container fluid>
                <HashRouter>
                    <Switch>
                        {this.state.mode === AppStateMode.LOGIN && <this.renderLoginBody/>}
                        {this.state.mode === AppStateMode.GAME && <this.renderNormalBody/>}
                        {this.state.mode === AppStateMode.GAMEOVER && <GameOverPage/>}
                        {this.state.mode === AppStateMode.CONNECTING && <ReconnectingComponent/>}
                        <Route path="/404" exact component={() => <Error404Component/>}/>
                        <Redirect from="*" to="/404"/>
                    </Switch>
                </HashRouter>
            </Container>
        </>
    }

    onGameOver(): void {
        this.setState({
            mode: AppStateMode.GAMEOVER
        });
    }

}