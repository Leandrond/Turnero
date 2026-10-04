import javax.swing.Timer;
import java.util.Date;

public class Turno
{
    /**Atributos**/
    private InstanciaEvaluativa instancia;
    private Alumno alumno;
    private Grupo grupo;
    private Date fecha;
    private Timer hora;
    private boolean tienePrioridad;
    private String motivoPrioridad;
    private String estado;
    
    /**Constructor**/
    public Turno()
    {
    
    }
    
    public Alumno getAlumno()
    {
        return alumno;
    }
    
    public Alumno siguienteAlumnoXEstado()
    {
        if (alumno.getEstado().equals("Evaluando")){
                     return alumno;
        }
    return alumno;
    } 
}
