import java.util.Timer;
import java.util.Date;

public class InstanciaEvaluativa
{
    // instance variables - replace the example below with your own
    private String tipo;
    private Date fecha;
    private Timer hora;
    private Comision comision;
    private String duracionEstimada;
    private Turno turno;

    public InstanciaEvaluativa()
    {
        // initialise instance variables
        
    }
    public InstanciaEvaluativa( String tipo, Date fecha,Timer hora,  Comision comision,  String duracionEstimada, Turno turno)
    {
        // initialise instance variables
        this.tipo =tipo;
        this.fecha= fecha;
        this.hora = hora;
        this.comision = comision;
        this.duracionEstimada = duracionEstimada;
        this.turno=turno;
    }
    public String getTipo(){return tipo;}
    public Date getFecha(){return fecha;}
    public Timer getHoraInicio(){return hora;}
    public Comision getComision(){return comision;}
    public String getDuracionEstimada(){return duracionEstimada;}
    public Turno getTurno(){return turno;}
    
}