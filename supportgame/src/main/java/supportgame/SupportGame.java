package supportgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.*;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoader;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.AbstractGame;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResult;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResultMessage;
import supportgame.members.ProfileLoader;

import java.io.InputStream;

public class SupportGame extends AbstractGame<Points> {

    public SupportGame() throws GameLoadingException {
        super(SupportGame.class, stream -> stream.filter(url -> url.toString().contains("supportgame")).findFirst().get());
        if (gameState.getData() == null) {
            gameState.setData(new PointsImpl(50, 50, 50));
        }
    }

    @Override
    protected void updateGameState(PlayResult res) {
        Points nextPoints = gameState.getData();

        for (ChatMessage cm : res.getMessages()) {
            SimpleMessage sm = cm.getMsg();

            if (sm instanceof Message) {
                Message m = (Message) sm;
                nextPoints = nextPoints.add(m.getPoints());
            }
        }

        gameState.setData(nextPoints);
    }

    @Override
    public ChatPartner produceSomeChatpartner() {
        return ProfileLoader.getRandom();
    }

    @Override
    protected void loadGame(InputStream gameDefinitionInputStream) throws GameLoadingException {
        gameModel.addMessages(new GameLoader(() -> gameDefinitionInputStream).loadGame(MessageImpl[].class, (answer, id) -> {
            ((AnswerImpl) answer).setId(id);
            return (parent, targets) -> {
                ((AnswerImpl) answer).setParent(parent);
                ((AnswerImpl) answer).setTargets(targets);
            };
        }));
    }

    @Override
    protected PlayResult playAnswer(Answer answer) {
        PlayResult res;

        if (answer.getTargets().size() == 1) {
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

    @Override
    public void updateGameModel() {
        //nichts zu tun
    }
}
