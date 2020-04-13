package plots;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Pedro
 */
public class Retangulo {
    public static void plot(Point p1, Point p2, Graphics2D g) {
        Point point1 = new Point(p1.x, p1.y);
        Point point2 = new Point(p1.x, p2.y);
        Point point3 = new Point(p2.x, p1.y);
        Point point4 = new Point(p2.x, p2.y);
        DDA.plot(point1, point2,g);
        DDA.plot(point1, point3,g);
        DDA.plot(point3, point4,g);
        DDA.plot(point2, point4,g);
    }
}
