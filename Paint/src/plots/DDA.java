package plots;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import obj.Objeto;

/**
 *
 * @author Pedro
 */
public class DDA {
    public static void plot(Objeto o, Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);
        
        Point p1, p2;
        p1 = o.getPontos().get(0);
        p2 = o.getPontos().get(1);
        
        plot(p1, p2, g);
    }
    
    public static void plot(Point p1, Point p2, Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);
        
        int dx = p2.x - p1.x;
        int dy = p2.y - p1.y;

        // calculate steps required for generating pixels
        int steps = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx) : Math.abs(dy);

        // calculate increment in x & y for each steps
        double Xinc = dx / (double) steps;
        double Yinc = dy / (double) steps;

        // Put pixel for each step
        double X = p1.x;
        double Y = p1.y;
        for (int i = 0; i <= steps; i++)
        {
            g.draw(new Line2D.Double(X, Y, X, Y));
            X += Xinc;
            Y += Yinc;
        }
    }
}
