package com.mygdx.game.actions;

import com.mygdx.game.actions.types.Type;
import com.mygdx.game.interfaces.Attackable;

import java.util.ArrayList;

public class TypeFabric {

    private ArrayList<Type> types=  new ArrayList<>();

    public TypeFabric(){

    }

    public TypeFabric setTypes(ArrayList<Type> types) {
        this.types=types;
        return this;
    }

    public Type addType(Type type){
        types.add(type);
        return type;
    }

    public Attack  build(Attackable master){
        Attack attack = new Attack();
        attack.setMaster(master);
        attack.setTypes(types);
        return attack;
    }


}
