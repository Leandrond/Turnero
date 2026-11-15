package Views;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelMenu extends JPanel {

    public PanelMenu(VentanaPrincipal ventana) {
        setLayout(new GridBagLayout());

        JPanel menu = new JPanel(new GridLayout(4, 1, 10, 10));// Crea un menu con 4 filas y 1 columna, con un espacio de 10 pixeles entre cada componente
        menu.setPreferredSize(new Dimension(400, 220));// Establece el tamaño preferido del menu para que no se estire con la ventana

        menu.add(new JLabel("MENU PRINCIPAL", SwingConstants.CENTER));// Agrega un titulo al menu centrado

        JButton botonComisiones = new JButton("Comisiones");// Crea un boton para acceder a la pantalla de comisiones
        menu.add(botonComisiones);// Agrega el boton al menu
        botonComisiones.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Pantalla de comisiones: próximamente."));//Agrega el evento de click al boton de comisiones

        JButton botonInstancias = new JButton("Instancias evaluativas");// Crea un boton para acceder a la pantalla de instancias evaluativas
        menu.add(botonInstancias);// Agrega el boton al menu
        botonInstancias.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Pantalla de instancias: próximamente."));//Agrega el evento de click al boton de instancias evaluativas

        JButton botonSalir = new JButton("Salir");// Crea un boton para salir de la aplicacion
        menu.add(botonSalir);// Agrega el boton al menu
        botonSalir.addActionListener(e -> System.exit(0));//Agrega el evento de click al boton de salir para cerrar la aplicacion

        add(menu);// Agrega el menu a la pantalla
    }
}