package com.mygdx.game.interfaces;

import com.mygdx.game.actions.Action;

import java.util.ArrayList;

public interface Health {
    //ArrayList<Action> getActions();
    void setHealth(int health);
    void dealHealth(int health);
    int getMaxHealth();
    void setMaxHealth(int maxHealth);
}
