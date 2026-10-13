package Views;
import java.util.Scanner;
import Models.Docente;
import Controllers.GestionTurnero;
public class Main {

    
    public static void main(String[] args) {

        Docente docente = null;

        GestionTurnero gestionTurnero = new GestionTurnero();

        System.out.println("Bienvenido al sistema de gestión de turnos.");
        
        if (docente==null) {
            System.out.println("Registrarse.");
            
        } else {
            System.out.println("Iniciar sesión");
        }


    }
}