package Models;
import java.time.LocalDate;
import java.time.LocalTime;

// es una clase que hereda porque hay 3 tios de instancia evaluativa Defensa de Parcial - Presentacion de Trabajo Practico - Examen Final
public abstract class InstanciaEvaluativa
{
    // Atributos
    protected String tipo;
    protected LocalDate fecha;
    protected LocalTime horaInicio; 
    protected Comision comision;
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
    public abstract LocalDate getFecha();
    public abstract LocalTime getHoraInicio();
    public abstract Comision getComision();
    public abstract Double getDuracionEstimada();
    public abstract Turnero getTurnero();
    public abstract Docente getDocente();
    public abstract int getCodigoDeInstancia();
    // Setters
    public abstract void setFecha(LocalDate fecha);
    public abstract void setHoraInicio(LocalTime horaInicio);
    public abstract void setComision(Comision comision);
    public abstract void setDuracionEstimada(Double duracionEstimada);
    public abstract void setTurnero(Turnero turnero);
    public abstract void setDocente(Docente docente);
    public abstract void setCodigoDeInstancia(int codigoDeInstancia);
     //Metodos y comentarios abstractos




    
}