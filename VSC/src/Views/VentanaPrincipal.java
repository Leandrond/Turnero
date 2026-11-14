package Views;

import Controllers.GestionTurnero;
import javax.swing.JFrame;


public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Turnero UTN-FRVM"); // Coloca el titulo de la ventana
        setSize(1920, 1080); // Establace el tamaño en la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la ventana al presionar el botón de cierre
        setLocationRelativeTo(null); // centra la ventana en la pantalla

        GestionTurnero gestion = new GestionTurnero();
        add(new PanelIngreso(gestion));// Agrega el panel de ingreso a la ventana
    }

    public static void main(String[] args) {
        VentanaPrincipal ventana = new VentanaPrincipal();// Crea una instancia de la ventana principal
        ventana.setVisible(true); // Hace visible la ventana
    }
}