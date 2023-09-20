package com.mygdx.game;

public class AlignmentPack {
    public enum Alignment {
        LEFT(-1),
        RIGHT(1),
        TOP(1),
        BOTTOM(-1),
        EMPTY(0);


        double f;
        Alignment(double f) {
            this.f=f;
        }
        public double get() {
            return f;
        }
    }
    public Alignment alignmentSides;
    public Alignment alignmentLevel;

    AlignmentPack(Alignment alignmentSides, Alignment alignmentLevel) {
        this.alignmentSides = alignmentSides;
        this.alignmentLevel = alignmentLevel;
    }
    AlignmentPack() {
        this.alignmentSides = Alignment.EMPTY;
        this.alignmentLevel = Alignment.EMPTY;
    }



}
