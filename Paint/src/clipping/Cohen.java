/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clipping;

import java.awt.Point;
import java.util.ArrayList;
import obj.Objeto;

/**
 *
 * @author vradoskein
 */
public class Cohen {

    public static ArrayList<Objeto> objs_recortado = new ArrayList<Objeto>();

    public static ArrayList<Objeto> objetosCohen;
    // Defining region codes
    static final byte INSIDE = 0; // 0000
    static final byte LEFT = 1; // 0001
    static final byte RIGHT = 2; // 0010
    static final byte BOTTOM = 4; // 0100
    static final byte TOP = 8; // 1000

    // Defining x_max, y_max and x_min, y_min for
    // clipping rectangle. Since diagonal points are
    // enough to define a rectangle
    static int x_max;
    static int y_max;
    static int x_min;
    static int y_min;

    static double x1;
    static double x2;
    static double y1;
    static double y2;

    public static ArrayList<Objeto> cohenstart(Point old_point, Point current_point, ArrayList<Objeto> objetos) {
        Cohen.objetosCohen = objetos;
        cohenPoints(old_point, current_point);
        Cohen(old_point, current_point);
        return objs_recortado;
    }

    // checks which objects will be clipped
    public static void checkObs_recorte() {
        System.out.println("[DATA] Objetos recortados existentes:");
        for (Objeto o : objs_recortado) {
            System.out.println(o.getNome() + " - operação: " + o.getOp() + " - pontos :");
            ArrayList<Point> pontos = o.getPontos();
            for (Point p : pontos) {
                System.out.println("x: " + p.x + " - y: " + p.y);
            }
        }
    }

    // adds new clipped objects to the list
    public static Objeto saveAdd_recorte(ArrayList<Point> p, String c, String tipo) {
        String nome = tipo + " ";
        Objeto d = new Objeto(p, c, nome);

        objs_recortado.add(d);
        System.out.println("[INFO] Salvo objeto recortado " + nome + " do tipo " + c + "com pontos: ");
        for (Point i : p) {
            System.out.println("p1: " + i.x + "e p2: " + i.y);
        }
        checkObs_recorte();

        return d;
    }

    // generalizes the rectangle into 4 lines
    public static void retang_to_reta(ArrayList<Point> pr, int x) {

        if (x == 1) {
            x1 = pr.get(0).x;
            x2 = pr.get(2).x;
            y1 = pr.get(0).y;
            y2 = pr.get(2).y;

        } else if (x == 2) {
            x1 = pr.get(2).x;
            x2 = pr.get(1).x;
            y1 = pr.get(2).y;
            y2 = pr.get(1).y;

        } else if (x == 3) { // certo
            x1 = pr.get(0).x;
            x2 = pr.get(3).x;
            y1 = pr.get(0).y;
            y2 = pr.get(3).y;

        } else if (x == 4) {
            x1 = pr.get(1).x;
            x2 = pr.get(3).x;
            y1 = pr.get(1).y;
            y2 = pr.get(3).y;

        }

    }

    // Function to compute region code for a point(x, y)
    public static byte computeCode(double xobj, double yobj) {
        // initialized as being inside
        byte code = INSIDE;

        if (xobj < x_min) // to the left of rectangle
        {
            code = LEFT;
        } else if (xobj > x_max) // to the right of rectangle
        {
            code |= RIGHT;
        }
        if (yobj < y_min) {
            code |= TOP;
        } else if (yobj > y_max) {
            code |= BOTTOM;
        }

        return code;
    }

    // sets up the clipping area
    public static void cohenPoints(Point p1, Point p2) {
        if (p1.x > p2.x) {
            x_max = p1.x;
            x_min = p2.x;
        } else {
            x_max = p2.x;
            x_min = p1.x;
        }
        if (p1.y > p2.y) {
            y_max = p1.y;
            y_min = p2.y;
        } else {
            y_max = p2.y;
            y_min = p1.y;
        }
    }

    // checks of the object should be clipped and sets up its new point coordinates
    public static void cohenLogic() {
        String c = "reta";

        boolean aceite = false, feito = false;
        while (!feito) {

            byte c1, c2;
            c1 = computeCode(x1, y1);
            c2 = computeCode(x2, y2);
            if ((c1 | c2) == INSIDE) {
                aceite = true;
                feito = true;
            } else if ((c1 & c2) != INSIDE) {
                feito = true;
            } else {

                double xint = 0.0, yint = 0.0;
                final byte cfora = (c1 != INSIDE) ? c1 : c2;

                if ((cfora & LEFT) != 0) {
                    xint = x_min;
                    yint = y1 + (y2 - y1) * (x_min - x1) / (x2 - x1);
                } else if ((cfora & RIGHT) != 0) {
                    xint = x_max;
                    yint = y1 + (y2 - y1) * (x_max - x1) / (x2 - x1);
                } else if ((cfora & BOTTOM) != 0) {
                    yint = y_max;
                    xint = x1 + (x2 - x1) * (y_max - y1) / (y2 - y1);
                } else if ((cfora & TOP) != 0) {
                    yint = y_min;
                    xint = x1 + (x2 - x1) * (y_min - y1) / (y2 - y1);
                }

                if (c1 == cfora) {
                    x1 = xint;
                    y1 = yint;
                } else {
                    x2 = xint;
                    y2 = yint;
                }
            }
        }
        if (aceite) {
            ArrayList<Point> pontos_recorte = new ArrayList<Point>();
            Point p1_recorte = new Point(Math.round((float) x1), (int) Math.round(y1));
            Point p2_recorte = new Point(Math.round((float) x2), (int) Math.round(y2));
            String nome = " Reta recortada ";
            pontos_recorte.add(p1_recorte);
            pontos_recorte.add(p2_recorte);

            saveAdd_recorte(pontos_recorte, c, nome);
        }
    }

    // handles each object and sets them up to be clipped
    public static void Cohen(Point p1, Point p2) {
        String c = "reta";

        for (int i = 0; i < objetosCohen.size(); i++) {
            if (objetosCohen.get(i).getNome().contains("Reta ")) {
                x1 = objetosCohen.get(i).getPontos().get(0).x;
                x2 = objetosCohen.get(i).getPontos().get(1).x;
                y1 = objetosCohen.get(i).getPontos().get(0).y;
                y2 = objetosCohen.get(i).getPontos().get(1).y;

                cohenLogic();
                /*
                 * boolean aceite = false, feito = false;
                 * 
                 * while (!feito) { byte c1, c2; c1 = computeCode(x1, y1); c2 = computeCode(x2,
                 * y2); if ((c1 | c2) == INSIDE) { aceite = true; feito = true; } else if ((c1 &
                 * c2) != INSIDE) { feito = true; } else {
                 * 
                 * double xint = 0.0, yint = 0.0; final byte cfora = (c1 != INSIDE) ? c1 : c2;
                 * 
                 * if ((cfora & LEFT) != 0) { xint = x_min; yint = y1 + (y2 - y1) * (x_min - x1)
                 * / (x2 - x1); } else if ((cfora & RIGHT) != 0) { xint = x_max; yint = y1 + (y2
                 * - y1) * (x_max - x1) / (x2 - x1); } else if ((cfora & BOTTOM) != 0) { yint =
                 * y_max; xint = x1 + (x2 - x1) * (y_max - y1) / (y2 - y1); } else if ((cfora &
                 * TOP) != 0) { yint = y_min; xint = x1 + (x2 - x1) * (y_min - y1) / (y2 - y1);
                 * }
                 * 
                 * if (c1 == cfora) { x1 = xint; y1 = yint; } else { x2 = xint; y2 = yint; } } }
                 * if (aceite) { ArrayList<Point> pontos_recorte = new ArrayList<Point>(); Point
                 * p1_recorte = new Point(Math.round((float) x1), (int) Math.round(y1)); Point
                 * p2_recorte = new Point(Math.round((float) x2), (int) Math.round(y2)); String
                 * nome = " Reta recortada "; pontos_recorte.add(p1_recorte);
                 * pontos_recorte.add(p2_recorte);
                 * 
                 * saveAdd_recorte(pontos_recorte, c, nome); }
                 */
            } else if (objetosCohen.get(i).getNome().contains("Retan")) {
                Point pr1 = objetosCohen.get(i).getPontos().get(0);
                Point pr2 = objetosCohen.get(i).getPontos().get(1);
                Point pr3 = objetosCohen.get(i).getPontos().get(2);
                Point pr4 = objetosCohen.get(i).getPontos().get(3);

                System.out.println("[PR1] " + pr1);
                System.out.println("[PR2] " + pr2);
                System.out.println("[PR3] " + pr3);
                System.out.println("[PR4] " + pr4);

                retang_to_reta(objetosCohen.get(i).getPontos(), 1);
                cohenLogic();
                retang_to_reta(objetosCohen.get(i).getPontos(), 2);
                cohenLogic();
                retang_to_reta(objetosCohen.get(i).getPontos(), 3);
                cohenLogic();
                retang_to_reta(objetosCohen.get(i).getPontos(), 4);
                cohenLogic();
            } // fim do for
        }
    }

}
