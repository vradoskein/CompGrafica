
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
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.awt.image.WritableRaster;

import java.lang.Math;  // sqrt

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

    public int[][] convertToPixels() {
      int width = this.image.getWidth(null);
      int height = this.image.getHeight(null);
      BufferedImage img = toBufferedImage(this.image);
      int[][] matrix = new int[height][width];

      for (int row = 0; row < height; row++) {
         for (int col = 0; col < width; col++) {
            matrix[row][col] = img.getRGB(col, row);
         }
      }

      return matrix;
    }

    public static BufferedImage toBufferedImage(Image img){
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
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
            } else if(c.equals("bresenham")){
                Implementation.bresenham(old_point, current_point, g2);
            } else if (c.equals("square")){
                Implementation.square(old_point, current_point, g2);
            } else if (c.equals("circ")){
                int r = (int) Math.sqrt( Math.pow(old_point.x - current_point.x ,2) + Math.pow(old_point.y - current_point.y ,2) );
                Implementation.circ(old_point, r , g2);
            }
            clicked = false;
        } else {
            System.out.println(c);
            current_point = new Point(e.getX(), e.getY());
            System.out.println("x: " + current_point.x + " y: " + current_point.y);
            if(!c.equals("circ"))Implementation.dot(current_point, g2); // coloca ponto inicial
            clicked = true;
        }
    }

    public void trans(){
        Image image2 = Implementation.trans(20, 25, this.convertToPixels());
        g2.drawImage(image2, 0, 0, null);
        repaint();
    }

    public void rot(MouseEvent e){};
    public void escala(MouseEvent e){};
    public void reflex(MouseEvent e){};
    public void cohen(MouseEvent e){};
    public void flood(MouseEvent e){};

    //metodos mouseListener
    public void mouseClicked(MouseEvent e) {
        switch (getState()) {
            case "dot":
                dot(e);
                break;
            case "square":
            case "dda":  //Nao eh erro
            case "bresenham":
            case "circ":
                line(e, getState());
                break;
            case "rot":
                rot(e);
                break;
            case "escala":
                escala(e);
                break;
            case "reflex":
                reflex(e);
                break;
            case "cohen":
                cohen(e);
                break;
            case "flood":
                flood(e);
                break;
        }
        repaint();

    }

    //
    public void mouseMoved(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}

}
