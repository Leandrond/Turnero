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
    
    //WLela deice que esto va aca Diego dice que va en turno.
    
    }
}