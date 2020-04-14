package transforms;

import java.awt.Point;
import java.util.ArrayList;
import obj.Objeto;

/**
 *
 * @author Pedro
 */
public class Rotacao {
    public static Objeto rotate(double deg, Objeto o) {
        ArrayList<Point> pontos = o.getPontos();
        ArrayList<Point> new_pontos = new ArrayList<Point>();

        double deg_radians = Math.toRadians(deg);
        
        for(Point p : pontos){
            new_pontos.add(new Point((int)((p.x * Math.cos(deg_radians)) - (p.y * Math.sin(deg_radians))), 
                (int)((p.x * Math.sin(deg_radians)) + (p.y * Math.cos(deg_radians)))));
        }
        
        return new Objeto(new_pontos, o.getOp(), o.getNome());
    }
    
    public static Objeto reflect(int axis, Objeto o){
        ArrayList<Point> pontos = o.getPontos();
        ArrayList<Point> new_pontos = new ArrayList<Point>();

        switch(axis){
            case 0: //x
                for(Point p : pontos){
                    new_pontos.add(new Point((p.x * 1) - (p.y * 0), 
                        (p.x * 0) + (p.y * -1)));
                }
                break;
            case 1: //y
                for(Point p : pontos){
                    new_pontos.add(new Point((p.x * -1) - (p.y * 0), 
                        (p.x * 0) + (p.y * 1)));
                }
                break;
            case 2: //xy
                for(Point p : pontos){
                    new_pontos.add(new Point((p.x * -1) - (p.y * 0), 
                        (p.x * 0) + (p.y * -1)));
                }
                break;
        }
        
        return new Objeto(new_pontos, o.getOp(), o.getNome());
    }
}
