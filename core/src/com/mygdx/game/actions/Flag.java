package com.mygdx.game.actions;

public class Flag {
    private byte flags=0;
    public static final byte checkNotMaster = 1;  // 0001 Master cant be a target
    public static final byte stopOnFirstCollision   = 2;  // 0010 Only one target can be targeted
    public static final byte chainDamage = 4; // 0100 Every target spread targeting by radius
    public static final byte chainChecking = 8; // 1000 while chain checking

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

    public void del(byte flag){
        this.flags= (byte) (flags&(~flag & 0xff));
    }
}
