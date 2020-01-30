package de.fhdortmund.j2t2.wise2019.server.user.sessionmanager;

import com.google.common.collect.Lists;
import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Chat;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.GameState;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResult;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResultData;
import de.fhdortmund.j2t2.wise2019.server.game.local.GameManagerLocal;
import de.fhdortmund.j2t2.wise2019.server.game.models.ChatImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;

public class MockGameManager implements GameManagerLocal {



    private Game game = new Game() {

        private GameState<String> gameState = new GameState<>();

        @Override
        public PlayResult playAnswer(Answer answer) {
            return new PlayResult() {
                @Override
                public Chat.ChatMessage getMessage() {
                    return message1;
                }

                @Override
                public boolean isEnd() {
                    return false;
                }

                @Override
                public List<PlayResultData> getPlayResultData() {
                    return Collections.singletonList(new PlayResultData() {
                    });
                }
            };
        }

        @Override
        public PlayResult playAnswer(int answerId) {
            return null;
        }

        @Override
        public Chat createNewChat() {
            Chat chat = new ChatImpl();
            gameState.addChat(chat);
            return chat;
        }

        @Override
        public GameState<String> getGameState() {
            return gameState;
        }
    };

    public MockGameManager(){
        game.createNewChat();
        game.getGameState().getOpenChats().get(0).addMessage(message1, false);
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
        public String getImage() {
            return "https://s3.amazonaws.com/baconmockup/img/bm-home-280.jpg";
        }

        @Override
        public int getDelay() {
            return RandomUtils.nextInt();
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
        public Collection<? extends Answer> getAnswers() {
            Answer answer1 = new Answer() {
                @Override
                public Message getParent() {
                    return message1;
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
                public List<Message> getTargets() {
                    return Arrays.asList(messages);
                }
            };

            return Lists.newArrayList(answer1);
        }
    };

    private static final Chat.ChatMessage message1= new Chat.ChatMessage(realMessage1, 0, false);

    private static Chat.ChatMessage[] messages = {message1};
}
