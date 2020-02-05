package supportgame;

import de.fhdortmund.j2t2.wise2019.gamelogic.Points;

public class PointsImpl implements Points {

    private int chefSatisfaction = 0;
    private int customerExperience = 0;
    private int budget = 0;

    public PointsImpl(int chefSatisfaction, int customerExperience, int budget) {
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

    public Points add(Points points) {
        return new PointsImpl(calc(chefSatisfaction + points.getChefSatisfaction()),
                calc(customerExperience + points.getCustomerExperience()),
                calc(budget + points.getBudget()));
    }

    private static int calc(int points) {
        return Math.min(points, 100);
    }
}
