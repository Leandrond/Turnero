package Models;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Grupo implements Priorizable
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
    public boolean agregarAlumno (Alumno alumno){
        if (alumno != null && tieneDisponibilidad() &&
        !integrantes.contains(alumno)){
            integrantes.add(alumno);
            return true;
        }
        return false;
    }
    
    public int getCupoMaximo(){
        return this.cupoMaximo;
    }
    
    public boolean setCupoMaximo(int cupoMaximo){
        if (cupoMaximo > 0 && cupoMaximo >= 
            integrantes.size()){ 
            this.cupoMaximo = cupoMaximo;
            return true;
        }
        return false;
    }
    //11-10-26 Se agrega el método quitarAlumno()
    //  para eliminar un alumno del grupo.
    public boolean quitarAlumno(Alumno alumno){
        return integrantes.remove(alumno);
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
    @Override
    public int getNivelPrioridad() {
        return contarAlumnosPrioritarios();
    }

    public int contarAlumnosPrioritarios(){
         int contador = 0;
        for (Alumno a : integrantes) {
            if (a.getPrioridad()) {
                contador++;
            }
        }
        return contador;
    }
       
}