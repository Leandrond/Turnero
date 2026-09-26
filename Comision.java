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

    //Metodos get y set
    
    //Devuelvo codigoComision
    public int getCodigoComision(){
        return codigoComision;
    }
    
    //Para poder modificar el codigo de la comision
    public void setCodigoComision(int codigoComision){
        this.codigoComision=codigoComision;
    }
    
    //Devuelvo nombre
    public String getNombre(){ 
        return nombre;
    }
    
    //Para poder modificar el nombre
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    //Devuelvo listaAlumnos
    public ArrayList<Alumno> getListaAlumnos(){
        return listaAlumnos;
    }
    

    
    
    
    
    
    
}