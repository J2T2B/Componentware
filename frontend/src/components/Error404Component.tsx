import {Jumbotron} from "reactstrap";
import React from "react";
import {faBookDead} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

const Error404Component: React.FC = () => {
    return <Jumbotron>
        <h1>
            <FontAwesomeIcon icon={faBookDead} />
            Error 404
        </h1>
        <p className="lead">
            Die Seite gibt es nicht
        </p>
        <p className="lead">
            <a href="#/" color="primary">
                Zur Startseite
            </a>
        </p>
    </Jumbotron>;
};

export default Error404Component;