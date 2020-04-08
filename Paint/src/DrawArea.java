
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

public class DrawArea extends JComponent implements MouseInputListener {

    // Image in which we're going to draw
    private Image image;
    // Graphics2D object ==> used to draw on
    private Graphics2D g2;
    private Point current_point = null, old_point = null;
    boolean clicked = false;

    // Mouse coordinates
    private int currentX, currentY, oldX, oldY;
    private int mPressX, mPressY;
    private int mReleaseX, mReleaseY;
    private int mClickedX, mClickedY;
    private int mMovedX, mMovedY;
    private int mDragX, mDragY;
    private String current_state;

    public DrawArea() {
        setDoubleBuffered(false);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            // image to draw null ==> we create
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            // enable antialiasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear draw area
            clear();
        }

        g.drawImage(image, 0, 0, null);

        /*if(current_state == "dot" && point != null){
            g.setColor(getForeground());
            g.fillRect(point.x - 3, point.y - 3, 7, 7);
        }*/
    }

    // now we create exposed methods
    public void clear() {
        g2.setPaint(Color.white);
        // draw white on entire draw area to clear
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }

    public void black() {
        // apply red color on g2 context
        g2.setPaint(Color.black);
    }

    //implement methods using the mouse
    void setState(String state) {
        this.current_state = state;
    }

    String getState() {
        return this.current_state;
    }
    
    public void dot(MouseEvent e){
        System.out.println("dot");
        current_point = new Point(e.getX(), e.getY());
        Implementation.dot(current_point, g2);
    }
    
    public void line(MouseEvent e, String c){
        if (clicked) {
            old_point = current_point;
            current_point = new Point(e.getX(), e.getY());
            System.out.println("x: " + current_point.x + " y: " + current_point.y);
            if(c.equals("dda")){
               Implementation.dda(old_point, current_point, g2); 
            }
            else Implementation.bresenham(old_point, current_point, g2);
            clicked = false;
        } else {
            System.out.println(c);
            current_point = new Point(e.getX(), e.getY());
            System.out.println("x: " + current_point.x + " y: " + current_point.y);
            Implementation.dot(current_point, g2);
            clicked = true;
        }
    }

    //metodos mouseListener
    public void mouseClicked(MouseEvent e) {
        switch (getState()) {
            case "dot":
                dot(e);
                break;
            case "dda": 
            case "bresenham":
                line(e, getState());
                break;
        }
        repaint();

    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {

    }

}
