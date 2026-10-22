package models;
import java.util.Date;
import java.util.Timer;

// es una clase que hereda porque hay 3 tios de instancia evaluativa Defensa de Parcial - Presentacion de Trabajo Practico - Examen Final
public abstract class InstanciaEvaluativa
{
    // Atributos
    protected String tipo;
    protected Date fecha;
    protected Timer hora;
    //protected Comision comision;
    protected double duracionEstimada;
    //protected Turno turno;
    //protected Docente docente;
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
    //public abstract Turno getTurno();
    //public abstract Docente getDocente();
    // Setters
    public abstract void setFecha(Date fecha);
    public abstract void setHoraInicio(Timer hora);
    //public abstract void setComision(Comision comision);
    public abstract void setDuracionEstimada(Double duracionEstimada);
    //public abstract void setTurno(Turno turno);
    //public abstract void setDocente(Docente docente);
    // Metodos y comentarios abstractos
    //public abstract void sumarDocente(Docente docente);




    
}