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

    public static void square(Point p1, Point p2, Graphics2D g) {
        //TODO
        System.out.println("This is a test");
        Point point1 = new Point(p1.x, p1.y);
        Point point2 = new Point(p1.x, p2.y);
        Point point3 = new Point(p2.x, p1.y);
        Point point4 = new Point(p2.x, p2.y);
        dda(point1, point2,g);
        dda(point1, point3,g);
        dda(point3, point4,g);
        dda(point2, point4,g);
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
        int dx, dy, x, y, const1, const2, p, incrx, incry;
        dx = p2.x - p1.x;
        dy = p2.y - p1.y;

        if(dx >= 0) incrx = 1;
        else{
            incrx = -1;
            dx = -dx;
        }
        if(dy >= 0) incry = 1;
        else{
            incry = -1;
            dy = -dy;
        }

        x = p1.x; y = p1.y;
        g.drawLine(x, y, x, y);

        if(dy < dx){
            p = 2*dy - dx;
            const1 = 2*dy;
            const2 = 2*(dy-dx);
            for(int i = 0; i < dx; i++){
                x += incrx;
                if(p < 0) p += const1;
                else{
                    y += incry;
                    p += const2;
                }
                g.drawLine(x, y, x, y);
            }
        } else {
            p = 2*dx - dy;
            const1 = 2*dx;
            const2 = 2*(dx-dy);
            for(int i = 0; i < dy; i++){
                y += incry;
                if(p < 0) p += const1;
                else{
                    x += incrx;
                    p += const2;
                }
                g.drawLine(x, y, x, y);
            }
        }

    }

    private static void drawCircle(int xc, int yc, int x, int y, Graphics2D g){
        g.drawLine(xc+x, yc+y, xc+x, yc+y);
        g.drawLine(xc-x, yc+y, xc-x, yc+y);
        g.drawLine(xc+x, yc-y, xc+x, yc-y);
        g.drawLine(xc-x, yc-y, xc-x, yc-y);
        g.drawLine(xc+y, yc+x, xc+y, yc+x);
        g.drawLine(xc-y, yc+x, xc-y, yc+x);
        g.drawLine(xc+y, yc-x, xc+y, yc-x);
        g.drawLine(xc-y, yc-x, xc-y, yc-x);
    }

// Function for circle-generation
// using Bresenham's algorithm
    public static void circ(Point p1, int r , Graphics2D g){
        int xc = p1.x;
        int yc = p1.y;
        int x = 0, y = r;
        int d = 3 - 2 * r;
        drawCircle(xc, yc, x, y,g);
        while (y >= x)
        {
            // for each pixel we will
            // draw all eight pixels

            x++;

            // check for decision parameter
            // and correspondingly
            // update d, x, y
            if (d > 0)
            {
                y--;
                d = d + 4 * (x - y) + 10;
            }
            else
                d = d + 4 * x + 6;
            drawCircle(xc, yc, x, y,g);
        }
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
