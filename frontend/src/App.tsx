import React from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import { MockComponent } from './components/MockComponent';
import { Container } from 'reactstrap';
import { Router, Switch, Route } from 'react-router';
import { HashRouter } from 'react-router-dom';

export class App extends React.Component<{}, {}> {
    
    render() {
       return <Container>
       <HashRouter>
           <Switch>
               <Route path="/" exact>
                   <MockComponent />
               </Route>
           </Switch>
       </HashRouter>
   </Container>
    }

}

// Idee: https://bootsnipp.com/snippets/exR5v