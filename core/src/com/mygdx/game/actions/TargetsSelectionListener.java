package com.mygdx.game.actions;

import com.mygdx.game.interfaces.Health;

public interface TargetsSelectionListener {
    void addTarget(Health target);
    boolean isChainDamage();
    boolean isStopOnFirstCollision();
}
