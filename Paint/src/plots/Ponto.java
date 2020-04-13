package plots;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Pedro
 */
public class Ponto {
    public static void plot(Point point, Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);
        g.drawLine(point.x, point.y, point.x, point.y);
    }
}
