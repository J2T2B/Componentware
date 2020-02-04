package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.*;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoader;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DetectivGame extends AbstractGame<Void> {

    public DetectivGame() throws GameLoadingException {
        super(DetectivGame.class, stream -> stream.filter(url -> url.toString().contains("detectivgame")).findFirst().get(), Void.class);
    }

    @Override
    protected void updateGameState(PlayResult res) {

    }

    @Override
    public ChatPartner produceSomeChatpartner() {
        return new ChatPartnerImpl();
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
                Chat chat = new DetectiveGameChat(message.getId().hashCode(), new ChatPartner() {

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
                chat.addMessage(new Chat.ChatMessageImpl(message, System.currentTimeMillis(), false));
                gameState.addChat(chat);
            }
        }
    }

    @Override
    protected PlayResult playAnswer(Answer answer) {
        DetectiveGameMessage target = (DetectiveGameMessage) gameModel.getMessage(answer.firstTarget().getId());

        // Create a new DetectiveGameMessage containing only the answers which are to be sent to the client
        List<DetectiveGameAnswer> answers = new ArrayList<>(target.getAnswers());
        Iterator<DetectiveGameAnswer> it = answers.iterator();
        while(it.hasNext()) {
            DetectiveGameAnswer nextAnswer = it.next();
            if(nextAnswer.isLocked()) {
                it.remove();
            }
        }
        target = new DetectiveGameMessage(target.getId(), target.getDelay(), target.getText(), answers, target.isRoot());
        if(target.isEnd()) {
            return new PlayResultEnd(new Chat.ChatMessageImpl(target));
        }
        if(target.getAnswers().size() == 1 && target.firstAnswer().getText() == null) {
            answer.getParent().getAnswers().removeIf(elem -> elem.getId() == answer.getId());
            return new PlayResultMessage(new Chat.ChatMessageImpl(target), new Chat.ChatMessageImpl(target.firstAnswer().firstTarget()));
        }
        return new PlayResultMessage(new Chat.ChatMessageImpl(target));
    }
}
