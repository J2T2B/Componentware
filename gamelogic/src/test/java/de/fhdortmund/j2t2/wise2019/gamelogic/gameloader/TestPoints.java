package de.fhdortmund.j2t2.wise2019.gamelogic.gameloader;

import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

public class TestPoints implements Points {

    int chefSatisfaction = 0;
    int customerExperience = 0;
    int budget = 0;

    public TestPoints(int chefSatisfaction, int customerExperience, int budget) {
        this.chefSatisfaction = chefSatisfaction;
        this.customerExperience = customerExperience;
        this.budget = budget;
    }

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

    @Override
    public Points add(Points points) {
        return new TestPoints(calc(chefSatisfaction + points.getChefSatisfaction()),
                calc(customerExperience + points.getCustomerExperience()),
                calc(budget + points.getBudget()));
    }

    @Override
    public boolean anyZeroOrLower() {
        return chefSatisfaction <= 0 || customerExperience <= 0 || budget <= 0;
    }

    private static int calc(int points) {
        return Math.min(points, 100);
    }
}
