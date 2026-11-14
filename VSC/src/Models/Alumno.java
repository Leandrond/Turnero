package Models;


public class Alumno extends Persona implements Priorizable
{
    
    private int legajo;
    private Estado estado;
    private boolean prioridad;

    public Alumno(String nombre, String apellido, int legajo) {
        super(nombre, apellido);
        this.legajo = legajo;
        this.estado = Estado.Evaluando;
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
    
    public void setLegajo(int legajo){
        this.legajo=legajo;
    }

    // hago un metodo set, que devuelva el legajo porque lo necesito para la comision
    public int getLegajo(){
        return legajo;
    }

    
}