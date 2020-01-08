package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader.models;

import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

public class PointsJson implements Points {

    private Integer chefSatisfaction;
    private Integer customerExperience;
    private Integer budget;

    @Override
    public int getChefSatisfaction() {
        return chefSatisfaction;
    }

    @Override
    public int getCustomerExperience() {
        return customerExperience;
    }

    @Override
    public int getBudget() {
        return budget;
    }
}
