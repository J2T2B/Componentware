package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoader;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.*;

import java.io.InputStream;

public class DetectivGame extends AbstractGame<Void> {

    public DetectivGame() throws GameLoadingException {
        super(DetectivGame.class, stream -> stream.filter(url -> url.toString().contains("detectivgame")).findFirst().get());
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

                    private String name = message.getId();
                    private String image = ((DetectiveGameMessage)message).getChatImage();

                    @Override
                    public String getName() {
                        return name;
                    }

                    @Override
                    public String getImageUri() {
                        return image;
                    }
                });
                chat.addMessage(new Chat.ChatMessage(() -> message.getText(), System.currentTimeMillis(), false));
                gameState.addChat(chat);
            }
        }
    }
}
