package plots;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import obj.Objeto;

/**
 *
 * @author Pedro
 */
public class DDA {

    static Point p1, p2, pmedio;

    public static void plot(Objeto o, Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);

        p1 = o.getPontos().get(0);
        p2 = o.getPontos().get(1);
        System.out.println("[DDA PLOTING] [p1] :" + p1 + "[p2]" + p2);

        int mx = (p1.x + p2.x) / 2;
        int my = (p1.y + p2.y) / 2;
        pmedio = new Point(mx, my);
        
        System.out.println("[DDA PLOTING] [PONTO MEDIO] :" + pmedio);
        //plot(p1, p2, g);

        ParallelDDA tarefa1 = new ParallelDDA(p1, pmedio, g);
        ParallelDDA tarefa2 = new ParallelDDA(pmedio, p2, g);

        tarefa1.start();
        tarefa2.start();

        try {
            tarefa1.join();
            tarefa2.join();
        } catch (InterruptedException ex) {
        }
        System.out.println("[DDA] : [parte1] [p1]" + tarefa1.getP1() +"[parte1] [p1]" + tarefa1.getP2());
        System.out.println("[DDA] : [parte2] [p1]" + tarefa2.getP1() +"[parte1] [p1]" + tarefa2.getP2());

    }

    
    //caso especifico para retangulo
    public static void plot(Point p1, Point p2, Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);

        
        int mx = (p1.x + p2.x) / 2;
        int my = (p1.y + p2.y) / 2;
        pmedio = new Point(mx, my);
        
        ParallelDDA tarefa1 = new ParallelDDA(p1, pmedio, g);
        ParallelDDA tarefa2 = new ParallelDDA(pmedio, p2, g);

        tarefa1.start();
        tarefa2.start();

        try {
            tarefa1.join();
            tarefa2.join();
        } catch (InterruptedException ex) {
        }
        
    }
}
