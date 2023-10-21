package com.mygdx.game.actions.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.actions.Fabric;
import com.mygdx.game.actions.Flag;
import com.mygdx.game.actions.Length;
import com.mygdx.game.actions.Radius;
import com.mygdx.game.interfaces.Health;

public abstract class Type implements ActInterface,DrawInterface {

    private Flag flags=new Flag();
    private float minLength= Length.NONE,
            maxLength= Length.LARGE,
            radius= Radius.NONE;
    private int damage=0;

    public Type(){
    }

    public static boolean chainCheck(Health other, ShapeRenderer shapeRenderer, Circle circle){
        if (Intersector.overlaps(circle, other.getBounds())) {
            shapeRenderer.circle(other.getCenterX(), other.getCenterY(), 50);
            return true;
        }
        return false;
    }


    public Flag getFlags() {
        return flags;
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
