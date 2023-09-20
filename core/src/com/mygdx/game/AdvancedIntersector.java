package com.mygdx.game;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdvancedIntersector  {

    public AdvancedIntersector(){}

    /** {@link #intersectSegmentRectangle(float, float, float, float, Rectangle,Vector2,AlignmentPack)} */
    public static boolean intersectSegmentRectangle (Vector2 start, Vector2 end, Rectangle rectangle, Vector2 intersection, AlignmentPack alignment) {
        return intersectSegmentRectangle(start.x, start.y, end.x, end.y, rectangle,intersection,alignment);
    }

    /** Determines whether the given rectangle and segment intersect
     * @param startX x-coordinate start of line segment
     * @param startY y-coordinate start of line segment
     * @param endX y-coordinate end of line segment
     * @param endY y-coordinate end of line segment
     * @param rectangle rectangle that is being tested for collision
     * @return whether the rectangle intersects with the line segment */
    public static boolean intersectSegmentRectangle (float startX, float startY, float endX, float endY, Rectangle rectangle, Vector2 intersection,AlignmentPack alignment) {
        float rectangleEndX = rectangle.x + rectangle.width;
        float rectangleEndY = rectangle.y + rectangle.height;
        ArrayList<Integer> arr= new ArrayList<>();

        //System.out.println(startX+" "+startY+" "+endX+" "+endY);
       //System.out.println(rectangle);
        //Попытаться объеденить aligment и проверкой сторон
        if (startY>rectangle.y && startY<rectangleEndY) {
            if (startX>rectangleEndX){
                arr.add(3);
                alignment.alignmentSides= AlignmentPack.Alignment.RIGHT;
            } else {
                arr.add(1);
                alignment.alignmentSides= AlignmentPack.Alignment.LEFT;
            }
        } else if (startX>rectangle.x && startX<rectangleEndX) {
            if (startY>rectangleEndY){
                arr.add(4);
                alignment.alignmentLevel= AlignmentPack.Alignment.TOP;
            } else {
                arr.add(2);
                alignment.alignmentLevel= AlignmentPack.Alignment.BOTTOM;
            }
        } else {
            if (startX < rectangle.x) {
                arr.add(1);
                alignment.alignmentSides= AlignmentPack.Alignment.LEFT;
            } else if (startX > rectangleEndX) {
                arr.add(3);
                alignment.alignmentSides= AlignmentPack.Alignment.RIGHT;
            }
            if (startY < rectangle.y) {
                arr.add(2);
                alignment.alignmentLevel= AlignmentPack.Alignment.BOTTOM;
            } else if (startY > rectangleEndY) {
                arr.add(4);
                alignment.alignmentLevel= AlignmentPack.Alignment.TOP;
            }
        }

        System.out.println(arr);

        for (int i:arr) {
            switch (i) {
                case 1:
                    if (intersectSegments(startX, startY, endX, endY, rectangle.x, rectangle.y, rectangle.x, rectangleEndY, intersection)) {
                        System.out.println("First " + intersection);
                        return true;
                    }
                    break;
                case 2:
                    if (intersectSegments(startX, startY, endX, endY, rectangle.x, rectangle.y, rectangleEndX, rectangle.y, intersection)) {
                        System.out.println("Second " + intersection);
                        return true;
                    }
                    break;
                case 3:
                    if (intersectSegments(startX, startY, endX, endY, rectangleEndX, rectangle.y, rectangleEndX, rectangleEndY, intersection)) {
                        System.out.println("Third " + intersection);
                        return true;
                    }
                    break;
                case 4:
                    if (intersectSegments(startX, startY, endX, endY, rectangle.x, rectangleEndY, rectangleEndX, rectangleEndY, intersection)) {
                        System.out.println("Fourth " + intersection);
                        return true;
                    }
                    break;
            }
        }

        return rectangle.contains(startX, startY);
    }


    public static boolean intersectSegments (float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4,
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
