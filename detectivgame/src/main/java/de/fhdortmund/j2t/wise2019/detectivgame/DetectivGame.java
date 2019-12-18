package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.Message;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoader;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoadingException;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameModel;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResult;

import java.util.List;

public class DetectivGame implements Game {

    GameModel model = new GameModel();
    public DetectivGame() throws GameLoadingException {
        GameLoader gameLoader = new GameLoader(DetectivGame.class.getClassLoader().getResource("gameDefinition.json").getPath());
        model.addMessages(gameLoader.loadGame());
    }
    @Override
    public PlayResult playAnswer(Answer answer) {
        return null;
    }
}
