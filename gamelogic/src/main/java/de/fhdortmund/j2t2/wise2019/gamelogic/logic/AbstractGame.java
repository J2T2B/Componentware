package de.fhdortmund.j2t2.wise2019.gamelogic.logic;

import de.fhdortmund.j2t2.wise2019.gamelogic.*;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Implementierende Klassen müssen eine gameDefinition.json auf dem Root der Jar haben.
 * @param <T> Typ der Daten, die GameState halten soll
 */
public abstract class AbstractGame<T> implements Game {
    protected GameModel gameModel  = new GameModel();
    protected GameState<T> gameState = new GameState<>();

    /**
     *
     * @param gameClass Game Klasse welche zum laden der gameDefinition.json genutzt wird.
     * @throws GameLoadingException wenn die gameDefintion.json nicht geladen werden kann oder ein sonstiger Fehler bei dem Laden
     * der Spieldefinition auftritt
     */
    protected AbstractGame(Class<? extends Game> gameClass, Function<Stream<URL>, URL> resourceSelector) throws GameLoadingException {
        URL gameDefinitionURL = resourceSelector.apply(getGameDefinitions(gameClass));
        try(InputStream in = gameDefinitionURL.openStream()) {
            loadGame(in);
        } catch (NullPointerException | IOException e) {
            throw new GameLoadingException("Unable to load gameDefinitition " + gameDefinitionURL.toString(), e);
        }
    }

    private Stream<URL> getGameDefinitions(Class<? extends Game> gameClass) {
        try {
            Enumeration<URL> jsons = gameClass.getClassLoader().getResources("gameDefinition.json");
            return StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(
                            new Iterator<URL>() {
                                public URL next() {
                                    return jsons.nextElement();
                                }
                                public boolean hasNext() {
                                    return jsons.hasMoreElements();
                                }
                            },
                            Spliterator.ORDERED),
                    false);
        } catch (IOException e) {
            return Stream.<URL>builder().build();
        }
    }

    protected abstract void loadGame(InputStream gameDefinitionInputStream) throws GameLoadingException;

    protected abstract void updateGameState(PlayResult res);

    @Override
    public PlayResult playAnswer(Chat chat, Answer answer) {
        PlayResult res;

        Chat.ChatMessage chatMessage = new Chat.ChatMessage(answer.getText(), Calendar.getInstance().getTimeInMillis(), true);
        gameState.getChat(chat.getId()).addMessage(chatMessage);
        res = playAnswer(answer);
        gameState.getChat(chat.getId()).addMessage(new Chat.ChatMessage(res.getMessage()));
        updateGameState(res);
        return res;
    }

    /**
     * Erstelle irgendeinen Chatpartner
     * @return erstellten Chatpartner
     */
    public abstract Chatpartner produceSomeChatpartner();



    @Override
    public GameState<T> getGameState() {
        return gameState;
    }

    @Override
    public Chat createNewChat() {
        Chatpartner chatpartner = produceSomeChatpartner();
        Chat chat = new ChatImpl(chatpartner);
        Message rootMessage = gameModel.getSomeRootMessage();
        gameState.addChat(chat);
        return chat;
    }

    private PlayResult playAnswer(Answer answer){
        PlayResult res;

        if(answer.getTargets().size() < 1) {
            res = new PlayResultEnd();
        } else if(answer.getTargets().size() == 1) {
            Message target = answer.getTargets().get(0);
            if(target.getAnswers().size() == 0) {
                res = new PlayResultEnd(target);
            } else {
                res = new PlayResultMessage(target);
            }
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
            res = new PlayResultMessage(msg);
        }
        return res;
    }
}
