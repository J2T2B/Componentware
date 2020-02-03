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
                GameOver <span role="img">☠</span>
            </h1>
            Du wurdest entlassen. <br/>
            Dein Konto wurde gelöscht. Du musst ein neues erstellen um wieder zu spielen
            <p className="lead">
                <Button color="primary" onClick={()=>window.location.reload()}>
                    Neues Leben beginnen
                </Button>
            </p>
        </Jumbotron>
    </Container>;
}