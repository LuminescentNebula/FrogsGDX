package com.mygdx.game.actions;

import com.mygdx.game.actions.types.BaseType;
import com.mygdx.game.interfaces.Attackable;

public class Fabric {

    private BaseType attackType;
    private Flag flags;
    private float minLength,maxLength,radius;
    private int damage;

    public Fabric(byte flags, BaseType attackType, float minLength, float maxLength, float radius, int damage){
        this.flags=new Flag(flags);
        this.attackType= attackType;
        this.minLength=minLength;
        this.maxLength=maxLength;
        this.radius=radius;
        this.damage=damage;
    }
    public Attack build(Attackable master){
        Attack attack = new Attack(master,attackType);
        attack.setFlags(flags);
        attack.setMinLength(minLength);
        attack.setMaxLength(maxLength);
        attack.setRadius(radius);
        attack.setDamage(damage);
        return attack;
    }

    public void setFlags(byte flags){
        this.flags=new Flag(flags);
    }
    public void addFlag(byte flag){
        this.flags.add(flag);
    }
    public void setAttackType(BaseType attackType){
        this.attackType= attackType;
    }
    public void setLength(float minLength,float maxLength){

    }
    public void setRadius(float radius){

    }
    public void setDamage(int damage){

    }

}
