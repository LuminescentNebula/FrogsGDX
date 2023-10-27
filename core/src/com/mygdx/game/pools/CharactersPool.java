package com.mygdx.game.pools;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actions.Attack;
import com.mygdx.game.actors.Character;
import com.mygdx.game.interfaces.CharacterSelectionListener;
import com.mygdx.game.interfaces.CharacterUIListener;
import com.mygdx.game.interfaces.UICharacterListener;

import java.util.ArrayList;

public class CharactersPool extends Pool<Character> implements CharacterSelectionListener,UICharacterListener {
    private final int ID_GROUP = 100;
    private boolean selected;
    private CharacterUIListener characterUIListener;
    //private int selected_id;

    public CharactersPool(){
        Character character1= new Character(actors.size()+ID_GROUP);
        character1.setPosition(200, 200);
        character1.setSelectionListener(this);
        addActor(character1);

        Character character2= new Character(actors.size()+ID_GROUP);
        character2.setPosition(400, 200);
        character2.setSelectionListener(this);
        addActor(character2);

        Character character3= new Character(actors.size()+ID_GROUP);
        character3.setPosition(500, 200);
        character3.setSelectionListener(this);
        addActor(character3);
    }

    public void setCharacterUIListener(CharacterUIListener characterUIListener){
        this.characterUIListener=characterUIListener;
        characterUIListener.setSubscriber(this);
    }


//    public void project(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool) {
//        for (Character character: actors) {
//            character.move(stage,shapeRenderer, mainPool);
//        }
//    }
    @Override
    public void act(Stage stage, ShapeRenderer shapeRenderer,MainPool mainPool) {
        for (Character character: actors) {
            character.act(stage,shapeRenderer,mainPool);
            //Todo: после окончания selection не снимается
        }
    }

    @Override
    public void setSelected(boolean selected) {
        if (!selected){
            characterUIListener.hideControls();
        }
        this.selected=selected;

    }
    @Override
    public boolean isSelected() {
        return selected;
    }
    @Override
    public void sendAttacks(ArrayList<Attack> attacks) {
        //TODO:send to UI
        characterUIListener.showControls(attacks);
    }
    @Override
    public void attackSelected(int id) {
        for (Character character : actors) {
            System.out.println("searching");
            System.out.println(character.isSelected());
            if (character.isSelected()) {
                System.out.println("Setting attack" + character.getId());
                character.setAttacking(true, id);
                break;
            }
        }
    }


}
