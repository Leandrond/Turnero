package Models;
import java.time.LocalDate;
import java.time.LocalTime;


public class Turno
{
    /**Atributos**/
    private static int contador = 1; //lo uso para que cada turno tenga su propio numero
    private int numero; 
    private InstanciaEvaluativa instancia; //instancia evaluativa a la que pertenece el turno
    private Alumno alumno; //se carga solo si el turno es de un alumno (individual)
    private Grupo grupo; //se carga solo si el turno es de un grupo (grupal)
    private Comision comision; //se carga solo si el turno es de un grupo (general)
    private LocalDate fecha;
    private LocalTime hora;
    private Estado estado; //Evaluando, Finalizado o Ausente (arranca en Evaluando)
    private boolean tienePrioridad;
    private String motivoPrioridad;
    
    /**Constructor**/
      public Turno(InstanciaEvaluativa instancia, Alumno alumno, LocalDate fecha, LocalTime hora)
    {
        this.numero = contador;
        contador = contador + 1; //despues de usarlo le sumo 1 para el proximo turno
        this.instancia=instancia;
        this.alumno=alumno;
        this.fecha=fecha;
        this.hora=hora;
        this.estado=Estado.Evaluando;
    }

    public InstanciaEvaluativa getInstancia()
    {
        return instancia;
    }

    public int getCodigoInstancia()
    {
        return instancia.getCodigo();
    }

    
    public Alumno getAlumno()
    {
        return alumno;
    }
    
    //Metodos get y set agregados por Leo
    
    //Devuelvo numero
    public int getNumero(){
        return numero;
    }
    
    //Devuelvo estado (Evaluando, Finalizado o Ausente)
    public Estado getEstado(){
        return estado;
    }
    
    //Para poder cambiar el estado (lo usa el docente para finalizar o marcar ausente)
    public void setEstado(Estado estado){
        this.estado = estado;
    }
    
    //Devuelvo grupo (si el turno es de un alumno devuelve null)
    public Grupo getGrupo(){
        return grupo;
    }
    
    //Devuelvo la fecha y la hora juntas en un texto, ej: 2026-09-20 10:30
    public String getFechaHora(){
        return fecha + " " + hora;

    }
    
    //este metodo nos sirve para ver si esta en estado "Evaluando" si da true
    //en el metodo del turnero siguiente turno va a dar true y nos va a 
    //devolver el objeto t y si es false no devuelve nada y avanza al que sigue.
    public boolean estaEvaluando(){
        return estado==Estado.Evaluando;
    }

    public boolean getPrioridad(){
        return alumno.getPrioridad();
    }
}
