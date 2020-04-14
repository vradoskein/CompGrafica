package plots;

import java.awt.Graphics2D;
import java.awt.Point;
import obj.Objeto;

/**
 *
 * @author Pedro
 */
public class Retangulo {
    public static void plot(Objeto o, Graphics2D g) {
        Point p1, p2, p3, p4;
        p1 = o.getPontos().get(0);
        p2 = o.getPontos().get(1);
        p3 = o.getPontos().get(2);
        p4 = o.getPontos().get(3);
        
        DDA.plot(p1, p3, g);
        DDA.plot(p3, p2, g);
        DDA.plot(p2, p4, g);
        DDA.plot(p4, p1, g);
    }
}
