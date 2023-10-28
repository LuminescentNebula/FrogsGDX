package com.mygdx.game.interfaces;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public interface Projectable extends Movable,Collidable {
    //Image getImage();
    //Image getSelection();
    Image getProjection();
}
