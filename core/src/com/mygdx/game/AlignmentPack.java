package com.mygdx.game;

public class AlignmentPack {
    public enum Alignment {
        LEFT(-1),
        RIGHT(1),
        TOP(1),
        BOTTOM(-1),
        EMPTY(0);


        int f;
        Alignment(int f) {
            this.f=f;
        }
        public int get() {
            return f;
        }
    }
    public Alignment alignmentSides;
    public Alignment alignmentLevel;

    public AlignmentPack(Alignment alignmentSides, Alignment alignmentLevel) {
        this.alignmentSides = alignmentSides;
        this.alignmentLevel = alignmentLevel;
    }
    public AlignmentPack() {
        this.alignmentSides = Alignment.EMPTY;
        this.alignmentLevel = Alignment.EMPTY;
    }

    public Alignment[] list(){
        return new Alignment[]{alignmentLevel,alignmentSides};
    }
}
