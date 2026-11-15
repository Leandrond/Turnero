
package Views;

import Controllers.GestionTurnero;
import Models.Alumno;
import Models.Comision;
import Models.Turno;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class PanelAlumnos extends JPanel {

    private GestionTurnero gestion;
    private VentanaPrincipal ventana;
    private JTextArea resultados;

    public PanelAlumnos(GestionTurnero gestion,
                        VentanaPrincipal ventana) {

        this.gestion = gestion;
        this.ventana = ventana;

        setLayout(new GridBagLayout());
        setBackground(EstiloTurnero.CHOCOLATE);

        JPanel contenido = new PanelRedondeado();
        contenido.setLayout(new BorderLayout(20, 20));
        contenido.setPreferredSize(new Dimension(850, 550));
        contenido.setBorder(
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        );

        JLabel titulo = new JLabel(
            "GESTIÓN DE ALUMNOS", SwingConstants.CENTER
        );
        titulo.setFont(new Font("Georgia", Font.BOLD, 32));
        titulo.setForeground(EstiloTurnero.BEIGE);
        contenido.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(8, 1, 0, 12));
        botones.setOpaque(false);
        botones.setPreferredSize(new Dimension(265, 400));

        JButton agregar = crearBoton("Agregar alumno");
        JButton listar = crearBoton("Listar alumnos");
        JButton buscar = crearBoton("Buscar alumno");
        JButton baja = crearBoton("Dar de baja");
        JButton notas = crearBoton("Ver calificaciones");
        JButton darPrioridad = crearBoton("Dar prioridad");
        JButton quitarPrioridad = crearBoton("Quitar prioridad");
        JButton volver = crearBoton("Volver a comisiones");

        botones.add(agregar);
        botones.add(listar);
        botones.add(buscar);
        botones.add(baja);
        botones.add(notas);
        botones.add(darPrioridad);
        botones.add(quitarPrioridad);
        botones.add(volver);

        resultados = new JTextArea();
        resultados.setFont(new Font("Tahoma", Font.PLAIN, 18));
        resultados.setForeground(EstiloTurnero.CHOCOLATE);
        resultados.setBackground(EstiloTurnero.BEIGE);
        resultados.setEditable(false);
        resultados.setLineWrap(true);
        resultados.setWrapStyleWord(true);
        resultados.setMargin(new Insets(18, 18, 18, 18));
        resultados.setText("Seleccioná una operación.");

        JScrollPane scroll = new JScrollPane(resultados);

        JPanel centro = new JPanel(new BorderLayout(20, 0));
        centro.setOpaque(false);
        centro.add(botones, BorderLayout.WEST);
        centro.add(scroll, BorderLayout.CENTER);

        contenido.add(centro, BorderLayout.CENTER);
        add(contenido);

        agregar.addActionListener(e -> agregarAlumno());
        listar.addActionListener(e -> listarAlumnos());
        buscar.addActionListener(e -> buscarAlumno());
        baja.addActionListener(e -> darDeBaja());
        notas.addActionListener(e -> verCalificaciones());
        darPrioridad.addActionListener(e -> cambiarPrioridad(true));
        quitarPrioridad.addActionListener(e -> cambiarPrioridad(false));

        volver.addActionListener(e ->
            ventana.mostrarPantalla("comisiones")
        );
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

    private Integer pedirNumero(String mensaje) {

        String dato = JOptionPane.showInputDialog(this, mensaje);

        if (dato == null) {
            return null;
        }

        try {
            int numero = Integer.parseInt(dato.trim());

            if (numero <= 0) {
                mostrarError("Ingresá un número positivo.");
                return null;
            }

            return numero;

        } catch (NumberFormatException ex) {
            mostrarError("Ingresá un número entero válido.");
            return null;
        }
    }

    private Integer pedirComision() {

        Integer codigo = pedirNumero("Código de comisión:");

        if (codigo == null) {
            return null;
        }

        if (gestion.buscarComisionPorCodigo(codigo) == null) {
            mostrarError("No existe esa comisión.");
            return null;
        }

        return codigo;
    }

    private void agregarAlumno() {

        Integer codigo = pedirComision();
        if (codigo == null) return;

        JTextField legajo = new JTextField();
        JTextField nombre = new JTextField();
        JTextField apellido = new JTextField();

        JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 12));
        formulario.add(new JLabel("Legajo:"));
        formulario.add(legajo);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(nombre);
        formulario.add(new JLabel("Apellido:"));
        formulario.add(apellido);

        int opcion = JOptionPane.showConfirmDialog(
            this, formulario, "Agregar alumno",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            int numero = Integer.parseInt(legajo.getText().trim());
            String nom = nombre.getText().trim();
            String ape = apellido.getText().trim();

            if (numero <= 0 || nom.isEmpty() || ape.isEmpty()) {
                mostrarError("Completá los datos correctamente.");
                return;
            }

            if (gestion.buscarAlumnoEnComision(codigo, numero) != null) {
                mostrarError("Ese alumno ya está registrado.");
                return;
            }

            gestion.agregarAlumno(codigo, numero, nom, ape);

            if (gestion.buscarAlumnoEnComision(codigo, numero) != null) {
                resultados.setText(
                    "Alumno registrado correctamente:\n\n" +
                    nom + " " + ape + "\nLegajo: " + numero
                );
            } else {
                mostrarError("No se pudo agregar el alumno.");
            }

        } catch (NumberFormatException ex) {
            mostrarError("El legajo debe ser un número entero.");
        }
    }

    private void listarAlumnos() {

        Integer codigo = pedirComision();
        if (codigo == null) return;

        Comision comision = gestion.buscarComisionPorCodigo(codigo);

        if (comision.getListaAlumnos().isEmpty()) {
            resultados.setText("No hay alumnos en esta comisión.");
            return;
        }

        StringBuilder texto = new StringBuilder();
        texto.append("ALUMNOS DE ")
             .append(comision.getNombre())
             .append("\n\n");

        for (Alumno alumno : comision.getListaAlumnos()) {
            texto.append(alumno.getNombreCompleto())
                 .append("\nLegajo: ")
                 .append(alumno.getLegajo())
                 .append("\n--------------------\n");
        }

        resultados.setText(texto.toString());
        resultados.setCaretPosition(0);
    }

    private void buscarAlumno() {

        Integer codigo = pedirComision();
        if (codigo == null) return;

        Integer legajo = pedirNumero("Legajo del alumno:");
        if (legajo == null) return;

        Alumno alumno = gestion.buscarAlumnoEnComision(codigo, legajo);

        if (alumno == null) {
            resultados.setText("Alumno no encontrado.");
            return;
        }

        resultados.setText(
            "ALUMNO ENCONTRADO\n\n" +
            "Nombre: " + alumno.getNombreCompleto() +
            "\nLegajo: " + alumno.getLegajo() +
            "\nPrioridad: " +
            (alumno.getPrioridad() ? "Sí" : "No")
        );
    }

    private void darDeBaja() {

        Integer codigo = pedirComision();
        if (codigo == null) return;

        Integer legajo = pedirNumero("Legajo del alumno:");
        if (legajo == null) return;

        Alumno alumno = gestion.buscarAlumnoEnComision(codigo, legajo);

        if (alumno == null) {
            mostrarError("No se encontró el alumno.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(
            this,
            "¿Dar de baja a " + alumno.getNombreCompleto() + "?",
            "Confirmar baja",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmar != JOptionPane.YES_OPTION) return;

        gestion.darDeBajaAlumno(codigo, legajo);

        if (gestion.buscarAlumnoEnComision(codigo, legajo) == null) {
            resultados.setText("Alumno dado de baja correctamente.");
        } else {
            mostrarError("No se pudo dar de baja al alumno.");
        }
    }

    private void verCalificaciones() {

        Integer codigo = pedirComision();
        if (codigo == null) return;

        Integer legajo = pedirNumero("Legajo del alumno:");
        if (legajo == null) return;

        Alumno alumno = gestion.buscarAlumnoEnComision(codigo, legajo);

        if (alumno == null) {
            mostrarError("No se encontró el alumno.");
            return;
        }

        ArrayList<Turno> turnos =
            gestion.obtenerCalificacionesAlumno(codigo, legajo);

        if (turnos.isEmpty()) {
            resultados.setText("No hay calificaciones registradas.");
            return;
        }

        StringBuilder texto = new StringBuilder(
            "CALIFICACIONES\n" +
            alumno.getNombreCompleto() + "\n\n"
        );

        for (Turno turno : turnos) {
            texto.append("Nota: ")
                 .append(turno.getNota())
                 .append("\n--------------------\n");
        }

        resultados.setText(texto.toString());
    }

    private void cambiarPrioridad(boolean nuevaPrioridad) {

        Integer codigo = pedirComision();
        if (codigo == null) return;

        Integer legajo = pedirNumero("Legajo del alumno:");
        if (legajo == null) return;

        Alumno alumno = gestion.buscarAlumnoEnComision(codigo, legajo);

        if (alumno == null) {
            mostrarError("No se encontró el alumno.");
            return;
        }

        if (alumno.getPrioridad() == nuevaPrioridad) {
            resultados.setText(
                nuevaPrioridad
                    ? "El alumno ya tiene prioridad."
                    : "El alumno ya está sin prioridad."
            );
            return;
        }

        gestion.cambiarPrioridad(codigo, legajo, nuevaPrioridad);

        resultados.setText(
            "Alumno: " + alumno.getNombreCompleto() +
            "\nLegajo: " + alumno.getLegajo() +
            "\nPrioridad: " +
            (alumno.getPrioridad() ? "Sí" : "No")
        );
    }


    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
            this, mensaje, "Aviso",
            JOptionPane.WARNING_MESSAGE
        );
    }

    private static class PanelRedondeado extends JPanel {

        public PanelRedondeado() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(EstiloTurnero.MARRON);
            g2.fillRoundRect(
                2, 2, getWidth() - 4, getHeight() - 4,
                30, 30
            );

            g2.setColor(EstiloTurnero.BEIGE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(
                2, 2, getWidth() - 5, getHeight() - 5,
                30, 30
            );

            g2.dispose();
        }
    }
}

