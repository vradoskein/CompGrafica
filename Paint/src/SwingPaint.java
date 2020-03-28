
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SwingPaint {

    JButton clearBtn, blackBtn, floodBtn, saveBtn, openBtn;
    JButton squareBtn, transBtn, rotBtn, escalaBtn, reflexBtn;
    JButton ddaBtn, bresenhamBtn, circBtn, cohenBtn, liangBtn;
    DrawArea drawArea;
    ActionListener actionListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearBtn) {
                drawArea.clear();
            } else if (e.getSource() == blackBtn) {
                drawArea.black();
            } else if (e.getSource() == squareBtn) {
                drawArea.square();
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

        //create panel
        createPanel(controls);

        // add to content pane
        content.add(controls, BorderLayout.NORTH);

        frame.setSize(1600, 600);
        // can close frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // show the swing paint result
        frame.setVisible(true);

        // Now you can try our Swing Paint !!! Enjoy :D
    }

    private void createPanel(JPanel controls) {
        
        // create controls to apply colors and call clear feature
        saveBtn = new JButton("Save");
        saveBtn.addActionListener(actionListener);
        openBtn = new JButton("Open");
        openBtn.addActionListener(actionListener);
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(actionListener);
        blackBtn = new JButton("Black");
        blackBtn.addActionListener(actionListener);
        squareBtn = new JButton("retângulo");
        squareBtn.addActionListener(actionListener);
        ddaBtn = new JButton("DDA");
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
        cohenBtn = new JButton(" Cohen-Sutherland");
        cohenBtn.addActionListener(actionListener);
        floodBtn = new JButton("Flood Fill");
        floodBtn.addActionListener(actionListener);
        
        
        
        
        // add to panel
        controls.add(saveBtn);
        controls.add(openBtn);
        controls.add(clearBtn);
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
