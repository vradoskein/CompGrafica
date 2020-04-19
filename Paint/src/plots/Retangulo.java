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
        Point aux;
        p1 = o.getPontos().get(0);
        p2 = o.getPontos().get(1);
        p3 = o.getPontos().get(2);
        p4 = o.getPontos().get(3);
        
        System.out.println(
                "[RETANGULO CLASS BEFORE]" + "[P1] :" + p1 + "[P2] :" + p2 + "[P3] :" + p3 + "[P4] :" + p4);

        /*if(p1.y > p3.y ){
            aux = p1;
            p1 = p3;
            p3 = aux;
        }
        
        if(p2.y < p4.y){
            aux = p2;
            p2 = p4;
            p4 = aux;
                    
        }*/
        
        

        System.out.println(
                "[RETANGULO CLASS AFTER]" + "[P1] :" + p1 + "[P2] :" + p2 + "[P3] :" + p3 + "[P4] :" + p4);

        DDA.plot(p1, p3, g);//

        DDA.plot(p3, p2, g);

        DDA.plot(p2, p4, g);//

        DDA.plot(p4, p1, g);
    }
}
