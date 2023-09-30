package com.mygdx.game;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AlignmentPack.Alignment;

public class AdvancedIntersector  {

    public AdvancedIntersector(){}

    //TODO javadoc
    private static void calculateAlignment(float startX, float startY, float endX, float endY, Rectangle rectangle, AlignmentPack alignment) {
        float rectangleEndX = rectangle.x + rectangle.width;
        float rectangleEndY = rectangle.y + rectangle.height;

        if (startY > rectangle.y && startY < rectangleEndY) {
            if (startX > rectangleEndX) {
                alignment.alignmentSides = AlignmentPack.Alignment.RIGHT;
            } else {
                alignment.alignmentSides = AlignmentPack.Alignment.LEFT;
            }
        } else if (startX > rectangle.x && startX < rectangleEndX) {
            if (startY > rectangleEndY) {
                alignment.alignmentLevel = AlignmentPack.Alignment.TOP;
            } else {
                alignment.alignmentLevel = AlignmentPack.Alignment.BOTTOM;
            }
        } else {
            if (startX < rectangle.x) {
                alignment.alignmentSides = AlignmentPack.Alignment.LEFT;
            } else if (startX > rectangleEndX) {
                alignment.alignmentSides = AlignmentPack.Alignment.RIGHT;
            }
            if (startY < rectangle.y) {
                alignment.alignmentLevel = AlignmentPack.Alignment.BOTTOM;
            } else if (startY > rectangleEndY) {
                alignment.alignmentLevel = AlignmentPack.Alignment.TOP;
            }
        }
    }

    public static boolean intersectSegmentRectangle (float startX, float startY, float endX, float endY, Rectangle rectangle, Vector2 intersection,AlignmentPack alignment) {
        float rectangleEndX = rectangle.x + rectangle.width;
        float rectangleEndY = rectangle.y + rectangle.height;
        calculateAlignment(startX, startY, endX, endY, rectangle, alignment);

        System.out.println(alignment.list()[0].get()+" "+alignment.list()[1].get());
        for (Alignment i: alignment.list()) {
            switch (i) {
                case LEFT:
                    if (intersectSegments(startX, startY, endX, endY, rectangle.x, rectangle.y, rectangle.x, rectangleEndY, intersection)) {
                        //System.out.println("Left " + intersection);
                        return true;
                    }
                    break;
                case BOTTOM:
                    if (intersectSegments(startX, startY, endX, endY, rectangle.x, rectangle.y, rectangleEndX, rectangle.y, intersection)) {
                        //System.out.println("Bottom " + intersection);
                        return true;
                    }
                    break;
                case RIGHT:
                    if (intersectSegments(startX, startY, endX, endY, rectangleEndX, rectangle.y, rectangleEndX, rectangleEndY, intersection)) {
                        //System.out.println("Right " + intersection);
                        return true;
                    }
                    break;
                case TOP:
                    if (intersectSegments(startX, startY, endX, endY, rectangle.x, rectangleEndY, rectangleEndX, rectangleEndY, intersection)) {
                        //System.out.println("Top " + intersection);
                        return true;
                    }
                    break;
            }
        }

        return rectangle.contains(startX, startY);
    }

    /** @link {#intersectSegmentRectangle(float, float, float, float,Rectangle,Vector2,AlignmentPack)} */
    public static boolean intersectSegmentRectangle (Vector2 start, Vector2 end, Rectangle rectangle, Vector2 intersection, AlignmentPack alignment) {
        return intersectSegmentRectangle(start.x, start.y, end.x, end.y, rectangle,intersection,alignment);
    }


    private static boolean intersectSegments (float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4,
                                             Vector2 intersection) {
        float d = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        if (d == 0) return false;

        float yd = y1 - y3;
        float xd = x1 - x3;
        float ua = ((x4 - x3) * yd - (y4 - y3) * xd) / d;
        if (ua < 0 || ua > 1) return false;

        float ub = ((x2 - x1) * yd - (y2 - y1) * xd) / d;
        if (ub < 0 || ub > 1) return false;

        if (intersection != null) intersection.set(x1 + (x2 - x1) * ua, y1 + (y2 - y1) * ua);
        return true;
    }

}

