package com.mygdx.game.actions.mods;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainPool;
import com.mygdx.game.interfaces.Attackable;

public interface Mod {
    void apply(Attackable master, Vector2 cursor, MainPool mainPool);
}