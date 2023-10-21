package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.VisUI;
import com.mygdx.game.actions.Attack;
import com.mygdx.game.interfaces.CharacterUIListener;
import com.mygdx.game.interfaces.UICharacterListener;
import com.mygdx.game.interfaces.UIListener;

import java.util.ArrayList;

public class UserInterface extends Group implements UIListener, CharacterUIListener {

    Table buttonTable;
    UICharacterListener uiCharacterListener;

    UserInterface() {}

    public void build(){
        VisUI.load();
        Skin skin = VisUI.getSkin();

        buttonTable = new Table();

        addActor(buttonTable);

        //buttonTable.align(Align.top);
        setTouchable(Touchable.childrenOnly);
        setHeight(75);
        setY(Gdx.graphics.getHeight() - 75);
        buttonTable.setFillParent(true);

        int N = 3;
        buttonTable.top().left();
        Button[] buttons = new Button[N];
        for (int i = 0; i < N; i++) {
            buttons[i] = new TextButton("Button " + (i + 1), skin);
            final int buttonIndex = i;
            buttons[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Button " + (buttonIndex + 1) + " pressed");
                }
            });
            buttons[i].setColor(Color.YELLOW);
            buttonTable.add(buttons[i]).expandX();
        }
        //buttonTable.row();
        //buttonTable.add(new Character(0)).colspan(buttonTable.getColumns()).expandY();

        buttonTable.setDebug(true);
    }

    @Override
    public void setSubscriber(UICharacterListener uiCharacterListener) {
        this.uiCharacterListener = uiCharacterListener;
    }

    @Override
    public void showControls(ArrayList<Attack> attacks) {
        buttonTable.clear();
        for (int i=0;i< attacks.size();i++) {
            Button button = new TextButton(attacks.get(i).toString(), VisUI.getSkin());
            int id=i;
            buttonTable.add(button).expand();
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println(id);
                    uiCharacterListener.attackSelected(id);
                }
            });
        }
    }

    @Override
    public void hideControls() {
        buttonTable.clear();
    }
}