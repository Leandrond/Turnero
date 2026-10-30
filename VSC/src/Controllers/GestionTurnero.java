package Controllers;

import Models.Docente;
import Models.Materia;
import Models.Comision;

public class GestionTurnero {
    private Docente docente;

    public GestionTurnero() {
        System.out.println("Hola Gestion del Turnero");
    }

    public boolean tieneDocenteRegistrado(){
        return docente != null;
    }

    public void registrarDocente(String nombre, String apellido, String legajo) {
    this.docente = new Docente(nombre, apellido, legajo);
    }

    public void crearComision(int codigo, String nombre, String materia) {
    if (tieneDocenteRegistrado()) {

        Materia nuevaMateria = new Materia(materia, docente);
        Comision nuevaComision = new Comision(codigo, nombre, nuevaMateria);

        docente.agregarComision(nuevaComision);

        } else {
            System.out.println("No hay docente registrado. No se puede crear la comisión.");
        }
    }

    public void listarComisiones() {
        if (tieneDocenteRegistrado()) {
            System.out.println("Comisiones del docente " + docente.getNombreCompleto() + ":");
            for (Comision comision : docente.getComisiones()) {
                System.out.println(comision.toString());
            }
        } else {
            System.out.println("No hay docente registrado. No se pueden listar las comisiones.");
        }
    }
} 