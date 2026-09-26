import java.util.ArrayList;

public class Comision
{
    private int codigoComision;
    private String nombre; //Yo lo haria string para poder poner "Comision 1/2/3..."
    private ArrayList<Alumno> listaAlumnos;//Lista para poder administrar alumnos

    
    public Comision()
    {
       
    }
   
    public Comision(int codigoComision, String nombre)
    {
       this.codigoComision=codigoComision;
       this.nombre=nombre;
       this.listaAlumnos= new ArrayList<>();
       
    }

    
    
    
    
}