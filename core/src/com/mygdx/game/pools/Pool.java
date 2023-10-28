package com.mygdx.game.pools;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.ArrayList;


/*TODO: Каждый пул имееют показатель действий, который ограничинивает их детей и считает общее количество
//TODO: Активный пул - дети производят действияэ
//TODO: Пассивный пул - дети действиуют когда прошло достаточно действия
    Character - активный
    Obstacle - пассивный
    Enemy - пассивный
    Main - главный
*/
public abstract class Pool<T> extends Group {
    protected ArrayList<T> actors = new ArrayList<>();

    public int getSize() {
        return actors.size();
    }

    public ArrayList<T> getActors() {
        return actors;
    }

    @Override
    public void addActor(Actor actor) {
        actors.add((T) actor);
        super.addActor(actor);
    }

    public abstract void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool);

    public <E> ArrayList<E> get(Class<E> clazz) {
        return new ArrayList<E>() {{
            for (T actor : actors) {
                if (clazz.isInstance(actor)) {
                    add((E) actor);
                }
            }
        }};
    }
}

