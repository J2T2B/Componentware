package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoader;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameModel;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DetectivGame extends AbstractGame<Void> {

    public DetectivGame() throws GameLoadingException {
        super(DetectivGame.class.getClassLoader());
    }

    @Override
    protected void updateGameState(PlayResult res) {
        //TODO gibt es hier etwas zu tun ?
    }
    protected void loadGame(InputStream gameDefinitionInputStream) throws GameLoadingException {
        gameModel.addMessages(new GameLoader(() -> gameDefinitionInputStream).loadGame(DetectiveGameMessage[].class, (answer, id) -> {
            ((DetectiveGameAnswer) answer).setId(id);
            return (parent, targets) -> {
                ((DetectiveGameAnswer) answer).setParent(parent);
                ((DetectiveGameAnswer) answer).setTarget(targets[0]);
            };
        }));
        for(Message message : gameModel.getMessages().values()) {
            if(message.isRoot()) {
                Chat chat = new DetectiveGameChat(message.getId().hashCode(), new Chatpartner() {

                    private String id = message.getId();
                    private String image = ((DetectiveGameMessage)message).getChatImage();

                    @Override
                    public String getName() {
                        return id;
                    }

                    @Override
                    public String getImageUri() {
                        return image;
                    }
                });
                chat.addMessage(message, false);
            }
        }
    }

    @Override
    public Chat createNewChat() {
        throw new UnsupportedOperationException("Creating chats after the game has been loaded is not supported.");
    }
}
