
import obj.Objeto;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import plots.*;
import transforms.*;
import clipping.*;

public class DrawArea extends JComponent implements MouseInputListener {

    // creates lists for the created and clipped objets
    ArrayList<Objeto> objetos;
    ArrayList<Objeto> objs_recortado;
    // Image in which we're going to draw
    private Image image;
    // Graphics2D object ==> used to draw on
    private Graphics2D g2;
    // current and old points, used for the plotting methods
    private Point current_point = null, old_point = null;
    boolean clicked = false;
    // tells the app which plotting method it should use
    private String current_state;

    // object counter
    private static int num_objetos;
    private static int num_objetos_recortado;

    // constructor for the canvas, adds the mouse listener and initiates the object
    // lists
    public DrawArea() {
        setDoubleBuffered(false);
        addMouseListener(this);
        addMouseMotionListener(this);
        num_objetos = 0;
        objetos = new ArrayList<Objeto>();
        num_objetos_recortado = 0;
        objs_recortado = new ArrayList<Objeto>();
    }

    // paints the canvas whenever repaint() is called
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

        /*
         * if(current_state == "dot" && point != null){ g.setColor(getForeground());
         * g.fillRect(point.x - 3, point.y - 3, 7, 7); }
         */
    }

    // clear the object lists and the canvas
    public void clear() {
        objetos.clear();
        objs_recortado.clear();
        num_objetos = 0;
        num_objetos_recortado = 0;
        clearCanvas();
    }

    // repaints white canvas
    public void clearCanvas() {
        g2.setPaint(Color.white);
        // draw white on entire draw area to clear
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }

    // changes colour to red
    public void black() {
        g2.setPaint(Color.black);
    }

    // changes app state (tells it which functionality it should execute)
    void setState(String state) {
        this.current_state = state;
    }

    // returns current state
    String getState() {
        return this.current_state;
    }

    // opens the last saved image
    public void open() {
        clear();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("image.txt"))) {
            String line;

            /*
             * the app saves the images in a text file format this reads each line and
             * recreates the objects according to each line
             */

            while ((line = br.readLine()) != null) {
                ArrayList<Point> cur = new ArrayList<Point>();
                System.out.println(line);
                String[] splittedLine = line.split(":");
                String op = "", nome = "";

                for (int i = 0; i < splittedLine.length; i++) {
                    if (splittedLine.length == 4) { // in case its an object with 2 control points
                        for (int j = 0; j < 2; j++) {
                            String[] point = splittedLine[i].split(",");
                            cur.add(new Point(Integer.parseInt(point[0]), Integer.parseInt(point[1])));
                            i++;
                        }
                    } else { // in case its an object with 4 control points
                        for (int j = 0; j < 4; j++) {
                            String[] point = splittedLine[i].split(",");
                            cur.add(new Point(Integer.parseInt(point[0]), Integer.parseInt(point[1])));
                            i++;
                        }
                    }
                    op = splittedLine[i++];
                    nome = splittedLine[i];
                }

                System.out.println("[INFO] Recuperando objeto " + nome + " do tipo " + op);
                saveAdd(cur, op, nome);
            }
            checkObs();
            redraw();

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    // saves the image in a text file format
    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("image.txt"));

        for (Objeto o : objetos) {
            ArrayList<Point> pontos = o.getPontos();
            for (Point p : pontos) {
                writer.append(p.x + "," + p.y + ":");
            }
            writer.append(o.getOp() + ":" + o.getNome() + "\n");
        }
        writer.close();
    }

    // adds the object to the object list
    public Objeto saveAdd(ArrayList<Point> p, String c, String tipo) {
        String nome = tipo + " " + ++num_objetos;
        Objeto d = new Objeto(p, c, nome);

        objetos.add(d);
        System.out.println("[INFO] Salvo objeto " + nome + " do tipo " + c + "com pontos: ");
        for (Point i : p) {
            System.out.println("p1: " + i.x + "e p2: " + i.y);
        }
        checkObs();

        return d;
    }

    // removes last added object
    public void undo() {
        if (objetos.size() > 0) {
            objetos.remove(objetos.size() - 1);
            redraw();
        } else {
            System.out.println("[ERROR] No objects to remove");
        }

    }

    // lists existing objects
    void checkObs() {
        System.out.println("[DATA] Objetos existentes:");
        for (Objeto o : objetos) {
            System.out.println(o.getNome() + " - operação: " + o.getOp() + " - pontos :");
            ArrayList<Point> pontos = o.getPontos();
            for (Point p : pontos) {
                System.out.println("x: " + p.x + " - y: " + p.y);
            }
        }
    }

    // plots a dot
    public void dot(MouseEvent e) {
        ArrayList<Point> pontos = new ArrayList<Point>();
        System.out.println("dot");
        current_point = new Point(e.getX(), e.getY());
        pontos.add(current_point);
        Objeto o = saveAdd(pontos, "dot", "Ponto");
        Ponto.plot(o, g2);
    }

    /*
     * handles all plotting functionality except dot checks for mouse clicks to get
     * the control points uses the program's state to decide what to plot
     * 
     * also handles object clipping the same way
     */
    public void draw(MouseEvent e, String c) {
        if (clicked) {
            ArrayList<Point> pontos = new ArrayList<Point>();

            old_point = current_point;
            current_point = new Point(e.getX(), e.getY());

            pontos.add(old_point);
            pontos.add(current_point);

            Objeto o = null;

            if (c.equals("dda")) { // plots a line using the DDA algorithm
                o = saveAdd(pontos, c, "Reta");
                DDA.plot(o, g2);
            } else if (c.equals("bresenham")) { // plots a line using the bresenham algorithm
                o = saveAdd(pontos, c, "Reta");
                Bresenham.plot(o, g2);
            } else if (c.equals("retangulo")) { // plots a rectangle using the DDA algorithm 4 times
                pontos.add(new Point(old_point.x, current_point.y));
                pontos.add(new Point(current_point.x, old_point.y));
                o = saveAdd(pontos, c, "Retangulo");
                Retangulo.plot(o, g2);
            } else if (c.equals("circ")) { // plots a circle using the bresenham algorithm
                o = saveAdd(pontos, c, "Circulo");
                int r = (int) Math
                        .sqrt(Math.pow(old_point.x - current_point.x, 2) + Math.pow(old_point.y - current_point.y, 2));
                Circulo.plot(o, g2);
            } else if (c.equals("liang")) { // this creates the clipping area for object clipping using the Liang-Barsky
                                            // method
                System.out.println("[DEBUG]   Liang call with [x1]" + old_point.x + "[y1] " + old_point.y + "[x2]"
                        + current_point.x + "[y2]" + current_point.y);
                objs_recortado = Liang.liangstart(old_point, current_point, objetos);
                redraw_recortados();
            } else if (c.equals("cohen")) { // this creates the clipping area for object clipping using the
                                            // Cohen-Sutherland method
                System.out.println("[DEBUG]   Cohen call with [x1]" + old_point.x + "[y1] " + old_point.y + "[x2]"
                        + current_point.x + "[y2]" + current_point.y);
                objs_recortado = Cohen.cohenstart(old_point, current_point, objetos);
                redraw_recortados();
            } else if (c.equals("clipcirc")) { // this creates the clipping area for cicrle clipping
                Point p1 = new Point(old_point.x, current_point.y);
                Point p2 = new Point(current_point.x, old_point.y);
                ClipCirc.clipcircstart(old_point, current_point, p1, p2, g2, image);
            }
            clicked = false;
        } else {
            current_point = new Point(e.getX(), e.getY());
            clicked = true;
        }
    }

    /*
     * handles object re-drawing (after transformation or after opening a file)
     */
    public void draw(Objeto o) {
        String op = o.getOp();
        System.out.println("Desenhando objeto " + o.getNome() + " operacao " + op);
        switch (op) {
            case "dot":
                Ponto.plot(o, g2);
                break;
            case "retangulo":
                Retangulo.plot(o, g2);
                break;
            case "dda":
                DDA.plot(o, g2);
                break;
            case "bresenham":
                Bresenham.plot(o, g2);
                break;
            case "circ":
                Circulo.plot(o, g2);
                break;
            case "reta":
                DDA.plot(o, g2);
                break;
        }
    }

    // redraws the object
    public void redraw() {
        clearCanvas();
        for (Objeto o : objetos) {
            this.draw(o);
        }
        repaint();
    }

    // redraws the clipped object
    public void redraw_recortados() {
        clearCanvas();
        for (Objeto o : objs_recortado) {
            this.draw(o);
        }
        repaint();
    }

    // handles object selection
    public Objeto selectObject() {
        // cria combobox com objetos
        int tam = this.objetos.size();
        String[] obs = new String[tam];

        for (int i = 0; i < tam; i++) {
            obs[i] = objetos.get(i).getNome();
        }

        JComboBox<String> cb = new JComboBox<String>(obs);

        // cria jpanel para seleção do objeto
        JPanel selecao = new JPanel();
        selecao.add(new JLabel("Selecione o objeto que deseja transformar:"));
        selecao.add(cb);

        // retorna o objeto
        Objeto o = null;
        int result = JOptionPane.showConfirmDialog(null, selecao, "Selecionar Objeto", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            for (Objeto i : objetos) {
                if (i.getNome().equals(cb.getSelectedItem())) {
                    o = i;
                }
            }
        }
        return o;
    }

    // handles object translation
    public void trans() {
        Objeto o = selectObject();
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);

        // prompts the user to select how many pixels X and Y they want to translate the
        // object
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("x:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("y:"));
        myPanel.add(yField);

        int x = 0, y = 0;

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Entre com os valores x e y de translação",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            x = Integer.parseInt(xField.getText());
            y = Integer.parseInt(yField.getText());
        }

        // translates the object
        o = Translacao.translate(x, y, o);

        // finds the translated object
        for (int i = 0; i < objetos.size(); i++) {
            if (objetos.get(i).getNome().equals(o.getNome())) {
                objetos.set(i, o);
            }
        }
        // and redraws it
        redraw();
    }

    // handles object scaling
    public void escala() {
        Objeto o = selectObject();
        double escala = Double.parseDouble(JOptionPane.showInputDialog("Selecione o valor de escala:"));

        int x_original, y_original;
        x_original = o.getPontos().get(0).x;
        y_original = o.getPontos().get(0).y;

        /*
         * translates the object to the origin point scales it translates it back to its
         * original position
         */

        o = Translacao.translate(-x_original, -y_original, o);
        o = Escala.scale(escala, o);
        o = Translacao.translate(x_original, y_original, o);

        // finds the scaled object
        for (int i = 0; i < objetos.size(); i++) {
            if (objetos.get(i).getNome().equals(o.getNome())) {
                objetos.set(i, o);
            }
        }
        // and redraws it
        redraw();
    }

    public void rotate() {
        Objeto o = selectObject();
        double deg = Double.parseDouble(JOptionPane.showInputDialog("Selecione o grau para rotacionar:"));

        int x_original, y_original;
        x_original = o.getPontos().get(0).x;
        y_original = o.getPontos().get(0).y;

        /*
         * translates the object to the origin point rotates it translates it back to
         * its original position
         */

        o = Translacao.translate(-x_original, -y_original, o);
        o = Rotacao.rotate(deg, o);
        o = Translacao.translate(x_original, y_original, o);

        // finds the rotated object
        for (int i = 0; i < objetos.size(); i++) {
            if (objetos.get(i).getNome().equals(o.getNome())) {
                objetos.set(i, o);
            }
        }
        // and redraws it
        redraw();
    }

    public void reflect() {
        Objeto o = selectObject();
        String[] options = { "x", "y", "xy" };
        JPanel frame = new JPanel();
        int op = JOptionPane.showOptionDialog(null, "Escolha o eixo de reflexão", "Reflexão", JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, "x");
        System.out.println(op);

        int x_original, y_original;
        x_original = o.getPontos().get(0).x;
        y_original = o.getPontos().get(0).y;

        /*
         * translates the object to the origin point reflects it (this is a
         * generalization of the rotation method) translates it back to its original
         * position
         */

        o = Translacao.translate(-x_original, -y_original, o);
        o = Rotacao.reflect(op, o);
        o = Translacao.translate(x_original, y_original, o);

        for (int i = 0; i < objetos.size(); i++) {
            if (objetos.get(i).getNome().equals(o.getNome())) {
                objetos.set(i, o);
            }
        }

        redraw();
    }

    // handles flood fill method
    public void flood(MouseEvent e) {
        current_point = new Point(e.getX(), e.getY());
        int cor_preenche = -16777215; // preto
        int cor_antiga = -1; // branco
        Floodfill.plot(current_point, g2, image, cor_preenche, cor_antiga);
        repaint();
    }

    // sets up the mouse listener, only really uses the mouseClicked event
    public void mouseClicked(MouseEvent e) {
        switch (getState()) {
            case "dot":
                dot(e);
                break;
            case "retangulo":
            case "dda": // Nao eh erro
            case "bresenham":
            case "circ":
            case "liang":
            case "cohen":
            case "clipcirc":
                draw(e, getState());
                break;
            case "flood":
                flood(e);
                break;
        }
        repaint();

    }

    // unused mouseListener events
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
