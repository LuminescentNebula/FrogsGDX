package com.mygdx.game.actions;

//TODO:Выделение всех затронутых персонажей и объектов

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.pools.MainPool;
import com.mygdx.game.actions.types.*;
import com.mygdx.game.interfaces.Attackable;
import com.mygdx.game.interfaces.DrawInterface;
import com.mygdx.game.interfaces.Health;

import java.util.*;
import java.util.stream.Stream;


public class Attack {
    private boolean isSelected=false;
    private Attackable master;
    private float actionCost;
    private ArrayList<Type> types;
    boolean chainChecking = false; //Temporal while chain checking

    public static final Color areaColor=new Color(1,1,0,0.3f);

    public Attack(){;
    }

    public void setTypes(ArrayList<Type> types) {
        this.types = types;
    }

    public void check(ShapeRenderer shapeRenderer, Attackable master, Vector2 cursor,MainPool mainPool){
        shapeRenderer.setColor(new Color(areaColor));
        flushTargets();
        for (Type type : types ){
            type.performPre(master,cursor,mainPool);
            draw(type,shapeRenderer,master.getCenter(),cursor, type.getMinLength(),type.getMaxLength(),type.getRadius());
            act(type,mainPool,shapeRenderer,master,cursor,type.getRadius());
            type.performAfter(master,cursor,mainPool);
        }
    }

    public void deal(){
        for (Type type: types) {
            type.deal();
        }
    }

    public void draw(DrawInterface drawInterface, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        drawInterface.draw(shapeRenderer,master,cursor,minLength,maxLength,radius);
    }

    public void flushTargets() {
        types.forEach(Type::flushTargets);
    }

    public void act(Type type, MainPool mainPool, ShapeRenderer shapeRenderer, Attackable master, Vector2 cursor, float radius) {
        Stream<Health> stream = mainPool.get(Health.class).stream();
        if (type.flags.is(Flag.checkNotMaster)) {
            stream = (stream.filter(other -> master.getId() != other.getId()));
        }

        if (chainChecking) {  //Для вызовов, которые проверяют цепочки
            stream = stream.filter(other -> type.chainCheck(other, shapeRenderer,
                    new Circle(cursor, radius)));
        } else { //Проверяем коллизию для всех остальных
            stream = stream.filter(other -> type.check(other, master.getCenter(), cursor,
                    new Circle(cursor, radius)));
        }

        if (type.flags.is(Flag.stopOnFirstCollision)) { //Если есть stop onFirstCollision, то добавляется только первая найденная цель
            stream = stream.sorted(Comparator.comparing(health -> health.getCenter().dst(master.getCenter())));
            type.flags.del(Flag.stopOnFirstCollision);
            chainChecking=true;
            stream.findFirst().ifPresent(other -> { //Если есть chainDamage, то вызывается act, но с удаленным stopOnFirst и отметкой chainChecking, что идет проверка цепи
                if (type.isAdd(other) && type.flags.is(Flag.chainDamage)) act(type, mainPool, shapeRenderer, master, other.getCenter(), radius);
            }); //Цепочка тоже начинается только от первой цели
            chainChecking=false;
            type.flags.add(Flag.stopOnFirstCollision);
        } else {
            stream.forEach(other -> { //Цепочка начинается от каждой цели
                if (type.isAdd(other) && type.flags.is(Flag.chainDamage)) {
                    act(type,mainPool, shapeRenderer, master, other.getCenter(), radius);
                };
            });
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public void setMaster(Attackable master) {
        this.master = master;
    }

    public float getActionCost(){
        return actionCost;
    }
    public void  setActionCost(float cost){
        this.actionCost=cost;
    }
}
