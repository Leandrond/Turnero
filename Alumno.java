
public class Alumno
{
    
    private int legajo;
    
    public Alumno()
    {
        
        
    }

    
    public Alumno(int legajo)
    {
        this.legajo=legajo;
        
    }
    
    //(Joaquin)--> hago un metodo set, que devuelva el legajo porque lo necesito para la comision
    public int getLegajo(){
        return legajo;
    }
}