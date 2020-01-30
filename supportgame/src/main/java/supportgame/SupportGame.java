package supportgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Points;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.AbstractGame;

public class SupportGame extends AbstractGame<Points> {

    public SupportGame() throws GameLoadingException {
        super(SupportGame.class.getClassLoader());
    }
}
