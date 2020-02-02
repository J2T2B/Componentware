package de.fhdortmund.j2t2.wise2019.server.game.remote.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

public class PointsRemoteModel {
    private int customerExperience;
    private int chefSatisfaction;
    private int budget;

    public PointsRemoteModel(Points points){
        this.customerExperience = points.getCustomerExperience();
        this.chefSatisfaction = points.getChefSatisfaction();
        this.budget = points.getBudget();
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
