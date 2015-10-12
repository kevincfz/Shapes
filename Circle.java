package shapes;

import java.util.ArrayList;
import java.util.List;

public class Circle extends Shape {

    /** Initial Coordinate X of the center.*/
    private int x;
    /** Initial Coordinate Y of the center.*/
    private int y;

    /** The Radius of the circle. */
    int radius;

    public Circle(int x, int y, int radius, String name) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.name = name;

    }

    public int getX() {
        return x;
    }

    @Override
    public int getCenterX(){
        return getX();
    }

    public int getY() {
        return y;
    }

    @Override
    public int getCenterY() {
        return getY();
    }

    @Override
    void changeCenter(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    double area() {
        return radius * radius * Math.PI;
    }

    @Override
    List<Integer> getSubplane(Shape plane) {
        List<Integer> index = new ArrayList<>();
        int planeCenterX = plane.getCenterX();
        int planeCenterY = plane.getCenterY();
        double centerDistance = Math.sqrt(Math.pow((planeCenterX - x), 2) +
                                          Math.pow((planeCenterY - y), 2));

        if ((x - planeCenterX >= 0) && (y - planeCenterY >= 0)) {
            index.add(1);
            if (x - radius > planeCenterX && y - radius < planeCenterY) {
                index.add(4);
            } else if (x - radius < planeCenterX && y - radius > planeCenterY) {
                index.add(2);
            } else if (x - radius < planeCenterX && y - radius < planeCenterY) {
                if (radius <= centerDistance) {
                    index.add(2);
                    index.add(4);
                } else {
                    index.add(3);
                }
            }
        }

        else if ((x - planeCenterX <= 0) && (y - planeCenterY >= 0)) {
            index.add(2);
            if (x + radius > planeCenterX && y - radius > planeCenterY) {
                index.add(1);
            }
            else if (x + radius < planeCenterX && y - radius < planeCenterY) {
                index.add(3);
            }
            else if (x + radius > planeCenterX && y - radius < planeCenterY) {
                if (radius <= centerDistance ) {
                    index.add(1);
                    index.add(3);
                } else {
                    index.add(4);
                }
            }
        }

        else if ((x - planeCenterX < 0) && (y - planeCenterY < 0)) {
            index.add(3);
            if (x + radius > planeCenterX && y + radius < planeCenterY) {
                index.add(4);
            }
            else if (x + radius < planeCenterX && y + radius > planeCenterY) {
                index.add(2);
            }
            else if (x + radius > planeCenterX && y + radius > planeCenterY) {
                if (radius <= centerDistance ) {
                    index.add(2);
                    index.add(4);
                } else {
                    index.add(1);
                }
            }
        }

        else if ((x - planeCenterX > 0) && (y - planeCenterY > 0)) {
            index.add(4);
            if (x - radius < planeCenterX && y + radius < planeCenterY) {
                index.add(3);
            }
            else if (x - radius > planeCenterX && y + radius > planeCenterY) {
                index.add(1);
            }
            else if (x - radius < planeCenterX && y + radius > planeCenterY) {
                if (radius <= centerDistance ) {
                    index.add(2);
                    index.add(3);
                } else {
                    index.add(1);
                }
            }
        }
        return index;
    }


    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt((Math.pow(Math.abs(x1 - x2), 2)
                + Math.pow(Math.abs(y1 - y2), 2)));
    }

    @Override
    boolean ifCollide(Shape shape) {
        if (shape instanceof Circle) {
            double centerDistance = Math.sqrt(Math.pow(Math.abs(this.x - shape.getCenterX()), 2)
                    + Math.pow(Math.abs(this.y - shape.getCenterY()), 2));

            if (centerDistance < (this.radius + ((Circle) shape).radius)) {
                return true;
            } else {
                return false;
            }
        }

        if (shape instanceof Rectangle) {
            Rectangle boundingRect = new Rectangle(this.x, this.y,
                    2 * radius, 2 * radius, "br");
            if (!boundingRect.ifCollide(shape)) {
                return false;
            } else {
                double w = ((Rectangle) shape).getWidth();
                double h = ((Rectangle) shape).getHeight();
                double cx = shape.getCenterX();
                double cy = shape.getCenterY();
                if (cy > y && cx > x) {
                    if (((cy - 0.5 * h) < y) || (cx - 0.5 * w) < x) {
                        return true;
                    } else {
                        if (distance(cx - 0.5 * w, cy - 0.5 * h, x, y) < radius) {
                            return true;
                        }
                    }
                }

                if (cy > y && cx < x) {
                    if (((cy - 0.5 * h) < y) || (cx + 0.5 * w) > x) {
                        return true;
                    } else {
                        if (distance(cx + 0.5 * w, cy - 0.5 * h, x, y) < radius) {
                            return true;
                        }
                    }
                }

                if (cy < y && cx < x) {
                    if (((cy + 0.5 * h) > y) || (cx + 0.5 * w > x)) {
                        return true;
                    } else {
                        if (distance(cx + 0.5 * w, cy + 0.5 * h, x, y) < radius) {
                            return true;
                        }
                    }
                }
                if (cy < y && cx > x) {
                    if (((cy + 0.5 * h > y || (cx - 0.5 * w < x)))) {
                        return true;
                    } else {
                        if (distance(cx - 0.5 * w, cy + 0.5 * h, x, y) < radius) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    @Override
    boolean outOfBounds(QuadTreePlane plane) {
        double leftX = plane.getBounds().getX();
        double topY = plane.getBounds().getY();
        double botY = topY - plane.getBounds().getHeight();
        double rightX = leftX + plane.getBounds().getWidth();

        if (y + radius > topY) {
            return true;
        }
        if (y - radius < botY) {
            return true;
        }
        if (x + radius > rightX) {
            return true;
        }
        if (x - radius < leftX) {
            return true;
        }
        return false;
    }

    @Override
    public Shape copy() {
        return new Circle(this.x, this.y, radius, this.name);
    }

    @Override
    public void changeSize(int rad) {
        radius = rad;
    }

    @Override
    public void changeSize(int w, int h) {
        throw new UnsupportedOperationException("Wrong Parameter.");
    }

    @Override
    public double getWidth() {
        return (double) radius;
    }
}