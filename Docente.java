public class Docente
{
    private String nombre;
    private String apellido;
    private String legajo;

    public Docente(String nombre, String apellido, String legajo)
    {
      this.nombre=nombre;
      this.apellido=apellido;
      this.legajo=legajo;
    }
   //getters para devolver nombre, apellido y legajo 
   public String getNombre(){return nombre;}
   public String getApellido(){return apellido;}
   public String getLegajo(){return legajo;}
   //devuelve su nombre completo concatenando nombre y apellido
   public String getNombreCompleto(){return nombre+" "+apellido;}

}