package de.fhdortmund.j2t2.wise2019.server.game.remote.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;

import java.util.List;
import java.util.stream.Collectors;

public class ChatRemoteModel {

    private List<MessageRemoteModel> messages;
    private ChatPartnerRemoteModel partner;
    private long chatId;

    public ChatRemoteModel(Chat chat){
        this.messages = chat.getMessages().stream().map(MessageRemoteModel::new).collect(Collectors.toList());
        this.partner = new ChatPartnerRemoteModel(chat.getChatpartner());
        this.chatId = chat.getId();
    }

    public List<MessageRemoteModel> getMessages() {
        return messages;
    }

    public ChatPartnerRemoteModel getPartner() {
        return partner;
    }

    public long getChatId() {
        return chatId;
    }
}
