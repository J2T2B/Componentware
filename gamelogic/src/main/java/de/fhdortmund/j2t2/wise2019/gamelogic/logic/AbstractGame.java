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
    protected GameState<T> gameState;

    /**
     *
     * @param gameClass Game Klasse welche zum laden der gameDefinition.json genutzt wird.
     * @throws GameLoadingException wenn die gameDefintion.json nicht geladen werden kann oder ein sonstiger Fehler bei dem Laden
     * der Spieldefinition auftritt
     */
    protected AbstractGame(Class<? extends Game> gameClass, Function<Stream<URL>, URL> resourceSelector, Class<? extends T> clazz) throws GameLoadingException {
        gameState = new GameState<>(clazz);
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

        Chat.ChatMessageImpl chatMessage = new Chat.ChatMessageImpl(answer.getText(), Calendar.getInstance().getTimeInMillis(), true);
        gameState.getChat(chat.getId()).addMessage(chatMessage);
        res = playAnswer(answer);
        for(Chat.ChatMessageImpl message : res.getMessages()) {
            gameState.getChat(chat.getId()).addMessage(message);
        }
        updateGameState(res);
        return res;
    }

    /**
     * Erstelle irgendeinen Chatpartner
     * @return erstellten Chatpartner
     */
    public abstract ChatPartner produceSomeChatpartner();



    @Override
    public GameState<T> getGameState() {
        return gameState;
    }

    @Override
    public Chat createNewChat() {
        ChatPartner chatpartner = produceSomeChatpartner();
        Chat chat = new ChatImpl(chatpartner);
        Message rootMessage = gameModel.getSomeRootMessage();
        chat.addMessage(new Chat.ChatMessageImpl(rootMessage));
        gameState.addChat(chat);
        return chat;
    }

    protected abstract PlayResult playAnswer(Answer answer);
}
