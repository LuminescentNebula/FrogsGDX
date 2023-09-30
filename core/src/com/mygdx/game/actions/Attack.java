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
import com.mygdx.game.actions.types.BaseType;
import com.mygdx.game.interfaces.Attackable;
import com.mygdx.game.interfaces.Health;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

public class Attack implements TargetsSelectionListener {
    private boolean isSelected=false;
    private Attackable master;
    private BaseType type;
    private Flag flags;
    private float radius; //chainradius=radius
    private float minLength;
    private float maxLength;
    private int damage=10;
    private Set<Health> targets = new HashSet<>();

    private static final Color areaColor=new Color(1,1,0,0.3f);

    public Attack(Attackable master, BaseType type){
        this.master = master;
        this.type = type;
        this.type.setTargetsSelectionListener(this);
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
        shapeRenderer.setColor(new Color(areaColor));

        type.draw(shapeRenderer,master,cursor, minLength, maxLength,radius);
    }

    public void act(MainPool mainPool, ShapeRenderer shapeRenderer, Attackable master, Vector2 cursor) {
        Stream<Health> stream = mainPool.getHealths().stream();
        if (flags.is(Flag.checkNotMaster)) {
            stream = stream.filter(other -> master.getId() != other.getId());
        }
        stream.filter(other -> type.check(other, shapeRenderer, master.getCenter(), cursor,
                        new Circle(cursor, radius)) && flags.is(Flag.chainDamage))
                .forEach(other -> act(mainPool, shapeRenderer, master, other.getCenter()));
    }

    @Override
    public void addTarget(Health target) {
        if (targets.contains(target)) {
            this.targets.add(target);
        }
    }

    @Override
    public boolean isChainDamage() {
        return flags.is(Flag.chainDamage);
    }

    @Override
    public boolean isStopOnFirstCollision() {
        return flags.is(Flag.stopOnFirstCollision);
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
