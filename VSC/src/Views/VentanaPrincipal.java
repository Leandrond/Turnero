package Views;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
//Importo la biblioteca de Swing para poder usarla y crear la ventana principal del programa


public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Turnero UTN-FRVM"); // Coloca el titulo de la ventana
        setSize(1920, 1080); // Establace el tamaño en la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la ventana al presionar el botón de cierre
        setLocationRelativeTo(null); // centra la ventana en la pantalla

        JLabel titulo = new JLabel("TURNERO", SwingConstants.CENTER);
        add(titulo);
    }

    public static void main(String[] args) {
        VentanaPrincipal ventana = new VentanaPrincipal();// Crea una instancia de la ventana principal
        ventana.setVisible(true); // Hace visible la ventana
    }
}