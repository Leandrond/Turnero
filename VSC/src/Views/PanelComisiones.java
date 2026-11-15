package Views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import Controllers.GestionTurnero;
import Models.Comision;
import Models.Materia;

public class PanelComisiones extends JPanel {

    private GestionTurnero gestion; // Guarda la gestion para leer y crear comisiones
    private VentanaPrincipal ventana; // Guarda la ventana para poder volver al menu
    private DefaultTableModel modelo; // Contiene los datos que se muestran en la tabla

    public PanelComisiones(GestionTurnero gestion, VentanaPrincipal ventana) {
        this.gestion = gestion;
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10)); // Divide la pantalla en norte centro y sur

        JLabel titulo = new JLabel("COMISIONES", SwingConstants.CENTER); // Titulo centrado
        add(titulo, BorderLayout.NORTH); // Va arriba

        String[] columnas = {"Codigo", "Nombre", "Materia"}; // Encabezados de la tabla
        modelo = new DefaultTableModel(columnas, 0) { // Tabla vacia al empezar (0 filas)
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false; // Evita que se editen las celdas haciendo doble click
            }
        };
        JTable tabla = new JTable(modelo); // La tabla muestra lo que hay en el modelo
        add(new JScrollPane(tabla), BorderLayout.CENTER); // El scroll aparece si hay muchas filas

        JPanel botones = new JPanel(); // Panel para los botones de abajo
        JButton botonCrear = new JButton("Crear comision");
        JButton botonVolver = new JButton("Volver");
        botones.add(botonCrear);
        botones.add(botonVolver);
        add(botones, BorderLayout.SOUTH); // Va abajo

        botonCrear.addActionListener(e -> crearComision()); // Al apretar crea una comision
        botonVolver.addActionListener(e -> ventana.mostrarPantalla("menu")); // Al apretar vuelve al menu
    }

    // Vuelve a llenar la tabla con las comisiones del docente actual
    public void actualizar() {
        modelo.setRowCount(0); // Borra todas las filas para no duplicar
        for (Comision c : gestion.getDocenteActual().getListaComisiones()) {
            modelo.addRow(new Object[]{
                c.getCodigoComision(),
                c.getNombre(),
                c.getMateria().getNombre()
            }); // Agrega una fila por comision
        }
    }

    private void crearComision() {
    JTextField campoNombre = new JTextField();
    JTextField campoMateria = new JTextField();

    JPanel formulario = new JPanel(new GridLayout(2, 2, 5, 5)); // 2 filas y 2 columnas
    formulario.add(new JLabel("Nombre:"));
    formulario.add(campoNombre);
    formulario.add(new JLabel("Materia:"));
    formulario.add(campoMateria);

    int opcion = JOptionPane.showConfirmDialog(this, formulario, "Nueva comision",
            JOptionPane.OK_CANCEL_OPTION); // Muestra el formulario con Aceptar y Cancelar
    if (opcion != JOptionPane.OK_OPTION) {
        return; // Si cancelo no hace nada
    }

    String nombre = campoNombre.getText().trim();
    String materia = campoMateria.getText().trim();

    if (nombre.isEmpty() || materia.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Completá todos los campos.");
        return; // Corta si falta algun dato
    }

    int codigo = 1; // El codigo arranca en 1
    while (gestion.buscarComisionPorCodigo(codigo) != null) {
        codigo++; // Si ya existe prueba con el siguiente hasta encontrar uno libre
    }

    gestion.crearComision(codigo, nombre, new Materia(materia, gestion.getDocenteActual()));
    actualizar(); // Refresca la tabla para que se vea la nueva comision
}