import React from 'react';
import './App.css';
import './chat.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { MockComponent } from './components/MockComponent';
import { Container } from 'reactstrap';
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
            <div id="frame">
                <ChatListComponent chats={chats} />
            </div>
            <HashRouter>
                <Switch>
                    <Route path="/" exact>
                        <div className="messages">
                            <MockComponent />
                        </div>
                    </Route>
                </Switch>
            </HashRouter>
        </>
    }

}

// Idee: https://bootsnipp.com/snippets/exR5v