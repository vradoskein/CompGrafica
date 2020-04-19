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

// handles DDA line ploting, base for the parallel DDA implementation
public class DDA {

    static Point p1, p2, pmedio;
    static Point p1dividido, p2dividido;

    public static void plot(Objeto o, Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);

        p1 = o.getPontos().get(0);
        p2 = o.getPontos().get(1);
        System.out.println("[DDA PLOTING] [p1] :" + p1 + "[p2]" + p2);

        pmedio = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        p1dividido = new Point((p1.x + pmedio.x) / 2, (p1.y + pmedio.y) / 2);
        p2dividido = new Point((pmedio.x + p2.x) / 2, (pmedio.y + p2.y) / 2);

        System.out.println("[DDA PLOTING] [PONTO MEDIO] :" + pmedio);
        // plot(p1, p2, g);

        ParallelDDA tarefa1 = new ParallelDDA(p1, p1dividido, g, 1);
        ParallelDDA tarefa2 = new ParallelDDA(p1dividido, pmedio, g, 2);
        ParallelDDA tarefa3 = new ParallelDDA(pmedio, p2dividido, g, 3);
        ParallelDDA tarefa4 = new ParallelDDA(p2dividido, p2, g, 4);

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
        System.out.println("[DDA] : [parte1] [p1]" + tarefa1.getP1() + "[parte1] [p1]" + tarefa1.getP2());
        System.out.println("[DDA] : [parte2] [p1]" + tarefa2.getP1() + "[parte1] [p1]" + tarefa2.getP2());

    }

    // caso especifico para retangulo
    public static void plot(Point p1, Point p2, Graphics2D g) {
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);

        pmedio = new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
        p1dividido = new Point((p1.x + pmedio.x) / 2, (p1.y + pmedio.y) / 2);
        p2dividido = new Point((pmedio.x + p2.x) / 2, (pmedio.y + p2.y) / 2);

        ParallelDDA tarefa1 = new ParallelDDA(p1, p1dividido, g, 1);
        ParallelDDA tarefa2 = new ParallelDDA(p1dividido, pmedio, g, 2);
        ParallelDDA tarefa3 = new ParallelDDA(pmedio, p2dividido, g, 3);
        ParallelDDA tarefa4 = new ParallelDDA(p2dividido, p2, g, 4);

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

    }
}
