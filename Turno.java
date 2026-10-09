import java.util.Date;
import java.time.LocalTime;

public class Turno
{
    /**Atributos**/
    private InstanciaEvaluativa instancia;
    private Alumno alumno;
    private Grupo grupo;
    private Date fecha;
    private LocalTime hora;
    private Estado estado;
    
    /**Constructor**/
    public Turno()
    {
    
    }
    
      public Turno(InstanciaEvaluativa instancia, Alumno alumno, Date fecha,
      LocalTime hora)
    {
        this.instancia=instancia;
        this.alumno=alumno;
        this.fecha=fecha;
        this.hora=hora;
        this.estado=Estado.Evaluando;
    }
    
      public Turno(InstanciaEvaluativa instancia, Grupo grupo, Date fecha,
      LocalTime hora)
    {
        this.instancia=instancia;
        this.grupo=grupo;
        this.fecha=fecha;
        this.hora=hora;
        this.estado=Estado.Evaluando;
    }
    
    public Alumno getAlumno()
    {
        return alumno;
    }
    //este metodo nos sirve para ver si esta en estado "Evaluando" si da true
    //en el metodo del turnero siguiente turno va a dar true y nos va a 
    //devolver el objeto t y si es false no devuelve nada y avanza al que sigue.
    public boolean estaEvaluando(){
        return estado==Estado.Evaluando;
    }
}
