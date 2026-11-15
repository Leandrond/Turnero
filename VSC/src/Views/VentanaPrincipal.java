
package Views;
import Conexion.InicializadorBD;
import Controllers.GestionTurnero;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class VentanaPrincipal extends JFrame {

    private CardLayout cartas = new CardLayout();
    private JPanel contenedor = new JPanel(cartas);

    private GestionTurnero gestion;

    public VentanaPrincipal() {

        setTitle("Sistema Turnero - UTN FRVM");

        setSize(1100, 700);
        setMinimumSize(new Dimension(1920, 1080));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(EstiloTurnero.CHOCOLATE);
        contenedor.setBackground(EstiloTurnero.CHOCOLATE);

        // Una sola gestión para todas las pantallas
        gestion = new GestionTurnero();

        // Pantalla de ingreso
        contenedor.add(
            new PanelIngreso(gestion, this),
            "ingreso"
        );
        contenedor.add(
            new PanelComisiones(gestion, this),
            "comisiones"
        );

        // Menú principal
        contenedor.add(
            new PanelMenu(this),
            "menu"
        );

        // Pantalla de comisiones
        
        contenedor.add(
            new PanelAlumnos(gestion, this),
            "alumnos"
        );
        
        contenedor.add(
            new PanelGrupos(gestion, this),
            "grupos"
        );


        contenedor.add(
            new PanelInstancias(gestion, this),
            "instancias"
        );

        contenedor.add(
            new PanelTurnos(gestion, this),
            "turnos"
        );



        add(contenedor);
    }

    public void mostrarPantalla(String nombre) {
        cartas.show(contenedor, nombre);
    }

    public GestionTurnero getGestion() {
        return gestion;
    }

    
    public static void main(String[] args) {

        try {
            InicializadorBD.crearBD();

            SwingUtilities.invokeLater(() -> {
                VentanaPrincipal ventana = new VentanaPrincipal();
                ventana.setVisible(true);
            });

        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(
                null,
                "No se pudo inicializar la base de datos: "
                + ex.getMessage()
            );
        }
    }

}
