import { Container, Jumbotron } from "reactstrap";

import React from "react";

export function MockComponent() {
    return <Container>
        <Jumbotron>
            <h1 className="display-3">
                Hallo Welt :)
        </h1>
        </Jumbotron>
    </Container>;
}