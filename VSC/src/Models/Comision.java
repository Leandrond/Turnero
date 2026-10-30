package Models;
import java.util.ArrayList;

public class Comision
{
    private int codigoComision;
    private String nombre; //Yo lo haria string para poder poner "Comision 1/2/3..."
    private ArrayList<Alumno> listaAlumnos;//Lista para poder administrar alumnos
    private Materia materia; //MANU:Para poder asociar la comision a una materia, ademas ya lo puse en el constructor y puse su getter.
    
    public Comision()
    {
       
    }
   
    public Comision(int codigoComision, String nombre, Materia materia)
    {
       this.codigoComision=codigoComision;
       this.nombre=nombre;
       this.materia=materia;
       this.listaAlumnos= new ArrayList<>();
       
    }
    
    //====================================================================================
    //Metodos get y set
    //====================================================================================
    
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

    //Devuelvo la materia asociada
    public Materia getMateria(){
    return materia;
}
    
    //====================================================================================
    // METODOS DE COMPORTAMIENTO
    //====================================================================================
    
    //Agregar alumno
    public void agregarAlumno(Alumno alumno){
        if(alumno != null){ //verifico que alumno no sea un valor nulo
            this.listaAlumnos.add(alumno);
        }
    }
    
    //Contar cantidad de alumnos
    public int getCantidadAlumnos(){
        return this.listaAlumnos.size();
    }
    
    //Busco alumnos por su legajo
    public Alumno buscarAlumnoPorLegajo(int legajo){
        for (Alumno a : this.listaAlumnos){
            if(a.getLegajo() == legajo){
                return a;//devuelve el alumno coincidente
            }
        }
        return null;//si no hay coincidente al recorrer la lista, retorna valor null
    }
    
    //Represento en texto en la comision
    public String toString(){
        return      "Comision " + codigoComision 
                    + " - " + nombre  
                    + " - Materia: " + materia.getNombre()//MANU: le agregue la materia asi queda completo
                    + "(Alumnos instriptos: )" 
                    + getCantidadAlumnos() + ")";
    }
    
    //Para dar de baja un alumno utilizo un booleano, retorna true si el alumno se pudo
    //dar de baja, sino retorna false.
    public boolean darDeBajaAlumno(int legajo){ //Recibe el legajo del alumno a dar de baja
        Alumno alumnoAEliminar=buscarAlumnoPorLegajo(legajo);
        if(alumnoAEliminar != null){
            return this.listaAlumnos.remove(alumnoAEliminar);//elimino el coincidente de a lista
        }
        return false;//si no hay coincidente retorno false
    }
    
    
}