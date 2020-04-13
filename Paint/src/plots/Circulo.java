package plots;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Pedro
 */
public class Circulo {
    public static void plot(Point p1, int r , Graphics2D g){
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
}
