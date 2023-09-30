package com.mygdx.game.actions;

public class Flag {
    private byte flags=0;
    public static final byte checkNotMaster = 1;  // 0001
    public static final byte stopOnFirstCollision   = 2;  // 0010
    public static final byte chainDamage = 4; // 0100

    public Flag(byte flags){
        this.flags=flags;
    }

    public byte get(){return this.flags;}

    public boolean is(byte flags){
        return (flags&this.flags)!=0;
    }

    public void add(byte flag){
        this.flags= (byte) (this.flags|flag);
    }
}
