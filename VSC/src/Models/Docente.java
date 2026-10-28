package Models;

import java.util.ArrayList;

public class Docente
{
    private String nombre;
    private String apellido;
    private String legajo;

    private ArrayList<Comision> listaComisiones; //Lista para poder administrar comisiones

    public Docente(String nombre, String apellido, String legajo)
    {
      this.nombre = nombre;
      this.apellido = apellido;
      this.legajo = legajo;
      this.listaComisiones = new ArrayList<>();
    }
   //getters para devolver nombre, apellido y legajo 
   public String getNombre(){return nombre;}
   public String getApellido(){return apellido;}
   public String getLegajo(){return legajo;}
   public ArrayList<Comision> getListaComisiones(){return listaComisiones;}
   //devuelve su nombre completo concatenando nombre y apellido
   public String getNombreCompleto(){return nombre+" "+apellido;}
  //metodo para agregar una comision a la lista de comisiones del docente
   public void agregarComision(Comision comision){
       this.listaComisiones.add(comision);
   }

}