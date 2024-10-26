package View;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Model.Offer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

public class ShowOffers {

    private JFrame frame;
    private JPanel panelContenedor;
    private int height = 450, width = 450;
    private JButton btnVolver;
    private List<JButton> btnVerList = new ArrayList<>();
    private ArrayList<Offer> ofertas; 
    private String cad = "Fecha de oferta: ";
    private String fechaInscripcion;

    public ShowOffers() {
        this.ofertas = new ArrayList<>();
        initialize();
    }

    public void copyOfferes(List<Offer> inscripciones) {
        for (Offer ff : inscripciones) {
            this.ofertas.add(ff);
        }
        createTablesByDate();
        actualizarPantalla();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Visualización");

        panelContenedor = new JPanel();
        panelContenedor.setLayout(null); 

        JScrollPane scrollPane = new JScrollPane(panelContenedor);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 0, width, height); 

        frame.setContentPane(scrollPane);

        btnVolver = new JButton("Atras");
        btnVolver.setBounds(0, 25, 80, 14);
        panelContenedor.add(btnVolver);
    }

    public void addBackListener(ActionListener listener) {
        btnVolver.addActionListener(listener);
    }
    
    public List<Offer> getListOffertsByDate(String fecha){
    	return this.agruparOfertasPorFecha().get(fecha);
    }


    private void createTablesByDate() {
        if (ofertas.isEmpty()) {
            System.out.println("No hay ofertas disponibles para mostrar.");
            return;
        }

        Map<String, List<Offer>> ofertasPorFecha = agruparOfertasPorFecha();
        int yPosition = 80;
        JPanel contentPane = (JPanel) ((JScrollPane) frame.getContentPane()).getViewport().getView();

        for (String fecha : ofertasPorFecha.keySet()) {
            JLabel lblFecha = new JLabel(cad + fecha);
            lblFecha.setBounds(10, yPosition, 400, 20);
            contentPane.add(lblFecha);
            yPosition += 30;

            JButton btnVer = new JButton("Ver ganador/es");
            btnVer.setBounds(250, yPosition - 20, 150, 14);
            contentPane.add(btnVer);
            btnVerList.add(btnVer); // Agregar el botón a la lista
            this.setFechaInscricion(fecha);

            String[] columnas = {"Dni", "Argentino?", "Fecha", "Hora in", "Hora out", "Oferta"};
            Object[][] datos = generarDatosTabla(ofertasPorFecha.get(fecha));

            if (datos.length > 0) {
                JTable table = new JTable(datos, columnas);
                JScrollPane tableScrollPane = new JScrollPane(table);
                tableScrollPane.setBounds(10, yPosition, 400, 120);
                contentPane.add(tableScrollPane);
                yPosition += 140;
            }
        }

        contentPane.setPreferredSize(new java.awt.Dimension(width, yPosition + 50));
        actualizarPantalla();
    }

    public void addVerListeners(ActionListener listener) {
        for (JButton btn : btnVerList) {
            btn.addActionListener(listener);
        }
    }

	private void setFechaInscricion(String fecha) {
		this.fechaInscripcion=fecha;
	}
	
	public String getFechaInscricion() {
		return this.fechaInscripcion;
	}

	public void actualizarPantalla() {
        frame.revalidate(); // Revalidar el contenido del frame
        frame.repaint(); // Repaint para reflejar los cambios
    }

    
    private Map<String, List<Offer>> agruparOfertasPorFecha() {
        Map<String, List<Offer>> mapaOfertas = new HashMap<>();

        for (Offer oferta : ofertas) {
            String fechaInsc = oferta.getFechaInscripcion();
            mapaOfertas.computeIfAbsent(fechaInsc, k -> new ArrayList<>());
            mapaOfertas.get(fechaInsc).add(oferta);
        }

        return mapaOfertas;
    }

    private Object[][] generarDatosTabla(List<Offer> listaOfertas) {
        Object[][] datos = new Object[listaOfertas.size()][6];
        for (int i = 0; i < listaOfertas.size(); i++) {
            Offer oferta = listaOfertas.get(i);
            datos[i][0] = oferta.getDni();
            datos[i][1] = oferta.getNacionalidad();
            datos[i][2] = oferta.getFechaEvento();
            datos[i][3] = oferta.getIn();
            datos[i][4] = oferta.getOut();
            datos[i][5] = "$" + oferta.getDinero();
        }
        return datos;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void show() {
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }
}