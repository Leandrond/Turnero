package Models;

import java.util.ArrayList;

public class Docente
{
    private String nombre;
    private String apellido;
    private String legajo;
    private ArrayList<Comision> listaComisiones;//Lista para poder administrar comisiones

    public Docente(String nombre, String apellido, String legajo)
    {
      this.nombre = nombre;
      this.apellido = apellido;
      this.legajo = legajo;
      this.listaComisiones = new ArrayList<>();
    }

   //GETTERS 
   public String getNombre(){return nombre;}
   public String getApellido(){return apellido;}
   public String getLegajo(){return legajo;}
   public ArrayList<Comision> getListaComisiones(){return listaComisiones;}
  
   //devuelve su nombre completo concatenando nombre y apellido
   public String getNombreCompleto(){return nombre+" "+apellido;}
  
   //metodo para agregar una comision a la lista de comisiones del docente
   public void agregarComision(Comision comision) {
        if (comision != null && !listaComisiones.contains(comision)) { //para que no se agreguen comisiones repetidas
            listaComisiones.add(comision);
        }
    }
    
    //metodo para eliminar una comision de la lista de comisiones del docente
     public boolean eliminarComision(Comision comision) {
        return listaComisiones.remove(comision);
    }

}