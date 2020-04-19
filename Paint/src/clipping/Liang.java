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
public class Liang {

    public static ArrayList<Objeto> objs_recortado = new ArrayList<Objeto>();

    public static ArrayList<Objeto> objetosliang;
    static double u1 = 0, u2 = 1.0;

    static double x1;
    static double x2;
    static double y1;
    static double y2;
    static double dx;
    static double dy;

    static int xjmin = 0;
    static int xjmax = 0;
    static int yjmin = 0;
    static int yjmax = 0;

    public static ArrayList<Objeto> liangstart(Point old_point, Point current_point, ArrayList<Objeto> objetos) {
        Liang.objetosliang = objetos;
        for (Objeto o : objetos) {
            System.out.println("[START] " + o.getNome());
        }
        liangpoints(old_point, current_point);
        liang(old_point, current_point);

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

    // checks if an object should be clipped or not (aka checks if its inside the
    // area or not)
    public static boolean clipset(double p, double q) {
        double r = q / p;

        if (p < 0) {
            if (r > 1) {
                return false;
            } else if (r > u1) {
                u1 = r;
            }
        } else if (p > 0) {
            if (r < 0) {
                return false;
            } else if (r < u2) {
                u2 = r;
            }
        } else if (q < 0) {
            return false;
        }
        return true;
    }

    // generalizes the rectangle into 4 lines
    public static void retang_to_reta(ArrayList<Point> pr, int x) {

        if (x == 1) {
            x1 = pr.get(0).x;
            x2 = pr.get(2).x;
            y1 = pr.get(0).y;
            y2 = pr.get(2).y;

            dx = x2 - x1;
            dy = y2 - y1;
        } else if (x == 2) {
            x1 = pr.get(2).x;
            x2 = pr.get(1).x;
            y1 = pr.get(2).y;
            y2 = pr.get(1).y;

            dx = x2 - x1;
            dy = y2 - y1;

        } else if (x == 3) { // certo
            x1 = pr.get(0).x;
            x2 = pr.get(3).x;
            y1 = pr.get(0).y;
            y2 = pr.get(3).y;

            dx = x2 - x1;
            dy = y2 - y1;

        } else if (x == 4) {// reta da direita q ta bugando
            x1 = pr.get(1).x;
            x2 = pr.get(3).x;
            y1 = pr.get(1).y;
            y2 = pr.get(3).y;

            dx = x2 - x1;
            dy = y2 - y1;

        }

    }

    // checks if each line should be clipped and sets the new points
    public static void liangclipset() {
        System.out.println(
                "[DEBUG] liangclipset [xjmin] " + xjmin + "[xjmax] " + xjmax + "[yjmin] " + yjmin + "[yjmax] " + yjmax);
        String c = "reta";
        if (clipset(-dx, x1 - xjmin)) {
            if (clipset(dx, xjmax - x1)) {
                if (clipset(-dy, y1 - yjmin)) {
                    if (clipset(dy, yjmax - y1)) {
                        if (u2 < 1.0) {
                            x2 = x1 + u2 * dx;
                            y2 = y1 + u2 * dy;
                        }
                        if (u1 > 0.0) {
                            x1 = x1 + u1 * dx;
                            y1 = y1 + u1 * dy;
                        }
                        ArrayList<Point> pontos_recorte = new ArrayList<Point>();
                        Point p1_recorte = new Point(Math.round((float) x1), (int) Math.round(y1));
                        Point p2_recorte = new Point(Math.round((float) x2), (int) Math.round(y2));
                        String nome = " Reta recortada ";
                        pontos_recorte.add(p1_recorte);
                        pontos_recorte.add(p2_recorte);

                        saveAdd_recorte(pontos_recorte, c, nome);

                    }
                }
            }
        }
    }

    // sets up the clipping area
    public static void liangpoints(Point p1, Point p2) {
        xjmin = 0;
        xjmax = 0;
        yjmin = 0;
        yjmax = 0;

        if (p1.x > p2.x) {
            xjmin = p2.x;
            xjmax = p1.x;
        } else {
            xjmin = p1.x;
            xjmax = p2.x;
        }

        if (p1.y > p2.y) {
            yjmin = p2.y;
            yjmax = p1.y;
        } else {
            yjmin = p1.y;
            yjmax = p2.y;
        }

    }

    // handles each object and sets them up to be clipped
    public static void liang(Point p1, Point p2) {

        for (Objeto o : objetosliang) {
            System.out.println("[Liang]" + o.getNome());
        }
        String c = "reta";

        for (int i = 0; i < objetosliang.size(); i++) {
            u1 = 0;
            u2 = 1;
            if (objetosliang.get(i).getNome().contains("Reta ")) {
                x1 = objetosliang.get(i).getPontos().get(0).x;
                x2 = objetosliang.get(i).getPontos().get(1).x;
                y1 = objetosliang.get(i).getPontos().get(0).y;
                y2 = objetosliang.get(i).getPontos().get(1).y;
                dx = x2 - x1;
                dy = y2 - y1;

                liangclipset();

            } else if (objetosliang.get(i).getNome().contains("Retan")) {
                Point pr1 = objetosliang.get(i).getPontos().get(0);
                Point pr2 = objetosliang.get(i).getPontos().get(1);
                Point pr3 = objetosliang.get(i).getPontos().get(2);
                Point pr4 = objetosliang.get(i).getPontos().get(3);

                System.out.println("[PR1] " + pr1);
                System.out.println("[PR2] " + pr2);
                System.out.println("[PR3] " + pr3);
                System.out.println("[PR4] " + pr4);

                retang_to_reta(objetosliang.get(i).getPontos(), 1);
                liangclipset();
                retang_to_reta(objetosliang.get(i).getPontos(), 2);
                liangclipset();
                retang_to_reta(objetosliang.get(i).getPontos(), 3);
                liangclipset();
                retang_to_reta(objetosliang.get(i).getPontos(), 4);// reta direita q ta bugada
                liangclipset();
            }
        }
        checkObs_recorte();
    }

}
