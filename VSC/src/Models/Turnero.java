package Models;
import java.util.ArrayList;

public class Turnero
{
    // instance variables - replace the example below with your own
    private  ArrayList<Turno> turnos = new ArrayList<>();
    
    
    public Turnero()
    {
        // initialise instance variables
        turnos = new ArrayList<>();
        
    }

    public ArrayList<Turno> getTurnero()
    {
        return this.turnos;
    }
    public void agregarTurno(Turno turno)
    {
        turnos.add(turno);
        
    }
    
    public ArrayList<Turno> getListaTurno()
    {
        return turnos;
    }
    
    public void cambiarOrden(Turno t, int nuevaPosicion)
    {
        turnos.remove(t);
        turnos.add(nuevaPosicion, t);
    }
    //Recorre la lista de turnos y devuelve el primer turno que se encuentre
    //en estado "Evaluando". Si no encuentra ninguno devuelve NUll.
    //como queremos que nos devuelva al "siguiente alumno" ya el for esta 
    //recorriendo por lo tanto todo alumno que el estado sea "Evaluando" va a
    //ser el siguiente. Cuando le cambias el estado y no es evaluando el for
    //pasa al que si tenga evaluando. Me explico?DV
    public Turno siguienteTurno(){
        for (Turno t: turnos){
            if (t.estaEvaluando()){
                return t;
            }
        }
        return null;
    }
    
    public void ordenarTurnosPorPrioridad(){
        
        turnos.sort((t1, t2) -> Boolean.compare(t2.getPrioridad(), t1.getPrioridad()));
    }
    
    
public void calcularHorarios() {

    if (turnos.isEmpty()) {
        return;
    }

    // aca obtenemos los datos de la instancia evaluativa.
    InstanciaEvaluativa instancia =
        turnos.get(0).getInstancia();

    if (instancia == null) {
        return;
    }

    java.time.LocalDate fecha = instancia.getFecha();
    java.time.LocalDateTime horario =
        java.time.LocalDateTime.of(
            fecha,
            instancia.getHoraInicio()
        );

    double duracion = instancia.getDuracionEstimada();

    if (duracion <= 0) {
        return;
    }

    // aca recorremos los turnos en su orden actual.
    for (Turno turno : turnos) {

        // Asignamos la fecha y hora estimadas.
        turno.setFecha(horario.toLocalDate());
        turno.setHora(horario.toLocalTime());

        // Avanzamos la duracion estimada del turno.
        horario = horario.plusSeconds(
            Math.round(duracion * 60)
        );
    }

    // PENDIENTE GESTIONTURNERO:
    // Llamar a calcularHorarios() despues de
    // ordenar los turnos o modificar su orden.
    //
    // La variable horario contiene al finalizar
    // la fecha y hora estimadas de finalizacion.
    // para consultarlo tambien habra que agregar un atributo o getter.
}



public void ordenarPorPrioridad() {

    // aca ordenamos la lista de turnos segun su prioridad.
    // Los turnos prioritarios quedan al principio.

    turnos.sort((turno1, turno2) -> {

        int prioridad1 = 0;
        int prioridad2 = 0;

        // Evaluaciones individuales.
        if (turno1.getAlumno() != null &&
            turno1.getAlumno().getPrioridad()) {
            prioridad1 = 1;
        }

        if (turno2.getAlumno() != null &&
            turno2.getAlumno().getPrioridad()) {
            prioridad2 = 1;
        }

        // Evaluaciones grupales.
        // Los grupos con mas alumnos prioritarios
        // deben aparecer primero.
        if (turno1.getGrupo() != null) {
            prioridad1 = turno1.getGrupo()
                .contarAlumnosPrioritarios();
        }

        if (turno2.getGrupo() != null) {
            prioridad2 = turno2.getGrupo()
                .contarAlumnosPrioritarios();
        }

        // Orden descendente: mayor prioridad primero.
        return Integer.compare(prioridad2, prioridad1);
    });

    // PENDIENTE EN GESTIONTURNERO:
    // Llamar a este metodo despues de generar
    // los turnos de cada instancia evaluativa.
}


    }



    //HACER

    
    