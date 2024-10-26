package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class ShowWinnersOffers {

    private JFrame frame;
    private int width = 400, height = 400;
    private StringBuilder cad;
    private JButton btnBack;
    private JPanel panel;
    private JScrollPane scrollPane; // JScrollPane para el panel

    public ShowWinnersOffers() {
        this.cad = new StringBuilder();
        this.cad.append("Ganadores de la fecha ");
        initialize();
    }

    public void setFechaInscripcion(String fecha) {
        this.cad.append(fecha);
        setTitle();
    }
    
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Ofertas ganadoras");
        frame.setBounds(100, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        panel = new JPanel();
        panel.setBackground(new Color(178, 223, 218));
        panel.setLayout(null);

        // Crear JScrollPane para el panel
        scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(0, 0, 384, 361);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(scrollPane);

        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/volver.png"));
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        btnBack = new JButton(scaledIcon);
        btnBack.setBounds(0, 0, 30, 30);
        panel.add(btnBack);
    }
    
    private void setImage() {
        JPanel panel_img = new JPanel() {
            private Image imagenFondo;
            {
                imagenFondo = new ImageIcon(getClass().getResource("/img/troffeo.jpg")).getImage();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panel_img.setBounds(93, 211, 180, 150); // x,y,w,h
        panel.add(panel_img);
    }

    private void setTitle() {
        JLabel lblTit = new JLabel(cad.toString());
        lblTit.setBounds(10, 55, 364, 14);
        panel.add(lblTit);
    }

    public void printRecaudado(String monto) {
        JLabel lblm = new JLabel("Total: $" + monto);
        lblm.setBounds(250, 55, 400, 14);
        panel.add(lblm);
    }

    public void printWinners(int y, StringBuilder cad) {
        JLabel lbl = new JLabel(cad.toString());
        lbl.setBounds(10, y, 364, 14);
        panel.add(lbl);

        // Actualizar el tamaño del panel si se añaden más ofertas
        panel.setPreferredSize(new java.awt.Dimension(364, y + 30));
        panel.revalidate();
    }
    
    public void clearWinnersPanel() {
        panel.removeAll();
        panel.add(btnBack);
        cad.setLength(0); 
        cad.append("Ganadores de la fecha "); 
        setTitle();
        setImage();
        panel.setPreferredSize(new java.awt.Dimension(364, 400));
        panel.revalidate();
        panel.repaint();
    }



    public void addBackListener(ActionListener listener) {
        btnBack.addActionListener(listener);
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
