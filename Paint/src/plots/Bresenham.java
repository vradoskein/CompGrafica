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

// parallel base for the bresenham plotting algorithm
public class Bresenham {

    static Point p1, p2, pmedio;
    static Point p1dividido, p2dividido;

    public static void plot(Objeto o, Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);

        p1 = o.getPontos().get(0);
        p2 = o.getPontos().get(1);

        int mx = (p1.x + p2.x) / 2;
        int my = (p1.y + p2.y) / 2;
        pmedio = new Point(mx, my);

        p1dividido = new Point((p1.x + pmedio.x) / 2, (p1.y + pmedio.y) / 2);

        p2dividido = new Point((pmedio.x + p2.x) / 2, (pmedio.y + p2.y) / 2);

        BresenhamParallel tarefa1 = new BresenhamParallel(p1, p1dividido, g, 1);
        BresenhamParallel tarefa2 = new BresenhamParallel(p1dividido, pmedio, g, 2);
        BresenhamParallel tarefa3 = new BresenhamParallel(pmedio, p2dividido, g, 3);
        BresenhamParallel tarefa4 = new BresenhamParallel(p2dividido, p2, g, 4);

        tarefa1.start();
        tarefa2.start();
        tarefa3.start();
        tarefa4.start();

        try {
            tarefa1.join();
            tarefa2.join();
            tarefa3.join();
            tarefa4.join();
        } catch (InterruptedException ex) {
        }

        /*
         * int dx, dy, x, y, const1, const2, p, incrx, incry; dx = p2.x - p1.x; dy =
         * p2.y - p1.y;
         * 
         * if(dx >= 0) incrx = 1; else{ incrx = -1; dx = -dx; } if(dy >= 0) incry = 1;
         * else{ incry = -1; dy = -dy; }
         * 
         * x = p1.x; y = p1.y; g.drawLine(x, y, x, y);
         * 
         * if(dy < dx){ p = 2*dy - dx; const1 = 2*dy; const2 = 2*(dy-dx); for(int i = 0;
         * i < dx; i++){ x += incrx; if(p < 0) p += const1; else{ y += incry; p +=
         * const2; } g.drawLine(x, y, x, y); } } else { p = 2*dx - dy; const1 = 2*dx;
         * const2 = 2*(dx-dy); for(int i = 0; i < dy; i++){ y += incry; if(p < 0) p +=
         * const1; else{ x += incrx; p += const2; } g.drawLine(x, y, x, y); } }
         */

    }
}
