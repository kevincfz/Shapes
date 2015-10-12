package shapes;

import java.util.List;

/**
 * Created by Kevin Fangzhou Chen.
 * A super class that represents all the shapes.
 */
abstract class Shape {

    /** The name of this shape. */
    public String name;

    /** Return which sub-plane(s) this shape belongs to,
     * 1, 2, 3, 4, 0, representing topRight, topLeft, bottomLeft,
     * bottomRight sub-plane or the current PLANE. Can be multiple.*/
    abstract List<Integer> getSubplane(Shape plane);

    /** Return the area of this shape.*/
    abstract double area();

    /** Return True if this shape will collide with
     * SHAPE. */
    abstract boolean ifCollide(Shape shape);

    /** Return True if this shape is out of bounds of the coordinate
     * system.*/
    abstract boolean outOfBounds(QuadTreePlane plane);

    /** Change the center of the Shape, according to X, Y.*/
    abstract void changeCenter(int x, int y);

    /** Get Center X.*/
    public abstract int getCenterX();

    /** Get Center Y.*/
    public abstract int getCenterY();

    /** Copy myself.*/
    public abstract Shape copy();

    /** Change the w and h. */
    public abstract void changeSize(int w, int h);

    /** Change the radius. */
    public abstract void changeSize(int rad);

    /** Get the width, if circle, radius */
    public abstract double getWidth();
}
