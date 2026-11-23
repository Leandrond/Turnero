package Models;


public class Alumno extends Persona implements Priorizable
{
    
    private final int legajo;
    private Estado estado;
    private boolean prioridad;

    public Alumno(String nombre, String apellido, int legajo) {
        super(nombre, apellido);
        this.legajo = legajo;
        this.estado = Estado.EVALUANDO;
        this.prioridad = false;
    }

    public Estado getEstado(){
        return this.estado;
    }

    public void setEstado(Estado estado){
        this.estado=estado;
    }

    @Override
    public int getNivelPrioridad() {
        if (getPrioridad()) {
            return 1;
        }
        return 0;
    }

    public boolean getPrioridad(){
        return this.prioridad;
    }

    public void setPrioridad(boolean prioridad){
        this.prioridad=prioridad;
    }
    
    public int getLegajo(){
        return legajo;
    }

    
}