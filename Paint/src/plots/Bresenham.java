package plots;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Pedro
 */
public class Bresenham {
    public static void plot(Point p1, Point p2, Graphics2D g) {
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
}
