
package Views;

import Controllers.GestionTurnero;
import Models.Comision;
import Models.Materia;

import java.awt.*;
import javax.swing.*;

public class PanelComisiones extends JPanel {

    private GestionTurnero gestion;
    private VentanaPrincipal ventana;
    private JTextArea areaResultados;

    public PanelComisiones(GestionTurnero gestion,
                           VentanaPrincipal ventana) {

        this.gestion = gestion;
        this.ventana = ventana;

        setLayout(new GridBagLayout());
        setBackground(EstiloTurnero.CHOCOLATE);

        JPanel contenido = new JPanel(new BorderLayout(15, 20));
        contenido.setBackground(EstiloTurnero.MARRON);
        contenido.setPreferredSize(new Dimension(790, 540));
        contenido.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(EstiloTurnero.BEIGE, 2),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        JLabel titulo = new JLabel(
            "GESTIÓN DE COMISIONES",
            SwingConstants.CENTER
        );
        titulo.setFont(new Font("Georgia", Font.BOLD, 30));
        titulo.setForeground(EstiloTurnero.BEIGE);

        contenido.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(5, 1, 0, 15));
        botones.setOpaque(false);

        JButton botonCrear = crearBoton("Crear comisión");
        JButton botonListar = crearBoton("Listar comisiones");
        JButton botonVolver = crearBoton("Volver al menú");
        JButton botonAlumnos = crearBoton("Gestionar alumnos");
        JButton botonGrupos = crearBoton("Gestionar grupos");


        botones.add(botonCrear);
        botones.add(botonListar);
        botones.add(botonAlumnos);
        botones.add(botonGrupos);
        botones.add(botonVolver);

        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false);
        botones.setPreferredSize(new Dimension(260, 220));
        panelBotones.add(botones);

        areaResultados = new JTextArea();
        areaResultados.setFont(new Font("Tahoma", Font.PLAIN, 16));
        areaResultados.setBackground(EstiloTurnero.BEIGE);
        areaResultados.setForeground(EstiloTurnero.CHOCOLATE);
        areaResultados.setEditable(false);
        areaResultados.setLineWrap(true);
        areaResultados.setWrapStyleWord(true);
        areaResultados.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scroll = new JScrollPane(areaResultados);
        scroll.setPreferredSize(new Dimension(340, 250));

        JPanel centro = new JPanel(new BorderLayout(15, 0));
        centro.setOpaque(false);

        centro.add(panelBotones, BorderLayout.WEST);
        centro.add(scroll, BorderLayout.CENTER);

        contenido.add(centro, BorderLayout.CENTER);

        botonCrear.addActionListener(e -> crearComision());
        botonListar.addActionListener(e -> listarComisiones());
        botonAlumnos.addActionListener(e -> ventana.mostrarPantalla("alumnos"));      
        botonVolver.addActionListener(e -> ventana.mostrarPantalla("menu"));
        botonGrupos.addActionListener(e -> ventana.mostrarPantalla("grupos"));

        add(contenido);
    }

    private JButton crearBoton(String texto) {

        JButton boton = new JButton(texto);

        boton.setFont(new Font("Georgia", Font.BOLD, 17));
        boton.setBackground(EstiloTurnero.VERDE_MUSGO);
        boton.setForeground(EstiloTurnero.BEIGE);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false);

        return boton;
    }

    private void crearComision() {

        if (!gestion.tieneDocenteRegistrado()) {
            JOptionPane.showMessageDialog(
                this, "Primero debe ingresar un docente."
            );
            return;
        }

        JTextField campoCodigo = new JTextField();
        JTextField campoNombre = new JTextField();
        JTextField campoMateria = new JTextField();

        JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));

        formulario.add(new JLabel("Código:"));
        formulario.add(campoCodigo);

        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);

        formulario.add(new JLabel("Materia:"));
        formulario.add(campoMateria);

        int opcion = JOptionPane.showConfirmDialog(
            this,
            formulario,
            "Crear comisión",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        try {
            int codigo = Integer.parseInt(
                campoCodigo.getText().trim()
            );

            String nombre = campoNombre.getText().trim();
            String nombreMateria = campoMateria.getText().trim();

            if (codigo <= 0 || nombre.isEmpty()
                    || nombreMateria.isEmpty()) {

                JOptionPane.showMessageDialog(
                    this, "Ingresá datos válidos."
                );
                return;
            }

            if (gestion.buscarComisionPorCodigo(codigo) != null) {
                JOptionPane.showMessageDialog(
                    this, "Ya existe una comisión con ese código."
                );
                return;
            }

            Materia materia = new Materia(
                nombreMateria,
                gestion.getDocenteActual()
            );

            gestion.crearComision(codigo, nombre, materia);

            if (gestion.buscarComisionPorCodigo(codigo) != null) {
                JOptionPane.showMessageDialog(
                    this, "Comisión creada correctamente."
                );
                listarComisiones();
            } else {
                JOptionPane.showMessageDialog(
                    this, "No se pudo crear la comisión."
                );
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "El código debe ser un número entero."
            );
        }
    }

    private void listarComisiones() {

        if (!gestion.tieneDocenteRegistrado()) {
            areaResultados.setText(
                "Primero debe ingresar un docente."
            );
            return;
        }

        StringBuilder resultado = new StringBuilder();

        if (gestion.getDocenteActual()
                .getListaComisiones().isEmpty()) {

            areaResultados.setText(
                "No hay comisiones registradas."
            );
            return;
        }

        resultado.append("COMISIONES REGISTRADAS\n\n");

        for (Comision comision :
                gestion.getDocenteActual().getListaComisiones()) {

            resultado.append("Código: ")
                     .append(comision.getCodigoComision())
                     .append("\n");

            resultado.append("Nombre: ")
                     .append(comision.getNombre())
                     .append("\n");

            resultado.append("Materia: ")
                     .append(comision.getMateria().getNombre())
                     .append("\n");

            resultado.append("-------------------------\n");
        }

        areaResultados.setText(resultado.toString());
        areaResultados.setCaretPosition(0);
    }
}

