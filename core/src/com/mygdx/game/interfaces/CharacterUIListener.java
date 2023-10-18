package com.mygdx.game.interfaces;

import com.mygdx.game.actions.Attack;

import java.util.ArrayList;

public interface CharacterUIListener {
    void showControls(ArrayList<Attack> attacks);
    void hideControls();
    void setSubscriber(UICharacterListener uiCharacterListener);
}
