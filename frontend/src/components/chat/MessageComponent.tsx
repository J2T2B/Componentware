import moment from "moment";
import React from "react";
import {Message} from "../../models/Message";

export interface MessageProps {
    m: Message;
    chatId: string;
}

const ln2br = (text: string) => {
    return {__html: text.replace("\n", "<br />")};
};

const MessageComponent: React.FC<MessageProps> = ({m, chatId}: MessageProps) =>
{
    return <div className="row" key={chatId + "/" + m.id}>
        <div className="message">
            <div className={"bubble" + (m.isAnswer ? " reply" : " receive")}>
                <div className="content">
                    <p dangerouslySetInnerHTML={ln2br(m.text)}/>
                    <p className="time">
                        {m.created.isBefore(moment().startOf('day')) && m.created.format('L')}
                        &nbsp;
                        {m.created.format('HH:mm')}
                    </p>
                </div>
            </div>
        </div>
    </div>;
};

export {MessageComponent};