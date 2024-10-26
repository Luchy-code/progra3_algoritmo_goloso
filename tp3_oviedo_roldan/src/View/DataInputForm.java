package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import java.awt.FlowLayout;

public class DataInputForm {

    private JFrame frame;
    private JSpinner timeSpinner, outTime, inTime;
    private JPanel panel;
    private int height = 400, width = 400;
    private JTextField dniLabel;
    private JComboBox<String> nacionalityComboBox;
    private SpinnerNumberModel money;
    private JButton btnSubmit,btnBack;
    private String hoy=this.getNewDate(0), manana=this.getNewDate(1);

    public DataInputForm() {
        initialize();
        inputData();
    }
    
    private JPanel initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Formulario");

        panel = new JPanel();
        panel.setBackground(new Color(175, 238, 238));
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);  // Usar null layout para control manual

        JLabel lblNewLabel = new JLabel("Introducir los datos necesarios:");
        lblNewLabel.setBounds(10, 60, 203, 19);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        panel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Zoom sala VIP - ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_1.setBounds(65, 13, 148, 14);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Al mejor postor");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
        lblNewLabel_2.setBounds(203, 8, 140, 26);
        panel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Aclaracion: estas ofertas son para mañana");
        lblNewLabel_3.setBounds(10, 83, 253, 14);
        panel.add(lblNewLabel_3);

        return panel;
    }
    
    private String getNewDate(int diasSumados) {
    	Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, diasSumados);
        java.util.Date fecha = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fecha);  // Retornar la fecha en cad
    }

    private void inputData() {
        JLabel lblNewLabel_4 = new JLabel("DNI: ");
        lblNewLabel_4.setBounds(10, 136, 46, 14);
        panel.add(lblNewLabel_4);
        
        dniLabel = new JTextField();
        dniLabel.setBounds(89, 133, 148, 20);
        panel.add(dniLabel);
        dniLabel.setColumns(1);

        JLabel lblNewLabel_5 = new JLabel("Nacionalidad:");
        lblNewLabel_5.setBounds(10, 169, 90, 14);
        panel.add(lblNewLabel_5);

        nacionalityComboBox = new JComboBox<>(new String[]{"Argentin@", "Extranjero"});
        nacionalityComboBox.setFont(new Font("Trebuchet MS", Font.ITALIC, 11));
        nacionalityComboBox.setBounds(89, 166, 148, 20);
        panel.add(nacionalityComboBox);

        JLabel lblNewLabel_6 = new JLabel("Fecha a reservar:");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel_6.setBounds(10, 108, 108, 14);
        panel.add(lblNewLabel_6);

        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();

        SpinnerDateModel dateModel = new SpinnerDateModel(currentTime, null, null, Calendar.HOUR_OF_DAY);
        inTime = new JSpinner(dateModel);
        inTime.setBounds(63, 197, 67, 30);

        JSpinner.DateEditor de_inTime = new JSpinner.DateEditor(inTime, "HH:mm");
        inTime.setEditor(de_inTime);
        panel.add(inTime);

        JLabel lblNewLabel_7 = new JLabel(manana);
        lblNewLabel_7.setBounds(113, 108, 218, 14);
        panel.add(lblNewLabel_7);

        JLabel lblNewLabel_8 = new JLabel("Desde:");
        lblNewLabel_8.setBounds(10, 205, 46, 14);
        panel.add(lblNewLabel_8);

        JLabel lblNewLabel_9 = new JLabel("hasta:");
        lblNewLabel_9.setBounds(143, 205, 46, 14);
        panel.add(lblNewLabel_9);

        calendar.add(Calendar.HOUR, 1);
        java.util.Date oneHourLater = calendar.getTime();

        SpinnerDateModel dateModel_1 = new SpinnerDateModel(oneHourLater, null, null, Calendar.HOUR_OF_DAY);
        outTime = new JSpinner(dateModel_1); // Usar la variable de instancia
        outTime.setBounds(184, 197, 67, 30);

        JSpinner.DateEditor de_outTime = new JSpinner.DateEditor(outTime, "HH:mm");
        outTime.setEditor(de_outTime);
        panel.add(outTime);

        JLabel lblNewLabel_10 = new JLabel("Como minimo una reserva de 1hr");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 9));
        lblNewLabel_10.setBounds(63, 232, 150, 14);
        panel.add(lblNewLabel_10);
        
        JLabel lblNewLabel_11 = new JLabel("Oferta: ");
        lblNewLabel_11.setBounds(10, 260, 46, 14);
        panel.add(lblNewLabel_11);

        // Crear un panel para el spinner de dinero, ubicado solo debajo del label "Oferta"
        JPanel panelMoney = new JPanel();
        panelMoney.setBounds(60, 260, 150, 50); // Posicionar el panel debajo del label de la oferta
        panelMoney.setBackground(new Color(175, 238, 238));
        panelMoney.setLayout(new FlowLayout());

        money = new SpinnerNumberModel(1000, 1000, 2000000, 1); // mínimo 1000, máximo 2.000.000
        JSpinner numberSpinner = new JSpinner(money);
        panelMoney.add(numberSpinner);

        panel.add(panelMoney);
        
        btnSubmit = new JButton("Subir");
        btnSubmit.setBackground(new Color(144, 238, 144));
        btnSubmit.setBounds(113, 310, 150, 30);
        panel.add(btnSubmit);
        
     // Cargar la imagen y escalarla
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/img/volver.png"));
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        
        btnBack = new JButton(scaledIcon);
        btnBack.setBounds(10, 13, 25, 25);
        panel.add(btnBack);
        
    } 

    public void addSubmitListener(ActionListener listener) {
        btnSubmit.addActionListener(listener);
    }
    
    public void addBackListener(ActionListener listener) {
        btnBack.addActionListener(listener);
    }


    public String getDni(String nac) {
    	String dniInput=dniLabel.getText();
        int largo = dniInput.length();
        if (nac.equals("Argentin@")) {
            if (largo < 7 || largo > 8) {
                this.Special_msm("El DNI para argentinos debe tener entre 7 y 8 dígitos.", "Error DNI");
            }
        }
        if (nac.equals("Extranjero")) {
            if (largo < 8 || largo > 10) {
                this.Special_msm("El DNI para extranjeros debe tener entre 8 y 10 dígitos.", "Error DNI");
            }
        }
        return dniInput;
    }
    
    public void Special_msm(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.WARNING_MESSAGE);
    }
    
    public String getFechaIns() {
    	return hoy; 
    }
    
    public String getFechaEvento() {
    	return manana; 
    }
    
    public String getInTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(inTime.getValue());
    }

    public String getOutTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(outTime.getValue());
    }

    public int getMoney() {
        return (Integer)money.getNumber();
    }

    public String getNacionality() {
        return (String) nacionalityComboBox.getSelectedItem();
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
