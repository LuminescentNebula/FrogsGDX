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

//TODO:Настройки урона

public class Attack {
    private boolean isSelected=false;
    private Attackable master;

    private ArrayList<Type> types;
    private Flag flags;

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
            type.performPre(master.getCenter(),cursor,mainPool);
            draw(type::draw,shapeRenderer,master.getCenter(),cursor, type.getMinLength(),type.getMaxLength(),type.getRadius());
            act(type,mainPool,shapeRenderer,master,cursor,type.getRadius());
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
    }

    public void flushTargets() {
        types.forEach(Type::flushTargets);
    }

    public void act(Type type, MainPool mainPool, ShapeRenderer shapeRenderer, Attackable master, Vector2 cursor, float radius) {

        Stream<Health> stream = mainPool.getHealths().stream();
        stream =stream.sorted(Comparator.comparing(health -> {
            return health.getCenter().dst(master.getCenter());
        }));
        if (flags.is(Flag.checkNotMaster)) {
            stream = (stream.filter(other -> master.getId() != other.getId()));
        }

        if (chainChecking) {  //Для вызовов, которые проверяют цепочки
            stream = stream.filter(other -> Type.chainCheck(other, shapeRenderer,
                    new Circle(cursor, radius)));
        } else { //Проверяем коллизию для всех остальных
            stream = stream.filter(other -> type.check(other, master.getCenter(), cursor,
                    new Circle(cursor, radius)));
        }

        if (flags.is(Flag.stopOnFirstCollision)) { //Если есть stop onFirstCollision, то добавляется только первая найденная цель
            flags.del(Flag.stopOnFirstCollision);
            chainChecking=true;
            stream.findFirst().ifPresent(other -> { //Если есть chainDamage, то вызывается act, но с удаленным stopOnFirst и отметкой chainChecking, что идет проверка цепи
                if (type.isAdd(other) && flags.is(Flag.chainDamage)) act(type, mainPool, shapeRenderer, master, other.getCenter(), radius);
            }); //Цепочка тоже начинается только от первой цели
            chainChecking=false;
            flags.add(Flag.stopOnFirstCollision);
        } else {
            stream.forEach(other -> { //Цепочка начинается от каждой цели
                if (type.isAdd(other) && flags.is(Flag.chainDamage)) {
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
    public void setFlags(Flag flags) {
        this.flags = flags;
    }
}
