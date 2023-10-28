package com.mygdx.game.actions.types;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.pools.MainPool;
import com.mygdx.game.actions.types.mods.Mod;
import com.mygdx.game.actions.types.mods.Reset;
import com.mygdx.game.interfaces.*;

import java.util.*;

public class Type implements ActInterface, DrawInterface {
    private ActInterface actInterface;
    private DrawInterface drawInterface;
    //TODO: flags and other modificators
    LinkedList<Mod> mods = new LinkedList<>();
    Reset reset=new Reset(true);
    private float minLength= Length.NONE,
            maxLength= Length.LARGE,
            radius= Radius.NONE;
    private int damage=0;
    private Set<Health> targets = new LinkedHashSet<>();
    //TODO:Not public
    public Flag flags=new Flag();


    public Type(DrawInterface drawInterface, ActInterface actInterface) {
        this.drawInterface = drawInterface;
        this.actInterface = actInterface;
    }

    public Type() {

    }

    public void deal(){
        for (Health target: targets){
            target.dealHealth(damage);
        }
    }

    public Type setDraw(DrawInterface drawInterface){
        this.drawInterface = drawInterface;
        return this;
    }

    public Type setAct(ActInterface actInterface){
        this.actInterface = actInterface;
        return this;
    }

    @Override
    public boolean check(Health other, Vector2 master, Vector2 cursor, Circle circle) {
        return actInterface.check(other, master, cursor, circle);
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        drawInterface.draw(shapeRenderer, master, cursor, minLength, maxLength, radius);
    }

    public boolean chainCheck(Health other, ShapeRenderer shapeRenderer, Circle circle){
        if (Intersector.overlaps(circle, other.getBounds())) {
            shapeRenderer.circle(other.getCenterX(), other.getCenterY(), 50);
            return true;
        }
        return false;
    }
    public Type setFlags(byte flags){
        this.flags=new Flag(flags);
        return this;
    }
    public Type addFlag(byte flag){
        this.flags.add(flag);
        return this;
    }
    /**
     * Trying to add given Health object should and return success of operation.
     *
     * @param  other  the Health object to be added
     * @return        true if the Health object was successfully added, otherwise false
     */
    public boolean isAdd(Health other) {
        if (targets.add(other)){
            other.setTargeted(true);
            return true;
        }
        return false;
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

    public Type addMod(Mod mod){
        mods.add(mod);
        return this;
    }
    public Type addMod(Reset mod){
        reset=mod;
        return this;
    }

    public void performPre(Attackable master, Vector2 cursor, MainPool mainPool){
        reset.save(cursor);
        for (Mod mod : mods) {
            mod.apply(master,cursor,mainPool);
        }

    }
    public void performAfter(Attackable master, Vector2 cursor, MainPool mainPool){
        reset.revert(cursor);
    }

    public float getMinLength() {
        return minLength;
    }

    public float getMaxLength() {
        return maxLength;
    }

    public float getRadius() {
        return radius;
    }

    public int getDamage() {
        return damage;
    }

    public Type setLength(float minLength, float maxLength){
        this.minLength=minLength;
        this.maxLength=maxLength;
        return this;
    }
    public Type setRadius(float radius){
        this.radius=radius;
        return this;
    }
    public Type setDamage(int damage){
        this.damage=damage;
        return this;
    }

    public Type setMinLength(float minLength) {
        this.minLength = minLength;
        return this;
    }

    public Type setMaxLength(float maxLength) {
        this.maxLength = maxLength;
        return this;
    }



}
