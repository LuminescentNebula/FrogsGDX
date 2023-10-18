package com.mygdx.game.interfaces;

import com.mygdx.game.actions.Attack;

import java.util.ArrayList;

public interface CharacterSelectionListener {
    void setSelected(boolean selected);
    boolean isSelected();
    void sendAttacks(ArrayList<Attack> attacks);
}
