
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class SwingPaint {

    JButton clearBtn, blackBtn, floodBtn, saveBtn, openBtn;
    JButton squareBtn, dotBtn, transBtn, rotBtn, escalaBtn, reflexBtn;
    JButton ddaBtn, bresenhamBtn, circBtn, cohenBtn, liangBtn;
    DrawArea drawArea;
    ActionListener actionListener = new ActionListener() {

        //add the buttons
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == saveBtn) {
                drawArea.setState("save");
            } else if (e.getSource() == openBtn) {
                drawArea.setState("open");
            } else if (e.getSource() == clearBtn) {
                drawArea.clear();
            } else if (e.getSource() == squareBtn) {
                drawArea.setState("square");
            } else if (e.getSource() == ddaBtn) {
                drawArea.setState("dda");
            } else if (e.getSource() == bresenhamBtn) {
                drawArea.setState("bresenham");
            } else if (e.getSource() == circBtn) {
                drawArea.setState("circ");
            } else if (e.getSource() == transBtn) {
                drawArea.setState("trans");
            } else if (e.getSource() == rotBtn) {
                drawArea.setState("rot");
            } else if (e.getSource() == escalaBtn) {
                drawArea.setState("escala");
            } else if (e.getSource() == reflexBtn) {
                drawArea.setState("reflex");
            } else if (e.getSource() == cohenBtn) {
                drawArea.setState("cohen");
            } else if (e.getSource() == floodBtn) {
                drawArea.setState("flood");
            } else if(e.getSource() == dotBtn){
                drawArea.setState("dot");
            }
        }
    };

    public static void main(String[] args) {
        new SwingPaint().show();
    }

    public void show() {
        // create main frame
        JFrame frame = new JFrame("Swing Paint");
        Container content = frame.getContentPane();
        // set layout on content pane
        content.setLayout(new BorderLayout());
        // create draw area
        drawArea = new DrawArea();

        // add to content pane
        content.add(drawArea, BorderLayout.CENTER);


        // create controls to apply colors and call clear feature
        JPanel controls = new JPanel();
        JPanel controls2 = new JPanel();

        controls2.setLayout(new BoxLayout(controls2, BoxLayout.Y_AXIS));

        //create panel
        //createPanel(controls);
        createPanel2(controls2);

        // add to content pane
        //content.add(controls, BorderLayout.NORTH);
        content.add(controls2, BorderLayout.WEST);


        frame.setSize(1600, 600);
        // can close frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // show the swing paint result
        frame.setVisible(true);

        // Now you can try our Swing Paint !!! Enjoy :D
    }

    private void createPanel(JPanel controls) {

        // create controls to apply colors
        /*saveBtn = new JButton("Cor1");
        saveBtn.addActionListener(actionListener);
        openBtn = new JButton("Cor2");
        openBtn.addActionListener(actionListener);

        // add to panel
        controls.add(saveBtn);
        controls.add(openBtn);*/
    }

    private void createPanel2(JPanel controls) {
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(actionListener);
        openBtn = new JButton("Open");
        openBtn.addActionListener(actionListener);
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(actionListener);
        dotBtn = new JButton("Ponto");
        dotBtn.addActionListener(actionListener);
        squareBtn = new JButton("Square");
        squareBtn.addActionListener(actionListener);
        ddaBtn = new JButton("DDA  ");
        ddaBtn.addActionListener(actionListener);
        bresenhamBtn = new JButton("Bresenham");
        bresenhamBtn.addActionListener(actionListener);
        circBtn = new JButton("Circunferência");
        circBtn.addActionListener(actionListener);
        transBtn = new JButton("Translacao");
        transBtn.addActionListener(actionListener);
        rotBtn = new JButton("Rotacao");
        rotBtn.addActionListener(actionListener);
        escalaBtn = new JButton("Escala");
        escalaBtn.addActionListener(actionListener);
        reflexBtn = new JButton("Reflexao");
        reflexBtn.addActionListener(actionListener);
        cohenBtn = new JButton(" Cohen");
        cohenBtn.addActionListener(actionListener);
        floodBtn = new JButton("Flood Fill");
        floodBtn.addActionListener(actionListener);

        controls.add(saveBtn);
        controls.add(openBtn);
        controls.add(clearBtn);
        controls.add(dotBtn);
        controls.add(squareBtn);
        controls.add(ddaBtn);
        controls.add(bresenhamBtn);
        controls.add(circBtn);
        controls.add(transBtn);
        controls.add(rotBtn);
        controls.add(escalaBtn);
        controls.add(reflexBtn);
        controls.add(cohenBtn);
        controls.add(floodBtn);

    }

}
