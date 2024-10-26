package Model;

public class Offer {
    private String dni, nacionalidad, in, out, fechaInscripcion, fechaEvento;
    private int dinero;
    
    public Offer() { }

    public void collecting (String dni, String nacionalidad, String in, String out, int dinero, String fechaInscripcion, String fechaEvento) {
        this.dni = dni;
        this.nacionalidad = nacionalidad;
        this.in = in;
        this.out = out;
        this.dinero = dinero;
        this.fechaEvento = fechaEvento;
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getDni() { return dni; }
    public String getNacionalidad() { return nacionalidad; }
    public String getIn() { return in; }
    public String getOut() { return out; }
    public String getFechaEvento() { return fechaEvento; }
    public String getFechaInscripcion() { return fechaInscripcion; }
    public int getDinero() { return dinero; }
    
    @Override
    public String toString() {
        return "Offer [dni=" + dni + ", nacionalidad=" + nacionalidad + ", in=" + in + ", out=" + out
                + ", fechaInscripcion=" + fechaInscripcion + ", fechaEvento=" + fechaEvento + ", dinero=" + dinero + "]";
    }
}
