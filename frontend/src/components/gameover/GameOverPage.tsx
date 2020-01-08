import React, { useEffect } from "react";
import { Container, Jumbotron, Button } from "reactstrap";

export function GameOverPage(): JSX.Element {
    useEffect(()=>{
        document.getElementsByTagName("body")[0].classList.add("gameover-background");

        return ()=> document.getElementsByTagName("body")[0].classList.remove("gameover-background");
    });

    return <Container>
        <Jumbotron className="transparent">
            <h1 className="display-3">
                GameOver ☠️
            </h1>
            Du wurdest entlassen. <br/>
            <p className="lead">
                <Button color="primary">
                    // TODO: Gib mir eine Funktion
                    Neues Leben beginnen
                </Button>
            </p>
        </Jumbotron>
    </Container>;
}