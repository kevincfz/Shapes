package shapes;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Junit 4.11 Test for QuadTreePlane.
 */
public class TestPlane {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testSimpleArea() {
        QuadTreePlane myPlane = new QuadTreePlane(
                new Rectangle(0, 0, 400, 400, "aa"), 3, true);
        Rectangle rectangle1 = new Rectangle(-100, -100, 10, 10, "r1");
        Rectangle rectangle2 = new Rectangle(100, 100, 10, 10, "r2");
        myPlane.insert(rectangle1);
        myPlane.insert(rectangle2);
        assertEquals(159800.0, myPlane.freeArea(), 0.01);
    }

    @Test
    /** If implemented correctly, the centerRectangle insertion will create
     * three duplication and but will not be recorded in the calculation of
     * free area.
     */
    public void testDuplication() {
        QuadTreePlane myPlane = new QuadTreePlane(
                new Rectangle(0, 0, 400, 400, "aa"), 3, true);
        Rectangle rectangle1 = new Rectangle(-100, -100, 10, 10, "r1");
        Rectangle rectangle2 = new Rectangle(100, 100, 10, 10, "r2");
        Rectangle rectangle3 = new Rectangle(70, 70, 10, 10, "r3");
        Rectangle centerRectangle = new Rectangle(0, 0, 40, 40, "rc");
        myPlane.insert(rectangle1);
        myPlane.insert(rectangle2);
        myPlane.insert(rectangle3);
        myPlane.insert(centerRectangle);
        assertEquals(158100.0, myPlane.freeArea(), 0.01);
    }

    @Test
    public void testRemove() {
        QuadTreePlane myPlane = new QuadTreePlane(
                new Rectangle(0, 0, 400, 400, "oo"), 3, true);
        Rectangle rectangle1 = new Rectangle(-100, -100, 10, 10, "r1");
        Rectangle rectangle2 = new Rectangle(100, 100, 10, 10, "r2");
        Rectangle rectangle3 = new Rectangle(70, 70, 10, 10, "r3");
        Rectangle centerRectangle = new Rectangle(0, 0, 40, 40, "rc");
        myPlane.insert(rectangle1);
        myPlane.insert(rectangle2);
        myPlane.insert(rectangle3);
        myPlane.insert(centerRectangle);
        myPlane.remove("r1");
        myPlane.remove("rc");
    }

    @Test
    public void testCirclesCollision() {
        exception.expect(UnsupportedOperationException.class);
        QuadTreePlane myPlane = new QuadTreePlane(
                new Rectangle(0, 0, 400, 400, "oo"), 3, true);
        Circle c1 = new Circle(30, 40, 51, "c1");
        Circle c2 = new Circle(-40, -30, 50, "c1");
        myPlane.insert(c1);
        myPlane.insert(c2);
    }

    @Test
    public void testCircleRectCollision() {
        exception.expect(UnsupportedOperationException.class);
        QuadTreePlane myPlane = new QuadTreePlane(
                new Rectangle(0, 0, 400, 400, "oo"), 3, true);
        Rectangle r1 = new Rectangle(15, 20, 10, 20,"r1");
        Circle c2 = new Circle(1, 1, (int)(10 * Math.sqrt(2)), "c1");
        myPlane.insert(r1);
        myPlane.insert(c2);
    }

    @Test
    public void testCircleRectCollision2() {
        QuadTreePlane myPlane = new QuadTreePlane(
                new Rectangle(0, 0, 400, 400, "oo"), 3, true);
        Rectangle r1 = new Rectangle(15, 20, 10, 20,"r1");
        Circle c2 = new Circle(-1, -1, (int)(10 * Math.sqrt(2)), "c1");
        myPlane.insert(r1);
        myPlane.insert(c2);
    }

    @Test
    public void testBounds() {
        exception.expect(UnsupportedOperationException.class);
        QuadTreePlane myPlane = new QuadTreePlane(
                new Rectangle(0, 0, 100, 80, "oo"), 3, true);
        Rectangle r1 = new Rectangle(90, 0, 20, 10,"r1");
        myPlane.insert(r1);
    }

    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(TestPlane.class));
    }

    @Test
    public void testMove() {
        QuadTreePlane myPlane = new QuadTreePlane(
                new Rectangle(0, 0, 400, 400, "aa"), 3, true);
        Rectangle rectangle1 = new Rectangle(-100, -100, 10, 10, "r1");
        Rectangle rectangle2 = new Rectangle(100, 90, 20, 10, "r2");
        Rectangle rectangle3 = new Rectangle(70, 70, 10, 10, "r3");
        Rectangle centerRectangle = new Rectangle(0, 0, 40, 40, "rc");
        myPlane.insert(rectangle1);
        myPlane.insert(rectangle2);
        myPlane.insert(rectangle3);
        myPlane.insert(centerRectangle);
        myPlane.move(centerRectangle, 0, 10);
    }

    @Test
    public void testResize() {
        QuadTreePlane myPlane = new QuadTreePlane(
                new Rectangle(0, 0, 400, 400, "aa"), 3, true);
        Rectangle rectangle3 = new Rectangle(70, 70, 10, 10, "r3");
        Rectangle centerRectangle = new Rectangle(0, 0, 40, 40, "rc");
        myPlane.insert(rectangle3);
        myPlane.insert(centerRectangle);
        Shape newRect3 = myPlane.changeSize(rectangle3, 88, 78);
        assertEquals(88, newRect3.getWidth(), 0.01);

    }
}
