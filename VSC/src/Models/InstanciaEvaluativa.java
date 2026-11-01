package models;
import java.util.Date;
import java.util.Timer;

// es una clase que hereda porque hay 3 tipos de instancia evaluativa Defensa de Parcial - Presentacion de Trabajo Practico - Examen Final
public abstract class InstanciaEvaluativa
{
    // Atributos
    protected int codigo; //lo agrego para identificarla
    protected String tipo;
    protected Date fecha;
    protected Timer hora;
    //protected Comision comision;
    protected double duracionEstimada;

    protected Turnero turnero;
    protected Docente docente;
    protected int codigoDeInstancia;
    // Constructor
    public InstanciaEvaluativa()
    {
        
    }
    // Getters
    public abstract String getTipo();
    public abstract Date getFecha();
    public abstract Timer getHoraInicio();  
    //public abstract Comision getComision();
    public abstract Double getDuracionEstimada();
    public abstract Turnero getTurnero();
    public abstract Docente getDocente();
    public abstract int getCodigoDeInstancia();
    // Setters
    public abstract void setFecha(Date fecha);
    public abstract void setHoraInicio(Timer hora);
    //public abstract void setComision(Comision comision);
    public abstract void setDuracionEstimada(Double duracionEstimada);

    public void setTurnero(Turnero turnero) {
        this.turnero = turnero;
    }
    public Turnero getTurnero() {
        return this.turnero;
    }
    //public abstract void setTurno(Turno turno);
    //public abstract void setDocente(Docente docente);
    // Metodos y comentarios abstractos
    //public abstract void sumarDocente(Docente docente);
    public abstract void setCodigoDeInstancia(int codigoDeInstancia);
}