import React from 'react';
import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Jumbotron } from 'reactstrap';

export class App extends React.Component<{}, {}> {
    
    render() {
        return <Container>
            <Jumbotron>
                <h1 className="display-3">
                    Hallo Welt :)
                </h1>
            </Jumbotron>
        </Container>;
    }

}

// Idee: https://bootsnipp.com/snippets/exR5v