

public class Materia
{
    private String nombre;
    private docente representanteCatedra;
    
    public Materia(String nombre, Docente representanteCatedra)
    {
        this.nombre = nombre;
        this.representanteCatedra = representanteCatedra;
    }
    //getters para devolver nombre y el representante de catedra
    public String getNombre(){
        return nombre;
    }

    public Docente getRepresentanteCatedra(){
        return representanteCatedra;
    }
    //para modificar el representante de catedra de la materia
    public void setRepresentanteCatedra(Docente docente){
        this.representanteCatedra = docente;
    }
}