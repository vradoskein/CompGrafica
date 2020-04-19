package plots;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import obj.Objeto;

/**
 *
 * @author Pedro
 */
public class Ponto {
    public static void plot(Objeto o, Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);
        // plots a dot at the determined point
        Point point = o.getPontos().get(0);
        g.drawLine(point.x, point.y, point.x, point.y);
    }
}
