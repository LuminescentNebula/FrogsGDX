package com.mygdx.game.actions.types;


public class Flag {
    private byte flags=0;
    public static final byte checkNotMaster = 1;  // 0001 Master cant be a target
    public static final byte stopOnFirstCollision   = 2;  // 0010 Only one target can be targeted
    public static final byte chainDamage = 4; // 0100 Every target spread targeting around by radius
    public static final byte chainDamageIncrease = 8; // 1000 Increasing damage with every next target

    public Flag(){}
    public Flag(byte flags){
        this.flags=flags;
    }

    public byte get(){return this.flags;}

    public void set(boolean[] arr){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]) {
                this.flags = (byte) (this.flags | (1 << i));
            }
        }
    }

    public boolean is(byte flags){
        return (flags&this.flags)!=0;
    }

    public void add(byte flag){
        this.flags= (byte) (this.flags|flag);
    }

    public void del(byte flag){
        this.flags= (byte) (flags&(~flag & 0xff));
    }
}
