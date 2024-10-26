package Presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;

import View.ShowWinnersOffers;
import Model.GreedyAlgorithm;
import Model.Offer;
import View.DataInputForm;
import View.Menu;
import View.ShowOffers;
import ConexionBD.Conexion;

public class Presenter {
    private Menu menuView;
    private DataInputForm di;
    private ShowOffers so;
    private Conexion bdd;
    private ShowWinnersOffers sw;
    private GreedyAlgorithm ga;

    public Presenter(Menu menu, DataInputForm Datai, ShowOffers soff, Conexion bd,ShowWinnersOffers swo, GreedyAlgorithm gam) {
        this.menuView = menu; 
        this.di=Datai;
        this.so=soff;
        this.bdd=bd;
        this.sw=swo;
        this.ga=gam;
        
        accionarNewAppointment();
        accionarSeeAppointment();
        accionarVolverMenu();
        accionarVolverMenuOFertas(); //desde ofertas
        accionarVolverMenuWinners(); //desde los ganadores
        updateRegisters();
        accionarNewOffer();
        accionarVerWinners();
    }

    private void accionarNewAppointment() {
        this.menuView.addCrearCitaListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuView.close();
                di.show();            }
        });
    }

    private void accionarSeeAppointment() {
        this.menuView.addMoreAppointmentsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuView.close();
                so.show();
                updateRegisters(); 
            }
        });
    }
    
    private void accionarVolverMenu() {
        this.di.addBackListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                di.close();
                menuView.show();
            }
        });
    }
    
    private void accionarVolverMenuOFertas() {
        this.so.addBackListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                so.close();
                menuView.show();
            }
        });
    }
    
    private void accionarVolverMenuWinners() {
        this.sw.addBackListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                sw.close();
                menuView.show();
            }
        });
    }
    
    private void updateRegisters() {
        try {
			so.copyOfferes(bdd.getAllInscripciones());
			so.actualizarPantalla();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    }
    
    private void accionarNewOffer() {
        this.di.addSubmitListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
            	saveOfferDB(creacteOffer());
                updateRegisters();
                menuView.close();
                so.show();
        	}
        });
    }
    
    private void saveOfferDB(Offer ff) {
        String nacionalidad = ff.getNacionalidad();
        String dni = ff.getDni();
        String in = ff.getIn();
        String out = ff.getOut();
        String fechaInscripcion = ff.getFechaInscripcion();
        String fechaEvento = ff.getFechaEvento();
        int cash = ff.getDinero();
        try {
            if (!bdd.existeOferta(dni, fechaInscripcion, fechaEvento, in, out, cash)) {
                bdd.insertDataIntoDatabase(dni, nacionalidad, in, out, cash, fechaInscripcion, fechaEvento);
                updateRegisters(); 
            } else {
                di.Special_msm("Esta oferta ya existe", "Oferta Duplicada");
            }
        } catch (SQLException ex) {
            di.Special_msm("Error al conectar a la base de datos.", "Mala Conexión");
            ex.printStackTrace();
        }
    }

    
    private Offer creacteOffer() {  //directamente del formulario
        String nacionalidad = di.getNacionality();
        String dni = di.getDni(nacionalidad);
        String in = di.getInTime();
        String out = di.getOutTime();
        String fechaInscripcion = di.getFechaIns();
        String fechaEvento = di.getFechaEvento();
        int cash = di.getMoney();
        Offer of = null;
        
        // Verificar si los datos son válidos antes de guardar
        if (!dni.isEmpty() && cash >=1000 && verificationTime(in,out)) {
            of=new Offer();
            of.collecting(dni, nacionalidad, in, out, cash, fechaInscripcion, fechaEvento);
            di.close(); 
        } else {
            di.Special_msm("Por favor, verifica los datos ingresados.", "Error de Validación");
        }
        
        return of;
    }
    
    private boolean verificationTime(String in, String out) {
    	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime inTime = LocalTime.parse(in, timeFormatter);
        LocalTime outTime = LocalTime.parse(out, timeFormatter);
        Duration duration = Duration.between(inTime, outTime);
        return inTime.isBefore(outTime) && duration.toHours()>0;
    }

    private void accionarVerWinners() {
        this.so.addVerListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el botón que fue presionado
                JButton botonPresionado = (JButton) e.getSource();
                // Obtener la fecha asociada al botón
                String fecha = (String) botonPresionado.getClientProperty("fecha");
                ga.setFecha(fecha);
                so.close();
                sw.show();
                setWinners(fecha);
            }
        });
    }


   
    private void setWinners(String fecha) {
        sw.clearWinnersPanel(); // Limpiar componentes previos
        sw.setFechaInscripcion(fecha);
        int y = 50;
        List<Offer> lista = so.getListOffertsByDate(fecha);
        ga.setList(lista);

        List<Offer> listaGanadores = ga.selectBestOffers();
        sw.printRecaudado(ga.getTotal() + "");

        for (Offer ff : listaGanadores) {
            y += 30;
            StringBuilder cad = new StringBuilder();
            cad.append(" DNI:").append(ff.getDni())
               .append(", HORAS:").append(ff.getIn()).append("-").append(ff.getOut())
               .append(", $").append(ff.getDinero());
            sw.printWinners(y, cad);
        }
    }
    
    private void imprimir(List<Offer> list) {
    	for (Offer offer : list) {
            System.out.println(offer.toString());
        }
    	System.out.println("-------------------------------");
    }
    
}
