package shapes;

import java.util.ArrayList;
import java.util.List;

/**
 * The class for Rectangle.
 * Created by Kevin Fangzhou Chen. */

 public class Rectangle extends Shape{

    /** The top left X coordinate of this rectangle.*/
    private int X;

    /** The top left Y coordinate of this rectangle.*/
    private int Y;

    /** The width of this rectangle.*/
    private double width;

    /** The height of this rectangle.*/
    private double height;

    /** The center X. */
    private int centerX;

    /** The center Y. */
    private int centerY;


    /** Constructor. CENTERX, CENTERY are the center coordinates
     * of the rectangle, and WIDTH and HEIGHT are the width and height
     * of the rectangle. */
    Rectangle(int centerX, int centerY, double width, double height, String name) {
        this.X = (int) (centerX - width / 2);
        this.Y = (int) (centerY + height / 2);
        this.centerY = centerY;
        this.centerX = centerX;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    @Override
    void changeCenter(int centerX, int centerY) {
        this.X = (int) (centerX - width/ 2);
        this.Y = (int) (centerY + height / 2);
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    double area() {
        return width * height;
    }

    @Override
    List<Integer> getSubplane(Shape plane) {
        List<Integer> index = new ArrayList<>();
        int planeCenterX = plane.getCenterX();
        int planeCenterY = plane.getCenterY();
        double halfWidth = 0.5 * width;
        double halfHeight = 0.5 * height;

        if ((centerX - planeCenterX >= 0) && (centerY - planeCenterY >= 0)) {
            index.add(1);
            if (centerX - halfWidth > planeCenterX && centerY - halfHeight < planeCenterY) {
                index.add(4);
            }
            else if (centerX - halfWidth < planeCenterX && centerY - halfHeight > planeCenterY) {
                index.add(2);
            }
            else if (centerX - halfWidth < planeCenterX && centerY - halfHeight < planeCenterY) {
                index.add(2);
                index.add(3);
                index.add(4);
            }
        }

        else if ((centerX - planeCenterX <= 0) && (centerY - planeCenterY >= 0)) {
            index.add(2);
            if (centerX + halfWidth > planeCenterX && centerY - halfHeight > planeCenterY) {
                index.add(1);
            }
            else if (centerX + halfWidth < planeCenterX && centerY - halfHeight < planeCenterY) {
                index.add(3);
            }
            else if (centerX + halfWidth > planeCenterX && centerY - halfHeight < planeCenterY) {
                index.add(1);
                index.add(3);
                index.add(4);
            }
        }

        else if ((centerX - planeCenterX < 0) && (centerY - planeCenterY < 0)) {
            index.add(3);
            if (centerX + halfWidth > planeCenterX && centerY + halfHeight < planeCenterY) {
                index.add(4);
            }
            else if (centerX + halfWidth < planeCenterX && centerY + halfHeight > planeCenterY) {
                index.add(2);
            }
            else if (centerX + halfWidth > planeCenterX && centerY + halfHeight > planeCenterY) {
                index.add(1);
                index.add(2);
                index.add(4);
            }
        }

        else if ((centerX - planeCenterX > 0) && (centerY - planeCenterY > 0)) {
            index.add(4);
            if (centerX - halfWidth < planeCenterX && centerY + halfHeight < planeCenterY) {
                index.add(3);
            }
            else if (centerX - halfWidth > planeCenterX && centerY + halfHeight > planeCenterY) {
                index.add(1);
            }
            else if (centerX - halfWidth < planeCenterX && centerY + halfHeight > planeCenterY) {
                index.add(1);
                index.add(2);
                index.add(3);
            }
        }
        return index;
    }

    @Override
    boolean ifCollide(Shape shape) {
        double x1 = centerX;
        double y1 = centerY;
        double w1 = width;
        double h1 = height;

        if (shape instanceof Rectangle) {
            double x2 = shape.getCenterX();
            double y2 = shape.getCenterY();
            double w2 = ((Rectangle) shape).getWidth();
            double h2 = ((Rectangle) shape).getHeight();

            if ((Math.abs(x1 - x2) <  (w1/2 + w2/2)) &&
                (Math.abs(y1 - y2) < (h1/2 + h2/2))) {
                return true;
            }
        }
        if (shape instanceof Circle) {
            return shape.ifCollide(this);
        }
        return false;
    }//needed to be implemented

    @Override
    boolean outOfBounds(QuadTreePlane plane) {
        double leftX = plane.getBounds().getX();
        double topY = plane.getBounds().getY();
        double botY = topY - plane.getBounds().getHeight();
        double rightX = leftX + plane.getBounds().getWidth();
        double halfWidth = 0.5 * width;
        double halfHeight = 0.5 * height;

        if (centerY + halfHeight > topY) {
            return true;
        }
        if (centerY - halfHeight < botY) {
            return true;
        }
        if (centerX + halfWidth > rightX) {
            return true;
        }
        if (centerX - halfWidth < leftX) {
            return true;
        }
        return false;
    }

    @Override
    /**The width of this rectangle.*/
    public double getWidth() {
        return width;
    }

    /** Get the height of this rectangle.*/
    double getHeight() {
        return height;
    }

    /** Get the top left X coordinate of this rectangle.*/
    int getX() {
        return X;
    }

    /** Get the top left Y coordinate of this rectangle.*/
    int getY() {
        return Y;
    }

    @Override
    /** Change the width and height of this rect, */
    public void changeSize(int w, int h) {
        this.width = w;
        this.height = h;
        X = centerX - w/2;
        Y = centerY + h/2;
    }

    @Override
    public void changeSize(int rad) {
        throw new UnsupportedOperationException("Wrong Parameter.");
    }

    @Override
    /** Get Center X.*/
    public int getCenterX() {
        return centerX;
    }

    @Override
    /** Get Center X.*/
    public int getCenterY() {
        return centerY;
    }

    @Override
    public Shape copy() {
        return new Rectangle(centerX, centerY, width, height, name);
    }


}
