package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.*;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoader;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.*;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class DetectivGame extends AbstractGame<DetectiveGameData> {

    public DetectivGame() throws GameLoadingException {
        super(DetectivGame.class, stream -> stream.filter(url -> url.toString().contains("detectivgame")).findFirst().get(), DetectiveGameData.class);
    }

    @Override
    protected void updateGameState(PlayResult res) {

    }

    @Override
    public ChatPartner produceSomeChatpartner() {
        return null;
    }

    @Override
    public CreateChatResult createNewChat() {
        DetectiveGameMessage rootMessage;
        if (gameState.getData().getSendTelefonDatenCount() == 0) {
            gameState.getData().setSendTelefonDatenCount(1);
            rootMessage = (DetectiveGameMessage) gameModel.getMessage("mainRoot");
        } else if (gameState.getData().getSendTelefonDatenCount() >= 3 && !gameState.getData().isTelefonDatenSent()) {
            gameState.getData().setTelefonDatenSent(true);
            rootMessage = (DetectiveGameMessage) gameModel.getMessage("telefonDaten");
        } else {
            return null;
        }
        List<Answer> unlockedTotal = unlockAll(rootMessage.getUnlockKeys());
        Chat chat = new ChatImpl(createChatpartner(rootMessage));
        chat.addMessage(new Chat.ChatMessageImpl(rootMessage));
        gameState.addChat(chat);
        if (unlockedTotal.size() > 0) {
            Map<Long, List<Answer>> unlockedPerChat = new HashMap<>();
            for (Chat openChat : gameState.getOpenChats()) {
                SimpleMessage msg = openChat.getMessages().get(openChat.getMessages().size() - 1).getMsg();
                if (msg instanceof ChatMessage) {
                    msg = ((ChatMessage) msg).getMsg();
                }
                if (msg instanceof DetectiveGameMessage) {
                    DetectiveGameMessage realMsg = (DetectiveGameMessage) gameModel.getMessage(((DetectiveGameMessage) msg).getId());
                    for (DetectiveGameAnswer answer : realMsg.getAnswers()) {
                        if (unlockedTotal.contains(answer)) {
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
    public void updateGameModel() {
        if (gameState.getData() == null) {
            gameState.setData(new DetectiveGameData());
        } else {
            DetectiveGameData data = gameState.getData();
            gameState.setData(new DetectiveGameData());
            gameState.getData().setTelefonDatenSent(data.isTelefonDatenSent());
            gameState.getData().setSendTelefonDatenCount(data.getSendTelefonDatenCount());
            for (String removeAnswers : data.getRemovedAnswerIds()) {
                removeAnswers(removeAnswers);
            }
            unlockAll(data.getUnlockKeys());
            for (Map.Entry<String, Integer> entry : data.getSingleRemovedAnswers().entrySet()) {
                removeSingleAnswer(entry.getKey(), entry.getValue());
            }
        }
    }

    protected void loadGame(InputStream gameDefinitionInputStream) throws GameLoadingException {
        gameModel.addMessages(new GameLoader(() -> gameDefinitionInputStream).loadGame(DetectiveGameMessage[].class, (answer, id) -> {
            ((DetectiveGameAnswer) answer).setId(id);
            return (parent, targets) -> {
                ((DetectiveGameAnswer) answer).setParent(parent);
                ((DetectiveGameAnswer) answer).setTarget(targets[0]);
            };
        }));
    }

    @Override
    protected PlayResult playAnswer(Answer answerOrig) {
        DetectiveGameAnswer answer = (DetectiveGameAnswer) answerOrig;
        DetectiveGameMessage target = (DetectiveGameMessage) gameModel.getMessage(answer.firstTarget().getId());

        target.getUnlockKeys().forEach(this::unlock);

        if (answer.getRemoveAnswers() != null) {
            this.removeAnswers(answer.getRemoveAnswers());
        }

        // Create a new DetectiveGameMessage containing only the answers which are to be sent to the client
        List<DetectiveGameAnswer> answers = new ArrayList<>(target.getAnswers());
        Iterator<DetectiveGameAnswer> it = answers.iterator();
        while (it.hasNext()) {
            DetectiveGameAnswer nextAnswer = it.next();
            if (nextAnswer.isLocked(gameModel)) {
                it.remove();
            }
        }
        target = new DetectiveGameMessage(target.getId(), target.getDelay(), target.getText(), answers, target.isRoot());

        if (target.getAnswers().size() == 1 && target.firstAnswer().getText() == null) {
            removeSingleAnswer(answer.getParent().getId(), answer.getId());
            PlayResult firstAnswerTargetResult = playAnswer(target.firstAnswer());
            List<ChatMessage> resultMessages = new ArrayList<>();
            resultMessages.add(new Chat.ChatMessageImpl(target));
            resultMessages.addAll(firstAnswerTargetResult.getMessages());
            return new PlayResultMessage(resultMessages.toArray(new Chat.ChatMessageImpl[0]));
        }
        return new PlayResultMessage(new Chat.ChatMessageImpl(target));
    }

    private void removeAnswers(String removeAnswers) {
        Message msg = gameModel.getMessage(removeAnswers);
        msg.getAnswers().clear();

        gameState.getData().addRemovedAnswerId(removeAnswers);
    }

    private void removeSingleAnswer(String msgId, int answerId) {
        Message msg = gameModel.getMessage(msgId);
        msg.getAnswers().removeIf(answer -> answer.getId() == answerId);

        gameState.getData().addSingleRemovedAnswer(msgId, answerId);
    }

    private List<Answer> unlockAll(List<String> unlockKeys) {
        return unlockKeys.stream().map(this::unlock).flatMap(unlocked -> unlocked.stream()).collect(Collectors.toList());
    }

    /**
     * @param unlockKey the unlock key to unlock the answers
     * @return the unlocked answers
     */
    private List<Answer> unlock(String unlockKey) {
        List<Answer> unlocked = new ArrayList<>();
        for (Message msg : gameModel.getMessages().values()) {
            for (Answer answer : msg.getAnswers()) {
                if (((DetectiveGameAnswer) answer).getUnlockKey() != null && ((DetectiveGameAnswer) answer).getUnlockKey().equals(unlockKey)) {
                    if (((DetectiveGameAnswer) answer).isLocked(gameModel)) {
                        ((DetectiveGameAnswer) answer).setLocked(false);
                        unlocked.add(answer);
                    }
                }
            }
        }

        gameState.getData().addUnlockKey(unlockKey);

        return unlocked;
    }

    private ChatPartner createChatpartner(DetectiveGameMessage msg) {
        return new ChatPartner() {
            private String name = msg.getChatName();
            private String image = msg.getChatImage();

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
