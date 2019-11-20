package de.fhdortmund.j2t.wise2019.detectivgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Answer;
import de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.GameLoader;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.Game;
import de.fhdortmund.j2t2.wise2019.gamelogic.logic.PlayResult;

public class DetectivGame implements Game {

    public DetectivGame(){
        GameLoader gameLoader = new GameLoader(DetectivGame.class.getClassLoader().getResource("gameDefinition.json").getPath());
    }
    @Override
    public PlayResult playAnswer(Answer answer) {
        return null;
    }
}
