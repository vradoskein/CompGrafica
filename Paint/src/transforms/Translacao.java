package transforms;

import obj.Objeto;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Pedro
 */
public class Translacao {
    public static Objeto trans(int x, int y, Objeto o) {
        String op;
        Point p1, p2;

        p1 = o.getP1();
        p2 = o.getP2();

        Point newP1, newP2;

        newP1 = new Point(p1.x + x, p1.y + y);
        newP2 = new Point(p2.x + x, p2.y + y);

        return new Objeto(newP1, newP2, o.getOp(), o.getNome());
    }
}
