package plots;

import java.awt.Graphics2D;
import java.awt.Point;
import obj.Objeto;

/**
 *
 * @author Pedro
 */

// generalization of the bresenham algorithm for circles
public class Circulo {
    public static void plot(Objeto o, Graphics2D g) {
        Point p1, p2;
        p1 = o.getPontos().get(0);
        p2 = o.getPontos().get(1);

        int r = (int) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));

        int xc = p1.x;
        int yc = p1.y;
        int x = 0, y = r;
        int d = 3 - 2 * r;
        drawCircle(xc, yc, x, y, g);
        while (y >= x) {
            // for each pixel we will
            // draw all eight pixels

            x++;

            // check for decision parameter
            // and correspondingly
            // update d, x, y
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else
                d = d + 4 * x + 6;
            drawCircle(xc, yc, x, y, g);
        }
    }

    private static void drawCircle(int xc, int yc, int x, int y, Graphics2D g) {
        g.drawLine(xc + x, yc + y, xc + x, yc + y);
        g.drawLine(xc - x, yc + y, xc - x, yc + y);
        g.drawLine(xc + x, yc - y, xc + x, yc - y);
        g.drawLine(xc - x, yc - y, xc - x, yc - y);
        g.drawLine(xc + y, yc + x, xc + y, yc + x);
        g.drawLine(xc - y, yc + x, xc - y, yc + x);
        g.drawLine(xc + y, yc - x, xc + y, yc - x);
        g.drawLine(xc - y, yc - x, xc - y, yc - x);
    }
}
