package com.mygdx.game.interfaces;

public interface Health {
    //ArrayList<Action> getActions();
    void setHealth(int health);
    int getHealth();
    void dealHealth(int health);
    int getMaxHealth();
    void setMaxHealth(int maxHealth);
//    boolean isDead();
//    void die();
}