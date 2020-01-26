import {Jumbotron} from "reactstrap";
import {faSync} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import React from "react";

const ReconnectingComponent : React.FC = () => {
    return <Jumbotron>
        <h1>
            <FontAwesomeIcon icon={faSync} spin />
            Die Verbindung wurde unterbrochen.
        </h1>
        <p className="lead">
            Bitte warten Sie bis die Verbindung wiederhergestellt wurde.....
        </p>
    </Jumbotron>
};

export default ReconnectingComponent;