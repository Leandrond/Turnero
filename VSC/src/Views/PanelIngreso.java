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

    private GestionTurnero gestion; // GestionTurnero que se usa para registrar docentes y obtener el docente actual
    private JTextField campoNombre; // Campo de texto para ingresar el nombre del docente
    private JTextField campoApellido;// Campo de texto para ingresar el apellido del docente
    private JTextField campoLegajo; // Campo de texto para ingresar el legajo del docente
    private VentanaPrincipal ventana; // VentanaPrincipal que se usa para cambiar de pantalla
    
    public PanelIngreso(GestionTurnero gestion, VentanaPrincipal ventana) {
        this.gestion = gestion;// Inicializa la gestion que se usa para registrar docentes y obtener el docente actual
        this.ventana = ventana;// Inicializa la ventana que se usa para cambiar de pantalla
        setLayout(new GridBagLayout()); // centra en la pantalla lo que tenga adentro

        // Formulario con tamaño fijo, asi no se estira con la ventana
        JPanel formulario = new JPanel(new GridLayout(4, 2, 10, 10));// Crea un formulario con 4 filas y 2 columnas, con un espacio de 10 pixeles entre cada componente
        formulario.setPreferredSize(new Dimension(400, 200));// Establece el tamaño preferido del formulario para que no se estire con la ventana

        formulario.add(new JLabel("Nombre:"));
        campoNombre = new JTextField();
        formulario.add(campoNombre);// Agrega el campo de texto para ingresar el nombre al formulario

        formulario.add(new JLabel("Apellido:"));
        campoApellido = new JTextField();
        formulario.add(campoApellido);// Agrega el campo de texto para ingresar el apellido al formulario

        formulario.add(new JLabel("Legajo:"));
        campoLegajo = new JTextField();
        formulario.add(campoLegajo);// Agrega el campo de texto para ingresar el legajo al formulario

        JButton botonIngresar = new JButton("Ingresar");
        formulario.add(botonIngresar);// Agrega el boton de ingresar al formulario

        botonIngresar.addActionListener(e -> ingresar());// Agrega el evento de click al boton de ingresar

        add(formulario);
    }

    private void ingresar() {
        String nombre = campoNombre.getText().trim();// Obtiene el texto ingresado en el campo de nombre y elimina los espacios en blanco al inicio y al final
        String apellido = campoApellido.getText().trim();// Obtiene el texto ingresado en el campo de apellido y elimina los espacios en blanco al inicio y al final
        String legajo = campoLegajo.getText().trim();// Obtiene el texto ingresado en el campo de legajo y elimina los espacios en blanco al inicio y al final

        if (nombre.isEmpty() || apellido.isEmpty() || legajo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completá todos los campos.");// Muestra un mensaje de error si alguno de los campos esta vacio
            return;
        }

        gestion.registrarDocente(nombre, apellido, legajo);// Registra el docente con los datos ingresados
        JOptionPane.showMessageDialog(this, "Docente registrado: " + gestion.getDocenteActual().getNombreCompleto());// Muestra un mensaje de confirmacion con el nombre completo del docente registrado
        ventana.mostrarPantalla("menu");// Cambia a la pantalla del menu principal
    }

}