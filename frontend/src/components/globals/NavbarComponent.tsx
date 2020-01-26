import {Button, Nav, Navbar, NavbarBrand, NavItem} from "reactstrap";
import React from "react";

export interface NavbarProps {
    toggleChatsList: () => void;
}

export function NavbarComponent({toggleChatsList}: NavbarProps): JSX.Element {
    return <Navbar color="faded" dark className="mb-3">
        <NavbarBrand href="#" className="mr-auto">
            Telekilo
        </NavbarBrand>
        <Nav>
            <NavItem className="d-md-none">
                <Button color="primary" onClick={toggleChatsList}>
                    Chatliste
                </Button>
            </NavItem>
        </Nav>
    </Navbar>;
}