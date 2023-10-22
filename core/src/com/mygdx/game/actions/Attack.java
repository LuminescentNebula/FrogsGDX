package com.mygdx.game.actions;

//TODO:Выделение всех затронутых персонажей и объектов
//TODO:Трата действия персонажа

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainPool;
import com.mygdx.game.actions.types.*;
import com.mygdx.game.interfaces.Attackable;
import com.mygdx.game.interfaces.Health;

import java.util.*;
import java.util.stream.Stream;

//TODO:Нужно рисовать зону поражения в act
//TODO:Настройки урона
//TODO:фабрика нужна
//        CONE,
//        SINGLE_TARGET, CIRCLE
//        SHOT,
//        CATAPULT,
//        CHAIN -- режим
//        RANGE

public class Attack {
    private boolean isSelected=false;
    private Attackable master;

    private ArrayList<Type> types;
    private Flag flags;
//    private float radius; //chainradius=radius
//    private float minLength;
//    private float maxLength;
//    private int damage = 10;
    //TODO: Переместить в Type
    private Set<Health> targets = new LinkedHashSet<>();
    boolean chainChecking = false; //Temporal while chain checking

    public static final Color areaColor=new Color(1,1,0,0.3f);

    public Attack(){;
    }

    public void setTypes(ArrayList<Type> types) {
        this.types = types;
    }

    public void check(ShapeRenderer shapeRenderer, Attackable master, Vector2 cursor,MainPool mainPool){
        for (Type type : types ){
            type.performPre(master.getCenter(),cursor,mainPool);
            draw(type::draw,shapeRenderer,master.getCenter(),cursor, type.getMinLength(),type.getMaxLength(),type.getRadius());
            act(type::check,mainPool,shapeRenderer,master,cursor,type.getRadius());
        }
    }

//    private void deal(){
//        for (Health target: targets) {
//            target.dealHealth(damage);
//        }
//    }

    public void draw(DrawInterface drawInterface, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        drawInterface.draw(shapeRenderer,master,cursor,minLength,maxLength,radius);
        flushTargets();
    }

    public void flushTargets() {
        System.out.print("{");
        for (Health health:targets) {
            System.out.print(health.getId()+" ");
            health.setTargeted(false);
        }
        System.out.print("}\n");
        targets = new HashSet<>();
    }

    public void act(ActInterface actInterface, MainPool mainPool, ShapeRenderer shapeRenderer, Attackable master, Vector2 cursor, float radius) {
        shapeRenderer.setColor(new Color(areaColor));


        Stream<Health> stream = mainPool.getHealths().stream();
        if (flags.is(Flag.checkNotMaster)) {
            stream = (stream.filter(other -> master.getId() != other.getId()));
        }

        if (chainChecking) {  //Для вызовов, которые проверяют цепочки
            stream = stream.filter(other -> Type.chainCheck(other, shapeRenderer,
                    new Circle(cursor, radius)));
        } else { //Проверяем коллизию для всех остальных
            stream = stream.filter(other -> actInterface.check(other, master.getCenter(), cursor,
                    new Circle(cursor, radius)));
        }

        if (flags.is(Flag.stopOnFirstCollision)) { //Если есть stop onFirstCollision, то добавляется только первая найденная цель
            flags.del(Flag.stopOnFirstCollision);
            chainChecking=true;
            stream.findFirst().ifPresent(other -> { //Если есть chainDamage, то вызывается act, но с удаленным stopOnFirst и отметкой chainChecking, что идет проверка цепи
                if (isAdd(other) && flags.is(Flag.chainDamage)) act(actInterface, mainPool, shapeRenderer, master, other.getCenter(), radius);
            }); //Цепочка тоже начинается только от первой цели
            chainChecking=false;
            flags.add(Flag.stopOnFirstCollision);
        } else {
            stream.forEach(other -> { //Цепочка начинается от каждой цели
                if (isAdd(other) && flags.is(Flag.chainDamage)) {
                    act(actInterface,mainPool, shapeRenderer, master, other.getCenter(), radius);
                };
            });
        }
    }
    /**
     * Trying to add given Health object should and return success of operation.
     *
     * @param  other  the Health object to be added
     * @return        true if the Health object was successfully added, otherwise false
     */
    private boolean isAdd(Health other) {
        if (targets.add(other)){
            other.setTargeted(true);
            return true;
        }
        return false;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public void setMaster(Attackable master) {
        this.master = master;
    }
    public void setFlags(Flag flags) {
        this.flags = flags;
    }
}
