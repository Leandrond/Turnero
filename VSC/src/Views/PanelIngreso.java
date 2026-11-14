package Views;

import Controllers.GestionTurnero;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelIngreso extends JPanel {

    private GestionTurnero gestion;
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoLegajo;

    public PanelIngreso(GestionTurnero gestion) {
        this.gestion = gestion;
        setLayout(new GridBagLayout()); // centra en la pantalla lo que tenga adentro

        // Formulario con tamaño fijo, asi no se estira con la ventana
        JPanel formulario = new JPanel(new GridLayout(4, 2, 10, 10));
        formulario.setPreferredSize(new Dimension(400, 200));

        formulario.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        formulario.add(campoNombre);

        formulario.add(new JLabel("Apellido:"));
        campoApellido = new JTextField();
        formulario.add(campoApellido);

        formulario.add(new JLabel("Legajo:"));
        campoLegajo = new JTextField();
        formulario.add(campoLegajo);

        JButton botonIngresar = new JButton("Ingresar");
        formulario.add(botonIngresar);

        botonIngresar.addActionListener(e -> ingresar());

        add(formulario);
    }

    private void ingresar() {
        String nombre = campoNombre.getText().trim();
        String apellido = campoApellido.getText().trim();
        String legajo = campoLegajo.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || legajo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completá todos los campos.");
            return;
        }

        gestion.registrarDocente(nombre, apellido, legajo);
        JOptionPane.showMessageDialog(this, "Docente registrado: " + gestion.getDocenteActual().getNombreCompleto());
    }
}