/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plots;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author vradoskein
 */

// plots a line using the bresenham algorithm's generalization
public class BresenhamParallel extends Thread {

    Point p1, p2;
    Graphics2D g;
    int id;

    public BresenhamParallel(Point pinicial, Point pfinal, Graphics2D g, int id) {
        this.p1 = pinicial;
        this.p2 = pfinal;
        this.g = g;
        this.id = id;

    }

    public Point getP1() {
        return this.p1;
    }

    public Point getP2() {
        return this.p2;
    }

    @Override
    public void run() {
        int dx, dy, x, y, const1, const2, p, incrx, incry;
        dx = p2.x - p1.x;
        dy = p2.y - p1.y;

        if (dx >= 0)
            incrx = 1;
        else {
            incrx = -1;
            dx = -dx;
        }
        if (dy >= 0)
            incry = 1;
        else {
            incry = -1;
            dy = -dy;
        }

        x = p1.x;
        y = p1.y;
        g.drawLine(x, y, x, y);

        if (dy < dx) {
            p = 2 * dy - dx;
            const1 = 2 * dy;
            const2 = 2 * (dy - dx);
            for (int i = 0; i < dx; i++) {
                x += incrx;
                if (p < 0)
                    p += const1;
                else {
                    y += incry;
                    p += const2;
                }
                g.drawLine(x, y, x, y);
            }
        } else {
            p = 2 * dx - dy;
            const1 = 2 * dx;
            const2 = 2 * (dx - dy);
            for (int i = 0; i < dy; i++) {
                y += incry;
                if (p < 0)
                    p += const1;
                else {
                    x += incrx;
                    p += const2;
                }
                g.drawLine(x, y, x, y);
            }
        }
        System.out.println("[THREAD FINISHED]  [ID] : " + this.id);
    }
}
