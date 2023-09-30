package com.mygdx.game.actions.types;

import com.mygdx.game.actions.ActDrawInterface;
import com.mygdx.game.actions.TargetsSelectionListener;

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
}
