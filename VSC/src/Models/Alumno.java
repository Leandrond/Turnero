package Models;


public class Alumno
{
    
    private int legajo;
    private Estado estado;
    private boolean prioridad;

    public Alumno()
    {
    }
    
    public Alumno(int legajo,Estado estado){
        this.legajo=legajo;
        this.estado=estado.Evaluando;
        this.prioridad=false;
    }

    public Estado getEstado(){
        return this.estado;
    }

    public void setEstado(Estado estado){
        this.estado=estado;
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