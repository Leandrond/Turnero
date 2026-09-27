import java.util.List;
import java.util.ArrayList;

public class Grupo
{
    private int numero;
    private List<Alumno> listaAlumno;
    private int cupoMaximo;
    
    public Grupo()
    {
        
    }
    
    public Grupo(int numero)
    {
        this.numero=numero;
        this.listaAlumno=new ArrayList<>();
        this.cupoMaximo=cupoMaximo;
    }

    public int getNumero(){
        return numero;
    }
    
    public List<Alumno> getlistaAlumnos(){
        return listaAlumno;
    }
    
    public void agregarAlumno (Alumno alumno){
        listaAlumno.add(alumno);
    }
    
    public boolean tieneDisponibilidad(){
        return listaAlumno.size()<cupoMaximo;
    }
}