package com.mygdx.game.actions.types;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainPool;
import com.mygdx.game.actions.ActDrawInterface;
import com.mygdx.game.actions.TargetsSelectionListener;
import com.mygdx.game.interfaces.Health;

public abstract class BaseType implements ActDrawInterface {
    protected TargetsSelectionListener targetsSelectionListener;

    BaseType(){
    }

    BaseType(TargetsSelectionListener targetsSelectionListener) {
        this.targetsSelectionListener = targetsSelectionListener;
    }

    public void setTargetsSelectionListener(TargetsSelectionListener targetsSelectionListener) {
        this.targetsSelectionListener = targetsSelectionListener;
    }

    public boolean chainCheck(Health other, ShapeRenderer shapeRenderer, Circle circle){
        if (Intersector.overlaps(circle, other.getBounds())) {
            shapeRenderer.circle(other.getCenterX(), other.getCenterY(), 50);
            return true;
        }
        return false;
    }
}
