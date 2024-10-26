package View;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu {

    private JFrame frame;
    private int height = 400, width = 400;
    private String logo = "/img/logou.png", back = "/img/back.png";
    private JButton btnCrearCita, btnVerCitas;
    private Image backgroundImage;

    public Menu() {
        initialize();
    }

    private void initialize() {
        setFrame(new JFrame());
        getFrame().setBounds(100, 100, width, height);
        getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setTitle("Heurıstica golosa");
        ImageIcon icon = new ImageIcon(getClass().getResource(logo));
        frame.setIconImage(icon.getImage());

        JPanel panelConFondo = new JPanel() {
            private Image backgroundImage = new ImageIcon(getClass().getResource(back)).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        frame.setContentPane(panelConFondo);
        panelConFondo.setLayout(null);

        btnCrearCita = new JButton("Nueva oferta");
        btnCrearCita.setBounds(118, 258, 147, 23);
        panelConFondo.add(btnCrearCita);

        btnVerCitas = new JButton("Ver ofertas");
        btnVerCitas.setBounds(118, 300, 147, 23);
        panelConFondo.add(btnVerCitas);
    }

    public void addCrearCitaListener(ActionListener listener) {
        btnCrearCita.addActionListener(listener);
    }

    public void addMoreAppointmentsListener(ActionListener listener) {
        btnVerCitas.addActionListener(listener);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    
    public void show() {
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }
}
