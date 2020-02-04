package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

import java.util.List;
import java.util.Map;

public class ChatCreationWithExtraAnswers implements CreateChatResult {

    private Chat chat;
    private Map<Long, List<Answer>> extaAnswers;

    public ChatCreationWithExtraAnswers(Chat chat, Map<Long, List<Answer>> extaAnswers) {
        this.chat = chat;
        this.extaAnswers = extaAnswers;
    }

    @Override
    public Chat getChat() {
        return this.chat;
    }

    public Map<Long, List<Answer>> getExtaAnswers() {
        return extaAnswers;
    }
}
