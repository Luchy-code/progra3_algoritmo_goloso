package Tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.GreedyAlgorithm;
import Model.Offer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class Test_Greedy {

	private GreedyAlgorithm algorithm;
    private List<Offer> offers;
    private String dni1="44584069", dni2="100584697", fechaIn="25/10/2024",fechaOut="26/10/2024", nac1="Argentin@", nac2="Extranjero";

    @BeforeEach
    public void setUp() {
        algorithm = new GreedyAlgorithm();
        offers = new ArrayList<>();
        
        //(String dni, String argentino, String in, String out, int dinero, String fechaInscripcion, String fechaEvento)
        // Crear instancias de Offer
        Offer ff=new Offer();
        ff.collecting(dni1,nac1,"08:00","10:00",1000,fechaIn,fechaOut);
        offers.add(ff);
        Offer ff1=new Offer();
        ff1.collecting(dni1,nac1,"09:00","11:00", 2000,fechaIn,fechaOut);
        offers.add(ff1);
        Offer ff2=new Offer();
        ff2.collecting(dni2,nac2,"09:00","11:00", 20000,fechaIn,fechaOut);
        offers.add(ff2);
        Offer ff3=new Offer();
        ff3.collecting(dni2,nac2,"13:00","15:00", 12010,fechaIn,fechaOut);
        offers.add(ff3);
        algorithm.setList(offers);
    }

    @Test
    public void testGetMaxOFerta() {
        int maxOferta = algorithm.getMaxOFerta();
        assertEquals(20000, maxOferta);
    }

    @Test
    public void testGetMinOFerta() {
        int minOferta = algorithm.getMinOFerta();
        assertEquals(1000, minOferta);
    }

    @Test
    public void testSelectBestOffers() {
        List<Offer> selectedOffers = algorithm.selectBestOffers();
        assertNotNull(selectedOffers, "La lista seleccionada no debe ser null.");
        
        // Verificar que las ofertas seleccionadas están dentro del rango esperado y sin solapamiento
        for (Offer offer : selectedOffers) {
            assertTrue(offer.getDinero() <= algorithm.getMaxOFerta(), "La oferta seleccionada debería ser menor o igual al máximo.");
            assertTrue(offer.getDinero() > algorithm.getMinOFerta(), "La oferta seleccionada debería ser mayor que el mínimo.");
        }
    }

    @Test
    public void testVerificationAgenda() {
        // Prueba de solapamiento y no solapamiento
    	 Offer ofertaNoSolapada=new Offer();
    	 ofertaNoSolapada.collecting(dni1,nac1,"15:30","17:00",1000,fechaIn,fechaOut);
         
         
         Offer ofertaSolapada=new Offer();
    	 ofertaSolapada.collecting(dni1,nac1,"09:30","10:30",1000,fechaIn,fechaOut);
         
         
        List<Offer> candidatos = new ArrayList<>();
        Offer ff=new Offer();
   	 	ff.collecting(dni1,nac1,"08:00", "09:00",1000,fechaIn,fechaOut);
   	 	candidatos.add(ff);  // No solapa
        assertTrue(algorithm.verificationAgenda(candidatos, ofertaNoSolapada), "Esta oferta no debería solaparse.");
        
        
        Offer ff1=new Offer();
   	 	ff1.collecting(dni1,nac1,"09:00", "11:00",1000,fechaIn,fechaOut);
   	 	candidatos.add(ff1);  // Solapa con ofertaSolapada
        assertFalse(algorithm.verificationAgenda(candidatos, ofertaSolapada), "Esta oferta debería solaparse.");
    }
    
    /*// Prueba de solapamiento y no solapamiento
        Offer ofertaNoSolapada = new Offer("333", "15:30", "17:00", 180);
        Offer ofertaSolapada = new Offer("444", "09:30", "10:30", 180);

        List<Offer> candidatos = new ArrayList<>();
        candidatos.add(new Offer("111", "08:00", "09:00", 150));  // No solapa
        assertTrue(algorithm.verificationAgenda(candidatos, ofertaNoSolapada), "Esta oferta no debería solaparse.");

        candidatos.add(new Offer("222", "09:00", "11:00", 150));  // Solapa con ofertaSolapada
        assertFalse(algorithm.verificationAgenda(candidatos, ofertaSolapada), "Esta oferta debería solaparse.");*/

    @Test
    public void testToDate() {
        LocalTime time = algorithm.toDate("08:30");
        assertNotNull(time, "El tiempo no debe ser null.");
        assertEquals(LocalTime.of(8, 30), time, "La conversión a LocalTime debería ser correcta.");
    }

    @Test
    public void testGetTotal() {
        algorithm.selectBestOffers();
        int total = algorithm.getTotal();
        assertTrue(total > 0, "El total recaudado debe ser mayor a cero después de seleccionar las mejores ofertas.");
    }
}
