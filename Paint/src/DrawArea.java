
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DrawArea extends JComponent implements MouseInputListener {

    ArrayList<Objeto> objetos = new ArrayList<Objeto>();
    // Image in which we're going to draw
    private Image image;
    // Graphics2D object ==> used to draw on
    private Graphics2D g2;
    private Point current_point = null, old_point = null;
    boolean clicked = false;
    private String current_state;

    //for object naming
    private static int num_objetos;


    public DrawArea() {
        setDoubleBuffered(false);
        addMouseListener(this);
        addMouseMotionListener(this);
        num_objetos = 0;
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
        objetos.clear();
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

    /*public void open() {

        try ( BufferedReader br = Files.newBufferedReader(Paths.get("filename.txt"))) {
            // read line by line
            String line;

            while ((line = br.readLine()) != null) {
                String[] splitedLine = line.split(":");
                String[] p1 = splitedLine[0].split(",");


            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }*/

    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("image.txt"));

        for (int i = 0; i < objetos.size(); i++) {
            writer.append(objetos.get(i).getP1().x + ","
                    + objetos.get(i).getP1().y + ":"
                    + objetos.get(i).getP2().x + ","
                    + objetos.get(i).getP2().y + ":"
                    + objetos.get(i).getOp() + "\n");
        }
        writer.close();
    }

    public void saveAdd(Point p1, Point p2, String c, String tipo) {
        String nome = tipo + " " + ++num_objetos;
        Objeto d = new Objeto(p1, p2, c, nome);
        objetos.add(d);
        System.out.println("Salvo objeto " + nome + " do tipo " + c  
            + " com ponto inicial " + p1.x + " " + p1.y 
            + " e ponto final " + p2.x + " " + p2.y);
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

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
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

    public void dot(MouseEvent e) {
        System.out.println("dot");
        current_point = new Point(e.getX(), e.getY());
        Point rand = new Point(-1, -1);
        saveAdd(current_point, rand, "dot", "Ponto");
        Implementation.dot(current_point, g2);
    }

    public void draw(MouseEvent e, String c) {
        if (clicked) {
            old_point = current_point;
            current_point = new Point(e.getX(), e.getY());
            if (c.equals("dda")) {
                saveAdd(old_point, current_point, c, "Reta");
                Implementation.dda(old_point, current_point, g2);
            } else if (c.equals("bresenham")) {
                saveAdd(old_point, current_point, c, "Reta");
                Implementation.bresenham(old_point, current_point, g2);
            } else if (c.equals("retangulo")) {
                saveAdd(old_point, current_point, c, "Retangulo");
                Implementation.ret(old_point, current_point, g2);
            } else if (c.equals("circ")) {
                saveAdd(old_point, current_point, c, "Circulo");
                int r = (int) Math.sqrt(Math.pow(old_point.x - current_point.x, 2) + Math.pow(old_point.y - current_point.y, 2));
                Implementation.circ(old_point, r, g2);
            }
            clicked = false;
        } else {
            current_point = new Point(e.getX(), e.getY());
            if (c.equals("circ") || c.equals("trans")) {/*nop*/
            } else {
                Implementation.dot(current_point, g2); // coloca ponto inicial
            }

            clicked = true;
        }
    }

    public Objeto selectObject(){
        //cria combobox com objetos
        int tam = this.objetos.size();
        String[] obs = new String[tam];
        
        for(int i = 0; i < tam; i++){
            obs[i] = objetos.get(i).getNome();
        }

        JComboBox<String> cb = new JComboBox<String>(obs);

        //cria jpanel para seleção do objeto
        JPanel selecao = new JPanel();
        selecao.add(new JLabel("Selecione o objeto que deseja transformar:"));
        selecao.add(cb);
        
        //retorna o objeto
        Objeto o = null;
        int result = JOptionPane.showConfirmDialog(null, selecao,
            "Selecionar Objeto", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            for(Objeto i : objetos){
                if(i.getNome().equals(cb.getSelectedItem())){
                    o = i;
                    System.out.println("Objeto selecionado: " + o.getNome());
                }
            }
        }
        return o;
        

    }

    public void trans() {
        Objeto o = selectObject();
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("x:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("y:"));
        myPanel.add(yField);

        int x = 0, y = 0;

        int result = JOptionPane.showConfirmDialog(null, myPanel,
           "Entre com os valores x e y de translação", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            x = Integer.parseInt(xField.getText());
            y = Integer.parseInt(yField.getText());
        }

        Implementation.trans(x, y, o, g2);

        //Image image2 = Implementation.trans(x, y, this.convertToPixels());
        //g2.drawImage(image2, 0, 0, null);
        repaint();
    }

    public void rot(MouseEvent e) {
    }

    ;
    public void escala() {
        double escala = Double.parseDouble(JOptionPane.showInputDialog("Selecione o valor de escala:"));

        Image image2 = Implementation.escala(escala, this.convertToPixels());
        g2.drawImage(image2, 0, 0, null);
        repaint();
    }

    ;
    public void reflex(MouseEvent e) {
    }

    ;
    public void cohen(MouseEvent e) {
    }

    ;
    public void flood(MouseEvent e) {
    }

    ;

    //metodos mouseListener
    public void mouseClicked(MouseEvent e) {
        switch (getState()) {
            case "dot":
                dot(e);
                break;
            case "retangulo":
            case "dda":  //Nao eh erro
            case "bresenham":
            case "circ":
            case "trans":
                draw(e, getState());
                break;
            case "rot":
                rot(e);
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
