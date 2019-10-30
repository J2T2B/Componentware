import React from "react";
import { Chat } from "../models/Chat";
import { Card, CardBody, ListGroup, ListGroupItem, ListGroupItemHeading, ListGroupItemText, CardTitle } from "reactstrap";

export interface ChatListProps {
    chats: Chat[];
}

export function ChatListComponent(props: ChatListProps) {
    return <Card>
        <CardBody>
            <CardTitle>William Walker</CardTitle>
        </CardBody>
        <ListGroup>
            {
                props.chats.map(c => {
                    let message = c.getLastMessage();
                    let text = message === null ? "Keine Nachricht" : message!.text;

                    return <ListGroupItem>
                        <ListGroupItemHeading>{c.partner.name}</ListGroupItemHeading>
                        <ListGroupItemText>
                            {text}
                        </ListGroupItemText>
                    </ListGroupItem>;
                })
            }
        </ListGroup>
    </Card>
}