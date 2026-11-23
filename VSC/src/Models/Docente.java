package Models;

import java.util.ArrayList;

public class Docente extends Persona
{
    private final String legajo;

    private ArrayList<Comision> listaComisiones; //Lista para poder administrar comisiones

    public Docente(String nombre, String apellido, String legajo)
    {
      super(nombre, apellido);
      this.legajo = legajo;
      this.listaComisiones = new ArrayList<>();
    }
   public String getLegajo(){return legajo;}
   public ArrayList<Comision> getListaComisiones(){return listaComisiones;}
  //metodo para agregar una comision a la lista de comisiones del docente
   public void agregarComision(Comision comision){
       this.listaComisiones.add(comision);
   }

}