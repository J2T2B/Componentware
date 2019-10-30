import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { MockComponent } from './components/MockComponent';
import { Container, Col, Row, Navbar, NavbarBrand } from 'reactstrap';
import { Router, Switch, Route } from 'react-router';
import { HashRouter } from 'react-router-dom';
import { ChatListComponent } from './components/ChatListComponent';
import { Chat } from './models/Chat';

const chats = [
    new Chat({
        imageUrl: "https://via.placeholder.com/35",
        name: "Max Mustermann"
    }),
    new Chat({
        imageUrl: "https://via.placeholder.com/35",
        name: "William Walker"
    })
];

export class App extends React.Component<{}, {}> {

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
                        <ChatListComponent chats={chats} />
                    </Col>
                    <Col md={8}>
                        <HashRouter>
                            <Switch>
                                <Route path="/" exact>
                                    <div className="messages">
                                        <MockComponent />
                                    </div>
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