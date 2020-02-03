package de.fhdortmund.j2t2.wise2019.server.user.sessionmanager;

import com.google.common.collect.Lists;
import de.fhdortmund.j2t2.wise2019.gamelogic.*;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.AbstractGame;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResult;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResultMessage;
import de.fhdortmund.j2t2.wise2019.server.game.local.GameManagerLocal;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MockGameManager implements GameManagerLocal {


    private Game game = new MockGame();

    public MockGameManager() throws GameLoadingException {
        game.createNewChat();
        game.getGameState().getOpenChats().get(0).addMessage(message1);
    }

    @Override
    public List<Game> getGamesForUser(String session) {
        return Collections.singletonList(game);
    }

    private String randomString(){
        return RandomStringUtils.random(RandomUtils.nextInt(1,50));
    }

    private static Message realMessage1 = new Message(){

        private boolean read = false;
        @Override
        public String getId() {
            return UUID.randomUUID().toString();
        }

        @Override
        public String getText() {
            return "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                    "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores" +
                    " et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. " +
                    "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut " +
                    "labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores" +
                    " et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
        }

        @Override
        public int getDelay() {
            return 5;
        }

        @Override
        public boolean isRoot() {
            return true;
        }

        @Override
        public Points getPoints() {
            return new Points() {
                @Override
                public int getChefSatisfaction() {
                    return 13;
                }

                @Override
                public int getCustomerExperience() {
                    return 42;
                }

                @Override
                public int getBudget() {
                    return 1337;
                }
            };
        }

        @Override
        public double getProbably() {
            return 0.5;
        }

        @Override
        public List<? extends Answer> getAnswers() {
            Answer answer1 = new Answer() {
                @Override
                public Message getParent() {
                    return realMessage1;
                }

                @Override
                public int getId() {
                    return 0;
                }

                @Override
                public String getText() {
                    return "I used to be an adventurer like you...then I took an arrow in the knee";
                }

                @Override
                public List<? extends Message> getTargets() {
                    return Arrays.asList(messages);
                }

                @Override
                public List<String> getTargetIds() {
                    return Arrays.stream(messages).map(msg -> msg.getId()).collect(Collectors.toList());
                }
            };

            return Lists.newArrayList(answer1);
        }
    };

    private static final Chat.ChatMessageImpl message1= new Chat.ChatMessageImpl(realMessage1, 0, false);

    private static Message[] messages = {realMessage1};

    private static class MockGame extends AbstractGame<String> {

        protected MockGame() throws GameLoadingException {
            super(MockGame.class, stream -> stream.findFirst().get(), String.class);
            gameModel.addMessages(Collections.singletonMap(realMessage1.getId(), realMessage1));
        }

        @Override
        protected void loadGame(InputStream gameDefinitionInputStream) throws GameLoadingException {

        }

        @Override
        protected void updateGameState(PlayResult res) {

        }

        @Override
        public Chatpartner produceSomeChatpartner() {
            return new ChatpartnerImpl();
        }

        @Override
        protected PlayResult playAnswer(Answer answer) {
            PlayResult res;

            if(answer.getTargets().size() == 1) {
                Message target = answer.getTargets().get(0);
                res = new PlayResultMessage(new Chat.ChatMessageImpl(target));
            } else {
                double random = Math.random();
                double sum = 0;
                Message msg = answer.getTargets().get(0);
                for (Message target : answer.getTargets()) {
                    if (random >= sum && random < (sum += target.getProbably())) {
                        msg = target;
                        break;
                    }
                }
                res = new PlayResultMessage(new Chat.ChatMessageImpl(msg));
            }
            return res;
        }
    }
}
