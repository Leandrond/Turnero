package Views;

import Controllers.GestionTurnero;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaPrincipal extends JFrame {

    private CardLayout cartas = new CardLayout(); // Mazo de pantallas donde se muestra una sola a la vez
    private JPanel contenedor = new JPanel(cartas); // Panel que guarda todas las pantallas
    private PanelComisiones panelComisiones; // Se guarda para poder refrescar su tabla al entrar

    public VentanaPrincipal() {
        setTitle("Turnero UTN-FRVM"); // Coloca el titulo de la ventana
        setSize(1920, 1080); // Establece el tamano de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la ventana al presionar el boton de cierre
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        GestionTurnero gestion = new GestionTurnero(); // Crea la gestion que usan todas las pantallas

        panelComisiones = new PanelComisiones(gestion, this);
        contenedor.add(new PanelIngreso(gestion, this), "ingreso"); // Agrega la pantalla de ingreso (es la primera que se ve)
        contenedor.add(new PanelMenu(this), "menu"); // Agrega la pantalla del menu principal
        contenedor.add(panelComisiones, "comisiones"); // Agrega la pantalla de comisiones
        add(contenedor); // Agrega el mazo de pantallas a la ventana
    }

    public void mostrarPantalla(String nombre) {
        cartas.show(contenedor, nombre); // Muestra la pantalla que tiene el nombre indicado
    }

    public void mostrarComisiones() {
    panelComisiones.actualizar(); // Carga la tabla con los datos actuales
    cartas.show(contenedor, "comisiones"); // Muestra la pantalla de comisiones
    }

    public static void main(String[] args) {
        VentanaPrincipal ventana = new VentanaPrincipal(); // Crea una instancia de la ventana principal
        ventana.setVisible(true); // Hace visible la ventana
    }
}