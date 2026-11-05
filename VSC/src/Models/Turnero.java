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
   
    
    // public void ordenarTurnosPorPrioridad(){
    //     // Implementation for ordering turns by priority
    //     turnos.sort((t1, t2) -> Boolean.compare(t2.getPrioridad(), t1.getPrioridad()));
    // }
    
    public void calcularHorarios(){
        // Implementation for calculating time slots
        // llamar un metodo del turno para calcular se duracion
    }

    public Turno getTurnoActual() {
        for (Turno turno : turnos) {
            if (turno.getEstado() == Estado.Evaluando) {
                return turno;
            }
        }
        return null; // No hay turno en curso
    }

    public Turno obtenerSiguienteTurno(Turno turnoActual) {
        for (int i = 0; i < turnos.size(); i++) {
            if (turnos.get(i).equals(turnoActual) && i + 1 < turnos.size()) {
                return turnos.get(i + 1);
            }
        }
        return null; // No hay siguiente turno
    }

    



}
    



    //HACER

    
    