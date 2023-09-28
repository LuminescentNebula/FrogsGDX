package com.mygdx.game.interfaces;

public interface Health extends Collidable{
    //ArrayList<Action> getActions();
    void setHealth(int health);
    int getHealth();
    void dealHealth(int health);
    int getMaxHealth();
    void setMaxHealth(int maxHealth);
//    boolean isDead();
//    void die();
}
