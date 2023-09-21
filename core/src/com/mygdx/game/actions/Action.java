package com.mygdx.game.actions;

//Отрисовка траектории/области
//Выделение всех затронутых персонажей и объектов
//Трата действия персонажа


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainPool;
import com.mygdx.game.interfaces.Actionable;
import com.mygdx.game.interfaces.Collidable;

import java.util.ArrayList;

public abstract class Action {
    //flags
    boolean stopOnFirstCollision=true;
    boolean isSelected=false;
    Actionable master;
    int damage=10;
    int area=0;
    ArrayList<Actionable> targets = new ArrayList<>();

    public Action(Actionable master){
        this.master = master;
    }

    public void act() {
        for (Actionable target : targets) {
            target.dealHealth(damage);
        }
    }


    public void draw(float x, float y,
                     Stage stage,
                     ShapeRenderer shapeRenderer,
                     MainPool mainPool){

    }

    public void calculateArea(float x,float y, MainPool mainPool) {
        for (Collidable other : mainPool) {

        }
    }

    enum Area{

    }
 //        CIRCLE,
    //        CONE,
    //        SINGLE_TARGET,
    //        SHOT,
    //        CATAPULT,
    //        CHAIN,
    //        RANGE
}
