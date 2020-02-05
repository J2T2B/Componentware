package de.fhdortmund.j2t2.wise2019.gamelogic;

import java.io.Serializable;

public interface Points extends Serializable {
    int getChefSatisfaction();
    int getCustomerExperience();
    int getBudget();
}
