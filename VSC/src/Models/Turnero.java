package Models;
import java.util.ArrayList;

public class Turnero
{
    // instance variables - replace the example below with your own
    private  ArrayList<Turno> turnos = new ArrayList<>();
    
    
    public Turnero()
    {
        // initialise instance variables
        
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
        // Implementation for ordering turns by priority
        turnos.sort((t1, t2) -> Boolean.compare(t2.getPrioridad(), t1.getPrioridad()));
    }
    
    public void calcularHorarios(){
        // Implementation for calculating time slots
        // llamar un metodo del turno para calcular se duracion
    }

    }



    //HACER

    
    