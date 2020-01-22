import { Navbar, NavbarBrand, Nav, NavItem, Button, NavLink } from "reactstrap";
import React from "react";

export interface NavbarProps {
    toggleChatsList: () => void;
}

export function NavbarComponent({ toggleChatsList }: NavbarProps): JSX.Element {
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
            {
                process.env.NODE_ENV === "development" && <>
                    <NavItem>
                        <NavLink href="#/login">Login</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href="#/register">Registrieren</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink href="#/gameover">GameOver</NavLink>
                    </NavItem>
                </>
            }
        </Nav>
    </Navbar>;
}