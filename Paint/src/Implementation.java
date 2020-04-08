import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

/*
 * Class for the algorithms
 */

/**
 *
 * @author vradoskein
 */
public class Implementation {
    
    public static void save() {
        //TODO
    }

    public static void dot(Point point, Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);
        g.drawLine(point.x, point.y, point.x, point.y);
    }

    

    public static void open() {
        //TODO
    }

    public static void clear() {
        //TODO
    }

    public static void square() {
        //TODO
        System.out.println("This is a test");
    }

    public static void dda(Point p1, Point p2, Graphics2D g) {
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

    public static void bresenham(Point p1, Point p2, Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);
        int dx, dy, p, const1, const2, x, y;
        dx = Math.abs(p2.x - p1.x);
        dy = Math.abs(p2.y - p1.y);
        p = 2 * dy - dx;
        const1 = 2 * dy;
        const2 = 2 * (dy - dx);
        x = p1.x; y = p1.y;
        g.drawLine(x, y, x, y);
        while (x < p2.x) {
            x = x + 1;
            if (p < 0)
                p += const1;
            else {
                p += const2; 
                y++;
            }
            g.drawLine(x, y, x, y);
        }
    }

    public static void circ() {
        //TODO
    }

    public static void trans() {
        //TODO
    }

    public static void rot() {
        //TODO
    }

    public static void escala() {
        //TODO
    }

    public static void reflexao() {
        //TODO
    }

    public static void cohen() {
        //TODO
    }

    public static void flood() {
        //TODO
    }

}
