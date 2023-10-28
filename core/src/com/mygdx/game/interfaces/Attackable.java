package com.mygdx.game.interfaces;

import com.mygdx.game.actions.Attack;

import java.util.ArrayList;

public interface Attackable extends Actionable,Placeable{
    ArrayList<Attack> getAttacks();

}
