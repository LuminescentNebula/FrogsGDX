package com.mygdx.game.interfaces;

public interface Health extends Collidable{
    //ArrayList<Action> getActions();
    void setHealth(int health);
    int getHealth();
    void dealHealth(int health);
    int getMaxHealth();
    void setMaxHealth(int maxHealth);
    //TODO: устанавливает, что цель выбрана,затем нужно на ней отрисовать
    boolean setTargeted(boolean targeted);
    //TODO: эффекты
//    boolean isDead();
//    void die();
}
