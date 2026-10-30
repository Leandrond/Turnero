package Models;

import java.util.ArrayList;

public class Docente
{
    private String nombre;
    private String apellido;
    private String legajo;
    private ArrayList<Comision> listaComisiones;

    public Docente(String nombre, String apellido, String legajo)
    {
      this.nombre=nombre;
      this.apellido=apellido;
      this.legajo=legajo;
      this.listaComisiones = new ArrayList<Comision>();
    }

   //GETTERS 
   public String getNombre(){return nombre;}
   public String getApellido(){return apellido;}
   public String getLegajo(){return legajo;}
   public ArrayList<Comision> getComisiones(){return listaComisiones;}
   //para devolver el nombre completo
   public String getNombreCompleto(){return nombre+" "+apellido;}
   
   public void agregarComision(Comision comision){
        if(comision != null){ //verifico que comision no sea un valor nulo
            this.listaComisiones.add(comision);
        }
    }
}