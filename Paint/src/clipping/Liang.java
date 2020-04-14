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

    public static ArrayList<Objeto> objs_recotardo = new ArrayList<Objeto>();

    public static ArrayList<Objeto> objetosliang;
    static double u1 = 0, u2 = 1.0;

    public static ArrayList<Objeto> liangstart(Point old_point, Point current_point, ArrayList<Objeto> objetos) {
        Liang.objetosliang = objetos;
        liang(old_point, current_point);
        return objs_recotardo;
    }

    public static void checkObs_recorte() {
        System.out.println("[DATA] Objetos recortados existentes:");
        for (Objeto o : objs_recotardo) {
            System.out.println(o.getNome() + " - operação: " + o.getOp() + " - pontos :");
            ArrayList<Point> pontos = o.getPontos();
            for (Point p : pontos) {
                System.out.println("x: " + p.x + " - y: " + p.y);
            }
        }
    }

    public static Objeto saveAdd_recorte(ArrayList<Point> p, String c, String tipo) {
        String nome = tipo + " ";
        Objeto d = new Objeto(p, c, nome);

        objs_recotardo.add(d);
        System.out.println("[INFO] Salvo objeto recortado " + nome + " do tipo " + c + "com pontos: ");
        for (Point i : p) {
            System.out.println("p1: " + i.x + "e p2: " + i.y);
        }
        checkObs_recorte();

        return d;
    }

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

    public static void liang(Point p1, Point p2) {
        String c = "reta";
        int xjmin = p1.x;
        int xjmax = p2.x;
        int yjmin = p1.y;
        int yjmax = p2.y;
        for (int i = 0; i < objetosliang.size(); i++) {
            u1 = 0;
            u2 = 1;
            if (objetosliang.get(i).getNome().contains("Reta ")) {
                double x1 = objetosliang.get(i).getPontos().get(0).x;
                double x2 = objetosliang.get(i).getPontos().get(1).x;
                double y1 = objetosliang.get(i).getPontos().get(0).y;
                double y2 = objetosliang.get(i).getPontos().get(1).y;
                double dx = x2 - x1;
                double dy = y2 - y1;
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
        }
        checkObs_recorte();
    }

}
