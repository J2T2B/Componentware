package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.gamelogic.Points;
import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;
import de.fhdortmund.j2t2.wise2019.server.game.remote.models.PointsRemoteModel;

public class ChangePointsWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "ChangePoints";
    PointsRemoteModel points;

    public ChangePointsWebSocketCommand(Points points) {
        super(COMMAND);
        this.points = new PointsRemoteModel(points);
    }
}


