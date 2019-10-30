import React from "react";
import { Chat } from "../models/Chat";

export interface ChatListProps {
    chats: Chat[];
}

export function ChatListComponent(props: ChatListProps) {
    return <div id="sidepanel">
        <div id="profile">
            <div className="wrap">
                <img id="profile-img" src="http://emilcarlsson.se/assets/mikeross.png" className="online" alt="" />
                <p>William Walker</p>
            </div>
        </div>
        <div id="contacts">
            <ul>
                {
                    props.chats.map(c => {
                        let message = c.getLastMessage();
                        let text = message === null ? "Keine Nachricht" : message!.text;

                        return <li className="contact">
                            <div className="wrap">
                                <span className="contact-status online"></span>
                                <img src={c.partner.imageUrl} alt="" />
                                <div className="meta">
                                    <p className="name">{c.partner.name}</p>
                                    <p className="preview">
                                        {text}
                                    </p>
                                </div>
                            </div>
                        </li>
                    })
                }
            </ul>
        </div>
    </div>
}