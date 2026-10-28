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
    //Se modifica agregarAlumno() para verificar 
    // la disponibilidad antes de incorporar un alumno.
    //Y que no se repita el mismo alumno en el grupo. 11-10-26
    public void agregarAlumno (Alumno alumno){
        if (tieneDisponibilidad() && 
        !integrantes.contains(alumno)){
            integrantes.add(alumno);
        }
    }
    public void setCupoMaximo(int cupoMaximo){
        if (cupoMaximo > 0 && cupoMaximo >= 
            integrantes.size()){ 
            this.cupoMaximo = cupoMaximo;
        }
    }
    //11-10-26 Se agrega el método quitarAlumno()
    //  para eliminar un alumno del grupo.
    public void quitarAlumno(Alumno alumno){
        integrantes.remove(alumno);
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