package transforms;

import obj.Objeto;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Pedro
 */
public class Translacao {
    public static Objeto translate(int x, int y, Objeto o) {
        ArrayList<Point> pontos = o.getPontos();
        ArrayList<Point> new_pontos = new ArrayList<Point>();

        // adds X and Y pixels to each points coordinates
        for (Point p : pontos) {
            new_pontos.add(new Point(p.x + x, p.y + y));
        }

        return new Objeto(new_pontos, o.getOp(), o.getNome());
    }
}
