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
public class Cohen
{

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


    public static ArrayList<Objeto> cohenstart(Point old_point, Point current_point, ArrayList<Objeto> objetos) {
        Cohen.objetosCohen = objetos;
        Cohen(old_point, current_point);
        return objs_recortado;
    }

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

    // Function to compute region code for a point(x, y)
    public static byte computeCode(double xobj, double yobj) {
        // initialized as being inside
        byte code = INSIDE;

        if (xobj < x_min) // to the left of rectangle
            code = LEFT;
        else if (xobj > x_max) // to the right of rectangle
            code |= RIGHT;
        if (yobj < y_min)
            code |= TOP;
        else if (yobj > y_max)
            code |= BOTTOM;

        return code;
    }

    public static void Cohen(Point p1, Point p2){
      String c = "reta";
      if(p1.x > p2.x) {
        x_max = p1.x;
        x_min = p2.x;
      } else {
        x_max = p2.x;
        x_min = p1.x;
      }
      if(p1.y > p2.y){
        y_max = p1.y;
        y_min = p2.y;
      } else {
        y_max = p2.y;
        y_min = p1.y;
      }

      for (int i = 0; i < objetosCohen.size(); i++) {
        if (objetosCohen.get(i).getNome().contains("Reta ")) {
          double x1 = objetosCohen.get(i).getPontos().get(0).x;
          double x2 = objetosCohen.get(i).getPontos().get(1).x;
          double y1 = objetosCohen.get(i).getPontos().get(0).y;
          double y2 = objetosCohen.get(i).getPontos().get(1).y;
          boolean aceite = false, feito = false;

          while(!feito){
            byte c1,c2;
            c1 = computeCode(x1, y1);
            c2 = computeCode(x2, y2);
            if ((c1 | c2) == INSIDE) {
                aceite = true;
                feito = true;
            } else if ((c1 & c2) != INSIDE) {
                feito = true;
             } else {

               double xint=0.0, yint=0.0;
               final byte cfora = (c1 != INSIDE)?c1:c2;

               if ((cfora & LEFT) != 0) {
                 xint = x_min;
                 yint = y1 + (y2-y1)*(x_min-x1)/(x2-x1);
               } else if ((cfora & RIGHT) != 0){
                 xint = x_max;
                 yint = y1 + (y2-y1)*(x_max-x1)/(x2-x1);
               } else if ((cfora & BOTTOM) != 0){
                  yint = y_max;
                  xint = x1 + (x2-x1)*(y_max-y1)/(y2-y1);
               } else if ((cfora & TOP) != 0){
                  yint = y_min;
                  xint = x1 + (x2-x1)*(y_min-y1)/(y2-y1);
               }

               if(c1==cfora){
                 x1=xint;
                 y1=yint;
               } else {
                 x2=xint;
                 y2=yint;
               }
             }
          }
          if(aceite){
            ArrayList<Point> pontos_recorte = new ArrayList<Point>();
            Point p1_recorte = new Point(Math.round((float) x1), (int) Math.round(y1));
            Point p2_recorte = new Point(Math.round((float) x2), (int) Math.round(y2));
            String nome = " Reta recortada ";
            pontos_recorte.add(p1_recorte);
            pontos_recorte.add(p2_recorte);

            saveAdd_recorte(pontos_recorte, c, nome);
          }
        }
      } // fim do for
    }

    public static void Cohena(Point p1, Point p2) {
        String c = "reta";
        if(p1.x > p2.x) {
          x_min = p2.x;
          x_max = p1.x;
        } else {
          x_min = p1.x;
          x_max = p2.x;
        }
        if(p1.y > p2.y){
          y_min = p2.y;
          y_max = p1.y;
        } else {
          y_min = p1.y;
          y_max = p2.y;
        }

        //System.out.println("[BIG INFO1] x_min,y_min:"+xmin+","+y_min);
        //System.out.println("[BIG INFO2] x_max,y_max:"+x_max+","+y_max);

        for (int i = 0; i < objetosCohen.size(); i++) {
            if (objetosCohen.get(i).getNome().contains("Reta ")) {
                double x1 = objetosCohen.get(i).getPontos().get(0).x;
                double x2 = objetosCohen.get(i).getPontos().get(1).x;
                double y1 = objetosCohen.get(i).getPontos().get(0).y;
                double y2 = objetosCohen.get(i).getPontos().get(1).y;


                // Initialize line as outside the rectangular window
               boolean accept = false;
               boolean feito = false;

               while (!feito) {
                 byte code1 = computeCode(x1, y1);
                 byte code2 = computeCode(x2, y2);
                   if ((code1 | code2) == INSIDE) {
                       // If both endpoints lie within rectangle
                       accept = true;
                       feito = true;
                   } else if ((code1 & code2) != INSIDE) {
                       // If both endpoints are outside rectangle,
                       // in same region
                       feito = true;
                    }
                    else {
                        double x, y;
                        final byte outCode = (code1 != INSIDE) ? code1 : code2;

                        /** Find intersection point;
                            using formulas y = y1 + slope * (x - x1),
                            x = x1 + (1 / slope) * (y - y1)*/
                        if ((outCode & TOP) != INSIDE) {
                            // point is above the clip rectangle
                            x = x1 + (x2 - x1) * (y_max - y1) / (y2 - y1);
                            y = y_max;
                            System.out.println("[DEBUG  140]    BOTTOM x: " + x +" y: "+y);
                        }
                        else if ((outCode & BOTTOM) != INSIDE) {
                            // point is below the rectangle
                            x = x1 + (x2 - x1) * (y_min - y1) / (y2 - y1);
                            y = y_min;
                            System.out.println("[DEBUG  146]    TOP x: " + x +" y: "+y);
                        }
                        else if ((outCode & RIGHT) != INSIDE) {
                            // point is to the right of rectangle
                            y = y1 + (y2 - y1) * (x_max - x1) / (x2 - x1);
                            x = x_max;
                            System.out.println("[DEBUG  152]    RIGHT x: " + x +" y: "+y);
                        }
                        else if ((outCode & LEFT) != INSIDE) {
                            // point is to the left of rectangle
                            y = y1 + (y2 - y1) * (x_min - x1) / (x2 - x1);
                            x = x_min;
                            System.out.println("LEFT x: " + x +" y: "+y);
                            System.out.println("[DEBUG  159]    LEFT x: " + x +" y: "+y);
                        }else{
                           x = 0;
                           y = 0;
                        }

                        /** Now intersection point x, y is found
                            We replace point outside rectangle
                            by intersection point */
                        if (outCode == code1) {
                            x1 = x;
                            y1 = y;
                            code1 = computeCode(x1, y1);
                        } else { // outcode == code2
                            x2 = x;
                            y2 = y;
                            code2 = computeCode(x2, y2);
                        }
                    }

                } // end while
                if(accept){
                  ArrayList<Point> pontos_recorte = new ArrayList<Point>();
                  Point p1_recorte = new Point(Math.round((float) x1), (int) Math.round(y1));
                  Point p2_recorte = new Point(Math.round((float) x2), (int) Math.round(y2));
                  String nome = " Reta recortada ";
                  pontos_recorte.add(p1_recorte);
                  pontos_recorte.add(p2_recorte);

                  saveAdd_recorte(pontos_recorte, c, nome);
                }
            }
         checkObs_recorte();
        }
    }
}
