package com.mygdx.game;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AlignmentPack.Alignment;

public class AdvancedIntersector  {

    public AdvancedIntersector(){}

    //TODO javadoc
    private static AlignmentPack calculateAlignment(float startX, float startY,
                                           float rectangleX, float rectangleY,
                                           float rectangleEndX, float rectangleEndY) {
        AlignmentPack alignment = new AlignmentPack();

        if (startX > rectangleEndX) {                     //32
            alignment.alignmentSides = AlignmentPack.Alignment.RIGHT;
        } else if (startX < rectangleX) {                              //31
            alignment.alignmentSides = AlignmentPack.Alignment.LEFT;
        }
        if (startY > rectangleEndY) {                     //42
            alignment.alignmentLevel = AlignmentPack.Alignment.TOP;
        } else if (startY < rectangleY) {                              //41
            alignment.alignmentLevel = AlignmentPack.Alignment.BOTTOM;
        }
        return alignment;
    }

    public static boolean intersectSegmentRectangle (float startX, float startY, float endX, float endY, Rectangle rectangle, Vector2 intersection,AlignmentPack alignment) {
        float rectangleEndX = rectangle.x + rectangle.width;
        float rectangleEndY = rectangle.y + rectangle.height;

        alignment.set(calculateAlignment(startX, startY, rectangle.x,rectangle.y, rectangleEndX, rectangleEndY));

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

    public static boolean intersectSegmentRectangle (Vector2 start, Vector2 end, Rectangle rectangle, Vector2 intersection) {
        return intersectSegmentRectangle(start.x, start.y, end.x, end.y, rectangle,intersection);
    }
        public static boolean intersectSegmentRectangle (float startX, float startY, float endX, float endY, Rectangle rectangle,Vector2 intersection) {
        float rectangleEndX = rectangle.x + rectangle.width;
        float rectangleEndY = rectangle.y + rectangle.height;

        if (intersectSegments(startX, startY, endX, endY, rectangle.x, rectangle.y, rectangle.x, rectangleEndY, intersection))
            return true;
        if (intersectSegments(startX, startY, endX, endY, rectangle.x, rectangle.y, rectangleEndX, rectangle.y, intersection))
            return true;
        if (intersectSegments(startX, startY, endX, endY, rectangleEndX, rectangle.y, rectangleEndX, rectangleEndY, intersection))
            return true;
        if (intersectSegments(startX, startY, endX, endY, rectangle.x, rectangleEndY, rectangleEndX, rectangleEndY, intersection))
            return true;

        return false;
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

