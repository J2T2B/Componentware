package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.*;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoader;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.*;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class DetectivGame extends AbstractGame<Void> {

    boolean created = false;

    public DetectivGame() throws GameLoadingException {
        super(DetectivGame.class, stream -> stream.filter(url -> url.toString().contains("detectivgame")).findFirst().get());
    }

    @Override
    protected void updateGameState(PlayResult res) {

    }

    @Override
    public CreateChatResult createNewChat() {
        DetectiveGameMessage rootMessage = (DetectiveGameMessage) gameModel.getSomeRootMessage();
        if(!rootMessage.getId().equals("telefonDaten") || created) {
            return null;
        }
        created = true;
        List<Answer> unlockedTotal = unlockAll(rootMessage.getUnlockKeys());
        Chat chat = new ChatImpl(createChatpartner(rootMessage));
        chat.addMessage(new Chat.ChatMessage(rootMessage));
        gameState.addChat(chat);
        if(unlockedTotal.size() > 0) {
            Map<Long, List<Answer>> unlockedPerChat = new HashMap<>();
            for(Chat openChat : gameState.getOpenChats()) {
                SimpleMessage msg = openChat.getMessages().get(openChat.getMessages().size() - 1).getMsg();
                if(msg instanceof Chat.ChatMessage) {
                    msg = ((Chat.ChatMessage) msg).getMsg();
                }
                if(msg instanceof DetectiveGameMessage) {
                    DetectiveGameMessage realMsg = (DetectiveGameMessage) gameModel.getMessage(((DetectiveGameMessage) msg).getId());
                    for(DetectiveGameAnswer answer : realMsg.getAnswers()) {
                        if(unlockedTotal.contains(answer)) {
                            unlockedPerChat.computeIfAbsent(openChat.getId(), l -> new ArrayList<>()).add(answer);
                            ((DetectiveGameMessage) msg).getAnswers().add(answer);
                        }
                    }
                }
            }
            return new ChatCreationWithExtraAnswers(chat, unlockedPerChat);
        }
        return new SimpleChatCreation(chat);
    }

    @Override
    public Chatpartner produceSomeChatpartner() {
        return new ChatpartnerImpl();
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
                Chat chat = new DetectiveGameChat(message.getId().hashCode(), createChatpartner((DetectiveGameMessage) message));
                chat.addMessage(new Chat.ChatMessage(message, System.currentTimeMillis(), false));
                gameState.addChat(chat);
            }
        }
    }

    @Override
    protected PlayResult playAnswer(Answer answerOrig) {
        DetectiveGameAnswer answer = (DetectiveGameAnswer) answerOrig;
        DetectiveGameMessage target = (DetectiveGameMessage) gameModel.getMessage(answer.firstTarget().getId());

        target.getUnlockKeys().forEach(this::unlock);

        if(answer.getRemoveAnswers() != null) {
            this.removeAnswers(answer.getRemoveAnswers());
        }

        // Create a new DetectiveGameMessage containing only the answers which are to be sent to the client
        List<DetectiveGameAnswer> answers = new ArrayList<>(target.getAnswers());
        Iterator<DetectiveGameAnswer> it = answers.iterator();
        boolean changed;
        do {
            changed = false;
            while(it.hasNext()) {
                DetectiveGameAnswer nextAnswer = it.next();
                if(nextAnswer.isLocked(gameModel)) {
                    it.remove();
                    changed = true;
                }
            }
        } while (changed);
        target = new DetectiveGameMessage(target.getId(), target.getDelay(), target.getText(), answers, target.isRoot());

        if(target.getAnswers().size() == 1 && target.firstAnswer().getText() == null) {
            removeSingleAnswer(answer.getParent().getId(), answer.getId());
            PlayResult firstAnswerTargetResult = playAnswer(target.firstAnswer());
            List<Chat.ChatMessage> resultMessages = new ArrayList<>();
            resultMessages.add(new Chat.ChatMessage(target));
            resultMessages.addAll(firstAnswerTargetResult.getMessages());
            return new PlayResultMessage(resultMessages.toArray(new Chat.ChatMessage[0]));
        }
        return new PlayResultMessage(new Chat.ChatMessage(target));
    }

    private void removeAnswers(String removeAnswers) {
        Message msg = gameModel.getMessage(removeAnswers);
        msg.getAnswers().clear();

        // TODO add removeId to gameState
    }

    private void removeSingleAnswer(String msgId, int answerId) {
        Message msg = gameModel.getMessage(msgId);
        msg.getAnswers().removeIf(answer -> answer.getId() == answerId);

        // TODO add to gameState
    }

    private List<Answer> unlockAll(List<String> unlockKeys) {
        return unlockKeys.stream().map(this::unlock).flatMap(unlocked -> unlocked.stream()).collect(Collectors.toList());
    }

    /**
     *
     * @param unlockKey the unlock key to unlock the answers
     * @return the unlocked answers
     */
    private List<Answer> unlock(String unlockKey) {
        List<Answer> unlocked = new ArrayList<>();
        for(Message msg : gameModel.getMessages().values()) {
            for(Answer answer : msg.getAnswers()) {
                if(((DetectiveGameAnswer) answer).getUnlockKey() != null && ((DetectiveGameAnswer) answer).getUnlockKey().equals(unlockKey)) {
                    if(((DetectiveGameAnswer) answer).isLocked(gameModel)) {
                        ((DetectiveGameAnswer) answer).setLocked(false);
                        unlocked.add(answer);
                    }
                }
            }
        }

        // TODO add to gameState

        return unlocked;
    }

    private Chatpartner createChatpartner(DetectiveGameMessage msg) {
        return new Chatpartner() {
            private String name = msg.getId();
            private String image = ((DetectiveGameMessage)msg).getChatImage();

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getImageUri() {
                return image;
            }
        };
    }
}
