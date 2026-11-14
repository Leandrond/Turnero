package Models.Instancias;
import java.time.LocalDate;
import java.time.LocalTime;

import Models.Comision;
import Models.Docente;
import Models.Modalidad;
import Models.Turnero;

// es una clase que hereda porque hay 3 tipos de instancia evaluativa Defensa de Parcial - Presentacion de Trabajo Practico - Examen Final
public abstract class InstanciaEvaluativa
{
    // Atributos
    protected int codigo; //lo agrego para identificarla
    protected String tipo;
    protected LocalDate fecha;
    protected LocalTime horaInicio;
    protected Comision comision;
    protected double duracionEstimada;
    private Modalidad modalidad;

    protected Turnero turnero;
    protected Docente docente;
    protected int codigoDeInstancia;
    // Constructor
    public InstanciaEvaluativa(Modalidad modalidad)
    {
        this.modalidad = modalidad;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }
    // Getters
    public abstract  int getCodigo();
    
    public abstract String getTipo();
    public abstract LocalDate getFecha();
    public abstract LocalTime getHoraInicio();  
    //public abstract Comision getComision();
    public abstract Double getDuracionEstimada();
    public abstract Turnero getTurnero();
    public abstract Docente getDocente();
    public abstract int getCodigoDeInstancia();
    // Setters
    public abstract void setFecha(LocalDate fecha);
    public abstract void setHoraInicio(LocalTime hora);
    //public abstract void setComision(Comision comision);
    public abstract void setDuracionEstimada(Double duracionEstimada);

    //public abstract void setDocente(Docente docente);
    // Metodos y comentarios abstractos
    //public abstract void sumarDocente(Docente docente);
    public abstract void setCodigoDeInstancia(int codigoDeInstancia);
}