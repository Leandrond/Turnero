import java.util.Date;
import java.util.Timer;
// es una clase que hereda porque hay 3 tios de instancia evaluativa Defensa de Parcial - Presentacion de Trabajo Practico - Examen Final
public abstract class InstanciaEvaluativa
{
    // Atributos
    private String tipo;
    private Date fecha;
    private Timer hora;
    private Comision comision;
    private String duracionEstimada;
    private Turno turno;
    private Docente docente;
    // Constructor
    public InstanciaEvaluativa()
    {
        
    }
    // Getters
    public String getTipo(){return tipo;}
    public Date getFecha(){return fecha;}
    public Timer getHoraInicio(){return hora;}
    public Comision getComision(){return comision;}
    public String getDuracionEstimada(){return duracionEstimada;}
    public Turno getTurno(){return turno;}
    public Docente getDocente(){return docente;}
    // Setters
    public void setFecha(Date fecha){this.fecha=fecha;}
    public void setHoraInicio(Timer hora){this.hora=hora;}
    public void setComision(Comision comision){this.comision=comision;}
    public void setDuracionEstimada(String duracionEstimada){this.duracionEstimada=duracionEstimada;}
    public void setTurno(Turno turno){this.turno=turno;}
    public void setDocente(Docente docente){this.docente=docente;}
    // Metodos y comentarios abstractos
    public abstract void calcularDuracionEstimada();
    public abstract void sumarDocente(Docente docente);




    
}