

public class Materia
{
    private String nombre;
  - private docente representanteCatedra;
    
    public Materia(String nombre, Docente representanteCatedra)
    {
        this.nombre = nombre;
        this.representanteCatedra = representanteCatedra;
    }

    public String getNombre(){
        return nombre;
    }

    public Docente getRepresentanteCatedra(){
        return representanteCatedra;
    }

    public void setRepresentanteCatedra(Docente docente){
        this.representanteCatedra = docente;
    }
}