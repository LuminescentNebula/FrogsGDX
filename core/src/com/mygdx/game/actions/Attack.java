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
import com.mygdx.game.actions.types.ActDrawInterface;
import com.mygdx.game.interfaces.Attackable;
import com.mygdx.game.interfaces.Health;

import java.util.HashSet;
import java.util.Set;
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
    private ActDrawInterface type;
    private Flag flags;
    private float radius; //chainradius=radius
    private float minLength;
    private float maxLength;
    private int damage = 10;
    private Set<Health> targets = new HashSet<>();

    private static final Color areaColor=new Color(1,1,0,0.3f);

    public ActDrawInterface getType() {
        return type;
    }

    public Attack(Attackable master, ActDrawInterface type){
        this.master = master;
        this.type = type;
    }

    private void deal(){
        for (Health target: targets) {
            target.dealHealth(damage);
        }
    }

    public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor){
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        type.draw(shapeRenderer,master,cursor, minLength, maxLength,radius);
        flushTargets();
    }

    private void flushTargets() {
        for (Health health:targets) {
            health.setTargeted(false);
        }
        targets = new HashSet<>();
    }

    public void act(MainPool mainPool, ShapeRenderer shapeRenderer, Attackable master, Vector2 cursor) {

        shapeRenderer.setColor(new Color(areaColor));

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(cursor.x, cursor.y, radius);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

        Stream<Health> stream = mainPool.getHealths().stream();
        if (flags.is(Flag.checkNotMaster)) {
            stream = (stream.filter(other -> master.getId() != other.getId()));
        }
        if (flags.is(Flag.chainChecking)) {
            stream = stream.filter(other -> type.chainCheck(other, shapeRenderer,
                    new Circle(cursor, radius)));
        } else {
            stream = stream.filter(other -> type.check(other, shapeRenderer, master.getCenter(), cursor,
                    new Circle(cursor, radius)));
        }
        if (flags.is(Flag.stopOnFirstCollision)) {
            flags.del(Flag.stopOnFirstCollision);
            flags.add(Flag.chainChecking);
            stream.findFirst().ifPresent(other -> {
                if (isAdd(other) && flags.is(Flag.chainDamage)) act(mainPool, shapeRenderer, master, other.getCenter());
            });
            flags.del(Flag.chainChecking);
            flags.add(Flag.stopOnFirstCollision);
        } else {
            stream.forEach(other -> {
                if (isAdd(other) && flags.is(Flag.chainDamage)) {
                    act(mainPool, shapeRenderer, master, other.getCenter());
                };
            });
        }
    }

    private boolean isAdd(Health other) {
        return other.setTargeted(targets.add(other));
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

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setMinLength(float minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(float maxLength) {
        this.maxLength = maxLength;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setTargets(Set<Health> targets) {
        this.targets = targets;
    }
}
