
public class Alumno
{
    
    private String nombre;
    private String apellido;
    private String dni;
    private String legajo;
    
    public Alumno()
    {
        this.nombre = "";
        this.apellido = "";
        this.legajo = "";
        this.dni = "";
        
    }
    
    public Alumno(String nombre, String apellido, String dni,String legajo)
    {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.legajo= legajo;
    }  
    //(Joaquin)--> hago un metodo set, que devuelva el legajo porque lo necesito para la comision
    
    public String getNombre() {
        return nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public String getDni() {
        return dni;
    }
    
    public String getLegajo(){
        return legajo;
    }
    
    public String getNombreCompleto(){
        return apellido + nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
}