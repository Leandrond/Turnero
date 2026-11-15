package Models;

public class Materia
{
    private String nombre;
    private Docente representanteCatedra;
    private int idMateria;
    public Materia(String nombre, Docente representanteCatedra)
    {
        this.nombre = nombre;
        this.representanteCatedra = representanteCatedra;
        
    }
    //GETTERS Y SETTERS
    public String getNombre(){
        return nombre;
    }

    public int getIdMateria(){
        return idMateria;
    }

    public void setIdMateria(int idMateria){
        this.idMateria = idMateria;
    }

    public Docente getRepresentanteCatedra(){
        return representanteCatedra;
    }

    public void setRepresentanteCatedra(Docente docente){
        this.representanteCatedra = docente;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}