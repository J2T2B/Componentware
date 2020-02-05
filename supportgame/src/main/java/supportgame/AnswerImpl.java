package supportgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;

import java.util.Arrays;
import java.util.List;

public class AnswerImpl implements Answer {

    private String text;
    private List<String> targets;

    private MessageImpl parent;
    private int id;
    private List<MessageImpl> resolvedTargets;

    @Override
    public Message getParent() {
        return parent;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public List<? extends Message> getTargets() {
        return resolvedTargets;
    }

    @Override
    public List<String> getTargetIds() {
        return targets;
    }

    void setId(Integer id) {
        this.id = id;
    }

    void setParent(MessageImpl parent) {
        this.parent = parent;
    }

    void setTargets(MessageImpl[] targets) {
        this.resolvedTargets = Arrays.asList(targets);
    }
}
