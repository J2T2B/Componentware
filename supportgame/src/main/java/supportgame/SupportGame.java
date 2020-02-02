package supportgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Chatpartner;
import de.fhdortmund.j2t2.wise2019.gamelogic.Points;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.AbstractGame;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResult;

import java.io.InputStream;

public class SupportGame extends AbstractGame<Points> {

    public SupportGame() throws GameLoadingException {
        super(SupportGame.class, stream -> stream.filter(url -> url.toString().contains("supportgame")).findFirst().get());
    }

    @Override
    protected void updateGameState(PlayResult res) {
        Points points = gameState.getData();
        res.getMessage();
    }

    @Override
    public Chatpartner produceSomeChatpartner() {
        return null;
    }

    @Override
    protected void loadGame(InputStream gameDefinitionInputStream) throws GameLoadingException {

    }
}
