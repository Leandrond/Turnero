import java.util.ArrayList;
import java.util.Collections;

public class Turnero
{
    // instance variables - replace the example below with your own
    private  ArrayList<Turno> turnos = new ArrayList<>();
    
    
    public Turnero()
    {
        // initialise instance variables
        
    }

    
    public void addTurno(Turno turno)
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
    
    }