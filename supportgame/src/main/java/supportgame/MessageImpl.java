package supportgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

import java.util.List;

public class MessageImpl implements Message {

    private String id;
    private int delay;
    private double probably;
    private String text;
    private List<AnswerImpl> answers;
    private PointsImpl points;
    private boolean isRoot;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public boolean isRoot() {
        return isRoot;
    }

    @Override
    public Points getPoints() {
        return points;
    }

    @Override
    public double getProbably() {
        return probably;
    }

    @Override
    public List<? extends Answer> getAnswers() {
        return answers;
    }

    @Override
    public String getText() {
        return text;
    }
}
