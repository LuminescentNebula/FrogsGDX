package com.mygdx.game.actions;

import com.mygdx.game.actions.types.Type;
import com.mygdx.game.interfaces.Attackable;

import java.util.ArrayList;

public class Fabric {

    private Flag flags=new Flag();
    private ArrayList<Type> types=  new ArrayList<>();

    public Fabric(){

    }

    public Fabric setTypes(ArrayList<Type> types) {
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
        attack.setFlags(flags);
        return attack;
    }

    public Fabric setFlags(byte flags){
        this.flags=new Flag(flags);
        return this;
    }
    public Fabric addFlag(byte flag){
        this.flags.add(flag);
        return this;
    }

}
