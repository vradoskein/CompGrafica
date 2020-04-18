/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plots;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

/**
 *
 * @author vradoskein
 */
public class ParallelDDA extends Thread {

    Point p1, p2;
    Graphics2D g;

    public ParallelDDA(Point pinicial, Point pfinal, Graphics2D g) {
        this.p1 = pinicial;
        this.p2 = pfinal;
        this.g = g;

    }
    
    public Point getP1(){
        return this.p1;
    }
    public Point getP2(){
        return this.p2;
    }
    
    @Override
    public void run(){
        g.setStroke(new BasicStroke(5));
        g.setPaint(Color.black);

        int dx = p2.x - p1.x;
        int dy = p2.y - p1.y;

        // calculate steps required for generating pixels
        int steps = Math.abs(dx) > Math.abs(dy) ? Math.abs(dx) : Math.abs(dy);

        // calculate increment in x & y for each steps
        double Xinc = dx / (double) steps;
        double Yinc = dy / (double) steps;

        // Put pixel for each step
        double X = p1.x;
        double Y = p1.y;
        for (int i = 0; i <= steps; i++) {
            g.draw(new Line2D.Double(X, Y, X, Y));
            X += Xinc;
            Y += Yinc;
        }
    }
}
