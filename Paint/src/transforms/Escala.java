package transforms;

import java.awt.Point;
import obj.Objeto;

/**
 *
 * @author Pedro
 */
public class Escala {
    public static Objeto scale(double escala, Objeto o) {
        String op;
        Point p1, p2;

        p1 = o.getP1();
        p2 = o.getP2();

        Point newP1, newP2;

        newP1 = new Point( (int)(p1.x*escala) , (int)(p1.y*escala) );
        newP2 = new Point( (int)(p2.x*escala) , (int)(p2.y*escala) );
        
        return new Objeto(newP1, newP2, o.getOp(), o.getNome());
    }
}
