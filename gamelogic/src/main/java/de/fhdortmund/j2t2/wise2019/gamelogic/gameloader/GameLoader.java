package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import com.google.gson.Gson;
import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameLoader {

    private InputStream gameJsonStream;
    private Gson gson = new Gson();

    private Map<String, Message> cachedMessages;

    public GameLoader(InputStream gameJsonStream) {
        if(gameJsonStream == null){
            throw new NullPointerException("gameJsonStream may not be null!");
        }
        this.gameJsonStream = gameJsonStream;
    }

    public synchronized Map<String, ? extends Message> loadGame() throws GameLoadingException {
        if (cachedMessages == null) {
            try (Reader reader = new InputStreamReader(gameJsonStream)) {
                MessageJson[] messages = gson.fromJson(reader, MessageJson[].class);

                cachedMessages = new HashMap<>();
                for(MessageJson msg : messages) {
                    List<Answer> answers = new ArrayList<>();
                    for(int i = 0; i < msg.getAnswers().length; i++) {
                        AnswerJson answer = msg.getAnswers()[i];
                        List<Message> targets = new ArrayList<>();
                        for(String targetId : answer.getTargets()) {
                            targets.add(cachedMessages.getOrDefault(targetId, new MessageReference(targetId)));
                        }
                        answers.add(new ResolvedAnswer(new MessageReference(msg.getId()), i, targets, answer.getText()));
                    }
                    cachedMessages.put(msg.getId(), new ResolvedMessage(msg.getId(), msg.getText(), msg.getImage(), msg.getDelay(), msg.isRoot(), msg.getPoints(), msg.getProbably(), answers));
                }
                for(Message msg : cachedMessages.values()) {
                    for(Answer answer : msg.getAnswers()) {
                        ((MessageReference) answer.getParent()).setResolved(cachedMessages.get(answer.getParent().getId()));
                        for(Message target : answer.getTargets()) {
                            if(target instanceof MessageReference) {
                                ((MessageReference) target).setResolved(cachedMessages.get(target.getId()));
                            }
                        }
                    }
                }

            } catch (IOException e) {
                throw new GameLoadingException("Unable to load game", e);
            }
        }

        return cachedMessages;
    }
}
