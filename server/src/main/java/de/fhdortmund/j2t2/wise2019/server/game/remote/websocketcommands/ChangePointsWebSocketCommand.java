package de.fhdortmund.j2t2.wise2019.server.game.remote.websocketcommands;

import de.fhdortmund.j2t2.wise2019.server.commons.remote.AbstractWebSocketCommand;

public class ChangePointsWebSocketCommand extends AbstractWebSocketCommand {

    private static final String COMMAND = "ChangePoints";
    private final int customerExperience;
    private final int chefSatisfaction;
    private final int budget;

    public ChangePointsWebSocketCommand(int customerExperience, int chefSatisfaction, int budget) {
        super(COMMAND);
        this.customerExperience = customerExperience;
        this.chefSatisfaction = chefSatisfaction;
        this.budget = budget;
    }

    public int getCustomerExperience() {
        return customerExperience;
    }

    public int getChefSatisfaction() {
        return chefSatisfaction;
    }

    public int getBudget() {
        return budget;
    }
}


