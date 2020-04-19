package transforms;

import java.awt.Point;
import java.util.ArrayList;
import obj.Objeto;

/**
 *
 * @author Pedro
 */
public class Escala {
    public static Objeto scale(double escala, Objeto o) {

        ArrayList<Point> pontos = o.getPontos();
        ArrayList<Point> new_pontos = new ArrayList<Point>();

        // scales the object
        for (Point p : pontos) {
            new_pontos.add(new Point((int) (p.x * escala), (int) (p.y * escala)));
        }

        return new Objeto(new_pontos, o.getOp(), o.getNome());
    }
}
