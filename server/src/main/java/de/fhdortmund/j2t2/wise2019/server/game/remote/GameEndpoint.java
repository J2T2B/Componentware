package de.fhdortmund.j2t2.wise2019.server.game.remote;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResult;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.ErrorWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.WebSocketCreatedCommand;
import de.fhdortmund.j2t2.wise2019.server.game.models.ChatImpl;
import de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands.*;
import de.fhdortmund.j2t2.wise2019.server.user.UserManager;
import de.fhdortmund.j2t2.wise2019.server.user.sessionmanager.SessionManager;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@ServerEndpoint(value = "/game/{usertoken}", encoders = MessageCoder.class, decoders = MessageCoder.class)
public class GameEndpoint {

    private Session session;

    @Inject
    private SessionManager sessionManager;
    @Inject
    private UserManager userManager;
    @Inject
    private RemoteChatManager chatManager;

    private List<Game> games;
    private String token;

    @OnOpen
    public void onOpen(Session session, @PathParam("usertoken") String token) throws IOException, EncodeException {
        this.session = session;
        this.games = sessionManager.getGamesForToken(token);
        send(new WebSocketCreatedCommand());
    }

    @OnMessage
    public void onMessage(AbstractWebSocketCommand command, Session session) throws IOException, EncodeException {
            switch (command.getCommand()) {
                case "Reinit":
                    handleReinitcommand();
                    break;
                case "SubmitAnswer": {
                    SubmitAnswerWebSocketCommand submitCommand = (SubmitAnswerWebSocketCommand) command;
                    handleSubmitCommand(submitCommand.getAnswerId(),submitCommand.getMessageId(), submitCommand.getChatId() );
                    break;
                }
                case "ReadMessage": {
                    ReadMessageWebSocketCommand readMessageCommand = (ReadMessageWebSocketCommand) command;
                    handleReadMessageCommand(readMessageCommand.getMessageId(), readMessageCommand.getChatId());
                    break;
                }
                default:
                    throw new UnsupportedOperationException(command.getCommand());
            }
    }



    @OnError
    public void onError(Throwable throwable){
        try {
            send(new ErrorWebSocketCommand(new Exception(throwable)));
        } catch (IOException | EncodeException e) {
            e.printStackTrace();  //TODO error handling
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        session.close();
        sessionManager.invalidate(token);
    }


    private void handleReinitcommand() throws IOException, EncodeException {
        for(Game game : games){
            GameState<?> gameState = game.getGameState();
            for(Chat chat : gameState.getOpenChats()){
                ChatImpl chatImpl = new ChatImpl(chat);
                sendCreateChatCommand(chatImpl, game);
            }
        }
    }

    private void handleSubmitCommand(long answerId, String messageId, long chatId) throws IOException, EncodeException {
        Game game = chatManager.getGameForRemoteChatId(chatId);
        Chat chat = game.getGameState().getChat(chatId);
        Chat.ChatMessage chatMessage = chat.getMessage(messageId);
        Message msg = (Message) chatMessage.getMsg();

        for(Answer answer : msg.getAnswers()) {
            if (answer.getId() == answerId) {
                PlayResult res = game.playAnswer(chat, answer);
                List<Chat.ChatMessage> messages = chat.getMessages();
                sendAddMessageCommand(chatId, messages.get(messages.size()-1));
                Object gameData = game.getGameState().getData();
                if(gameData instanceof Points){
                    sendChangePointsCommand((Points) gameData);
                }
            }
        }
    }

    private void handleReadMessageCommand(String messageId, long chatId) {
        Game game = chatManager.getGameForRemoteChatId(chatId);
        Chat chat = game.getGameState().getChat(chatId);
        Chat.ChatMessage message = chat.getMessage(messageId);
        message.setRead(true);
    }


    private void send(AbstractWebSocketCommand replyObject) throws IOException, EncodeException {
        session.getBasicRemote().sendObject(replyObject);
    }

    public void sendCreateChatCommand(Chat chat, Game game) throws IOException, EncodeException {
        chatManager.registerChat(chat, game);
        send(new CreateChatWebSocketCommand(chat));
    }

    public void sendAddMessageCommand(long chatId, Chat.ChatMessage chatMessage) throws IOException, EncodeException {
        send(new AddMessageWebSocketCommand(chatId, chatMessage));
    }

    public void sendChangePointsCommand(Points points) throws IOException, EncodeException {
        send(new ChangePointsWebSocketCommand(points));
    }
}
