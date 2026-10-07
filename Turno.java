import java.util.Date;
import java.time.LocalTime;
import java.text.SimpleDateFormat;

public class Turno
{
    /**Atributos**/
    private static int contador = 1; //lo uso para que cada turno tenga su propio numero
    private int numero;
    private InstanciaEvaluativa instancia;
    private Alumno alumno;
    private Grupo grupo;
    private Date fecha;
    private LocalTime hora;
    private Estado estado;
    private boolean tienePrioridad;
    private String motivoPrioridad;
    
    /**Constructor**/
      public Turno(InstanciaEvaluativa instancia, Alumno alumno, Date fecha,
      LocalTime hora)
    {
        this.numero = contador;
        contador = contador + 1; //despues de usarlo le sumo 1 para el proximo turno
        this.instancia=instancia;
        this.alumno=alumno;
        this.fecha=fecha;
        this.hora=hora;
        this.estado=Estado.Evaluando;
    }
    
      public Turno(InstanciaEvaluativa instancia, Grupo grupo, Date fecha,
      LocalTime hora)
    {
        this.numero = contador;
        contador = contador + 1; //despues de usarlo le sumo 1 para el proximo turno
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
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaTexto = formato.format(fecha);
        String horaTexto = hora.toString();
        return fechaTexto + " " + horaTexto;
    }
    
    //este metodo nos sirve para ver si esta en estado "Evaluando" si da true
    //en el metodo del turnero siguiente turno va a dar true y nos va a 
    //devolver el objeto t y si es false no devuelve nada y avanza al que sigue.
    public boolean estaEvaluando(){
        return estado==Estado.Evaluando;
    }
}
