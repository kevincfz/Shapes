package shapes;

import java.util.*;

/**
 * Created by Kevin Fangzhou Chen.
 * A quadTree implementation of the QuadTreePlane
 */
public class QuadTreePlane {


    /** The maximum objects that can be held in the current
     * Node. Used for splitting*/
    int maxObjects;

    /** The plane boundary as a rectangle.*/
    Rectangle plane;

    /** The framework boundary.*/
    static Rectangle bounds;

    /** The four sub-planes of the current plane. */
    QuadTreePlane TopLeft, TopRight, BotLeft, BotRight;

    /** The shapes of the current Plan. */
    HashMap<String, Shape> shapes;

    /** A list that keeps track of the duplicated shapes. */
    private List<Shape> _duplicated;

    /** If the plane is split. **/
    public boolean isSplit() {
        return TopLeft != null;
    }

    /** Construct a new QuadTreePlane, taking in a
     * Rectangle PLANE, and MAX as how many objects
     * each level of Plane can hold.
     * */
    private QuadTreePlane(Rectangle plane, int max) {
        this.maxObjects = max;
        this.plane = plane;
        this.shapes = new LinkedHashMap<>();
        this._duplicated = new ArrayList<>();
    }

    /** Construct a new QuadTreePlane, taking in a
     * Rectangle PLANE, and MAX as how many objects
     * each level of Plane can hold. If INITIALBOUNDS
     * or not, record the plane as bounds.
     * Use this function to initialize a
     * coordinate framework.
     * */
    public QuadTreePlane(Rectangle plane, int max, boolean initialBounds) {
        this.maxObjects = max;
        this.plane = plane;
        this.shapes = new HashMap<>();
        this._duplicated = new ArrayList<>();
        QuadTreePlane.bounds = plane;
    }

    /** Insert SHAPE into the current plane.*/
    public void insert(Shape shape) {
        if (!isSplit()) {
            if (shapes.size() >= maxObjects) {
                splitPlanes(maxObjects);
            } else {
                verifyBounds(shape);
                verifyInsertion(shape);
                shapes.put(shape.name, shape);
                return;
            }
        }
        List<Integer> indexList = shape.getSubplane(plane);
        for (Integer i : indexList) {
            switch (i) {
                case 1: TopRight.insert(shape);
                        break;
                case 2: TopLeft.insert(shape);
                        break;
                case 3: BotLeft.insert(shape);
                        break;
                case 4: BotRight.insert(shape);
                        break;
                default: break;
            }
        }
        if (indexList.size() > 1) {
            for (int i = 1; i < indexList.size(); i += 1)
            _duplicated.add(shape);
        }
    }

    /** Remove a shape. */
    public void remove(String name) {
        if (shapes.containsKey(name)) {
            shapes.remove(name);
            for (Shape shape : _duplicated) {
                if (shape.name.equals(name)) {
                    _duplicated.remove(shape);
                }
            }
        } else if (isSplit()) {
            BotLeft.remove(name);
            BotRight.remove(name);
            TopRight.remove(name);
            TopLeft.remove(name);
        }
    }

    /** Move the shape OLDSHAPE, to a new position with center X and Y.*/
    public Shape move(Shape oldShape, int x, int y) {
        Shape clone = oldShape.copy();
        String name = oldShape.name;
        clone.name = "temp";
        remove(name);
        clone.name = name;
        clone.changeCenter(x, y);
        insert(clone);
        return clone;
    }

    /** Change the size of rectangle OLDSHAPE, to a new WIDTH and HEIGHT.*/
    public Shape changeSize(Shape oldShape, int width, int height) {
        Shape clone = oldShape.copy();
        String name = oldShape.name;
        clone.name = "temp";
        remove(name);
        clone.name = name;
        clone.changeSize(width, height);
        insert(clone);
        return clone;
    }

    /** Change the size of circle OLDSHAPE, to a new WIDTH and HEIGHT.*/
    public Shape changeSize(Shape oldShape, int radius) {
        Shape clone = oldShape.copy();
        String name = oldShape.name;
        clone.name = "temp";
        remove(name);
        clone.name = name;
        clone.changeSize(radius);
        insert(clone);
        return clone;
    }

    /** Change the size of circle named NAME, to a new radius R.*/

    /** Check if the insertion candidate can fit into the current plane.*/
    private void verifyInsertion(Shape candidate) {
        for (Map.Entry<String, Shape> shape : shapes.entrySet()) {
            if (candidate.ifCollide(shape.getValue())){
                throw new UnsupportedOperationException(
                        "Cannot add candidate shape: Collision.");
            }
        }
    }

    /** Return the initial bounds. */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Verify if CANDIDATE is Out of Bounds.
     */
    private void verifyBounds(Shape candidate) {
        if (candidate.outOfBounds(this)) {
            throw new UnsupportedOperationException(
                    "Cannot add candidate shape: Out of Bounds.");
        }
    }


    /** Split the plane into four sub-planes, sub-plane
     * cant hold MAX items, and automatically put all the
     * existing shapes into the sub-planes.*/
    private void splitPlanes(int max) {
        double newWidth = plane.getWidth() / 2;
        double newHeight = plane.getHeight() / 2;

        TopLeft = new QuadTreePlane(
                  new Rectangle((int)(- newWidth / 2),
                                (int)(newHeight / 2),
                                newWidth, newHeight, "tl"), max);

        TopRight = new QuadTreePlane(
                   new Rectangle((int)(newWidth / 2),
                                 (int)(newHeight / 2), newWidth,
                                 newHeight, "tr"), max);

        BotLeft = new QuadTreePlane(
                  new Rectangle((int)(- newWidth / 2),
                                (int)(- newHeight / 2),
                                newWidth, newHeight, "bl"), max);

        BotRight = new QuadTreePlane(
                   new Rectangle((int)(+ newWidth / 2),
                                 (int)(- newHeight / 2),
                                 newWidth, newHeight, "br"), max);

        for (Map.Entry<String, Shape> s : shapes.entrySet()) {
            Shape shape = shapes.get(s.getKey());
            insert(shape);
        }
        shapes = new HashMap<>();
    }

    /** Return the remaining free area in the plane.*/
    public double freeArea() {
        double originalArea = plane.area();
        double occupiedArea = 0.0;
        double duplicatedArea = 0.0;

        for (Map.Entry<String, Shape> s : shapes.entrySet()) {
            occupiedArea += s.getValue().area();
        }
        for (Shape shape : _duplicated) {
            duplicatedArea += shape.area();
        }
        if (isSplit()) {
            occupiedArea += TopRight.plane.area() - TopRight.freeArea();
            occupiedArea += TopLeft.plane.area() - TopLeft.freeArea();
            occupiedArea += BotLeft.plane.area() - BotLeft.freeArea();
            occupiedArea += BotRight.plane.area() - BotRight.freeArea();
        }
        return originalArea - occupiedArea + duplicatedArea;
    }

}