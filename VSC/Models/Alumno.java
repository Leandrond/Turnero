
public class Alumno
{
    
    private int legajo;
    private Estado estado;
    
    public Alumno()
    {
        
        
    }
    
    public Alumno(int legajo)
    {
        this.legajo=legajo;
        this.estado=estado.Evaluando;
    }
    public Estado getEstado(){
        return estado;
    }
    //(Joaquin)--> hago un metodo set, que devuelva el legajo porque lo necesito para la comision
    public int getLegajo(){
        return legajo;
    }
    
}