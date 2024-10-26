package Model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GreedyAlgorithm {
    
    private List<Offer> selectedOffers;
    private int totRecaudado;
    private String fechaIns;
    
    public GreedyAlgorithm() {
        this.totRecaudado = 0;
        this.selectedOffers = new ArrayList<Offer>();
        this.fechaIns = "";
    }
    
    public void setFecha(String fecha) {
        this.fechaIns = fecha;
    }
    
    public void setList(List<Offer> selected) {
        if (selected.isEmpty()) {
            System.out.println("Está vacío");
        }    
        this.selectedOffers = selected;
    }
    
    public int getMaxOFerta(){
    	int x=0;
    	for (Offer ff: selectedOffers) {
    		if(ff.getDinero()>x) {
    			x=ff.getDinero();
    		}
    	}
    	return x;
    }
    
    public int getMinOFerta(){
    	int x=getMaxOFerta();
    	for (Offer ff: selectedOffers) {
    		if(ff.getDinero()<x) {
    			x=ff.getDinero();
    		}
    	}
    	return x;
    }
    
    public List<Offer> selectBestOffers() {
        // Ordenar las ofertas por monto de mayor a menor
        Collections.sort(selectedOffers, Comparator.comparingInt(Offer::getDinero).reversed());

        List<Offer> candidatos = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        int maximaOFerta = getMaxOFerta();
        int minimaOFerta = getMinOFerta();

        for (Offer ff : selectedOffers) {
            LocalTime horaIn = LocalTime.parse(ff.getIn(), timeFormatter);
            LocalTime horaOut = LocalTime.parse(ff.getOut(), timeFormatter);

            if (ff.getDinero() <= maximaOFerta && ff.getDinero() > minimaOFerta) { 
                if (verificationAgenda(candidatos, ff)) {
                    candidatos.add(ff); 
                    totRecaudado += ff.getDinero();
                }
            }
        }
        
        return candidatos;
    }

    private boolean verificationAgenda(List<Offer> candidatos, Offer oferta) {
        LocalTime ofertaTimeIn = toDate(oferta.getIn());
        LocalTime ofertaTimeOut = toDate(oferta.getOut());

        for (Offer ff : candidatos) {
            LocalTime candTimeIn = toDate(ff.getIn());
            LocalTime candTimeOut = toDate(ff.getOut());

            // Verifica que las horas no se superpongan, permitiendo que los tiempos coincidan
            if (!(ofertaTimeOut.isBefore(candTimeIn) || ofertaTimeIn.isAfter(candTimeOut) || ofertaTimeIn.equals(candTimeOut))) {
                return false; 
            }
        }
        return true;
    }


    private LocalTime toDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = null;
        try {
            time = LocalTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Error al convertir la cadena a hora: " + e.getMessage());
        }
        return time;
    }


    public int getTotal() {
        return this.totRecaudado;
    }
}
