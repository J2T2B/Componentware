package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

public class TestPoints implements Points {

    int chefSatisfaction = 0;
    int customerExperience = 0;
    int budget = 0;

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
