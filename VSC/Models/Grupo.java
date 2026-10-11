package Models;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Grupo
{
    private int numero;
    private List<Alumno> integrantes;
    private int cupoMaximo;
    
    public Grupo()
    {
        
    }
    
    public Grupo(int numero, int cupoMaximo)
    {
        this.numero=numero;
        this.integrantes=new ArrayList<>();
        this.cupoMaximo=cupoMaximo;
    }

    public int getNumero(){
        return numero;
    }
    
    public List<Alumno> getlistaAlumnos() {
    return Collections.unmodifiableList(integrantes);
    }
    
    public void agregarAlumno (Alumno alumno){
        if (tieneDisponibilidad()){
            integrantes.add(alumno);
        }
    }
    
    public boolean tieneDisponibilidad(){
        return integrantes.size()<cupoMaximo;
    }
    //Se modifica agregarAlumno() para verificar 
    // la disponibilidad antes de incorporar un alumno. 
    // Se modifica getlistaAlumnos() para devolver una 
    // vista no modificable de la lista, 
    // evitando que otras clases alteren directamente 
    // los integrantes del grupo. 10-10-26
}