package supportgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.*;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.*;

import java.io.InputStream;

public class SupportGame extends AbstractGame<Points> {

    public SupportGame() throws GameLoadingException {
        super(SupportGame.class, stream -> stream.filter(url -> url.toString().contains("supportgame")).findFirst().get());
    }

    @Override
    protected void updateGameState(PlayResult res) {
        Points points = gameState.getData();
        res.getMessages();
    }

    @Override
    public Chatpartner produceSomeChatpartner() {
        return null;
    }

    @Override
    protected void loadGame(InputStream gameDefinitionInputStream) throws GameLoadingException {

    }

    @Override
    protected PlayResult playAnswer(Answer answer) {
        PlayResult res;

        if(answer.getTargets().size() == 1) {
            Message target = answer.getTargets().get(0);
            res = new PlayResultMessage(new Chat.ChatMessage(target));
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
            res = new PlayResultMessage(new Chat.ChatMessage(msg));
        }
        return res;
    }
}
