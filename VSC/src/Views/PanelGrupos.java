package Views;

import Controllers.GestionTurnero;
import Models.Alumno;
import Models.Comision;
import Models.Grupo;

import java.awt.*;
import javax.swing.*;

public class PanelGrupos extends JPanel {

    private GestionTurnero gestion;
    private VentanaPrincipal ventana;
    private JTextArea resultados;

    public PanelGrupos(GestionTurnero gestion,
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
            "GESTIÓN DE GRUPOS", SwingConstants.CENTER
        );
        titulo.setFont(new Font("Georgia", Font.BOLD, 32));
        titulo.setForeground(EstiloTurnero.BEIGE);
        contenido.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(6, 1, 0, 12));
        botones.setOpaque(false);
        botones.setPreferredSize(new Dimension(270, 410));

        JButton crear = crearBoton("Crear grupo");
        JButton listar = crearBoton("Listar grupos");
        JButton agregar = crearBoton("Agregar integrante");
        JButton quitar = crearBoton("Quitar integrante");
        JButton cupo = crearBoton("Cambiar cupo");
        JButton volver = crearBoton("Volver a comisiones");

        botones.add(crear);
        botones.add(listar);
        botones.add(agregar);
        botones.add(quitar);
        botones.add(cupo);
        botones.add(volver);

        resultados = new JTextArea();
        resultados.setFont(new Font("Tahoma", Font.PLAIN, 17));
        resultados.setBackground(EstiloTurnero.BEIGE);
        resultados.setForeground(EstiloTurnero.CHOCOLATE);
        resultados.setEditable(false);
        resultados.setLineWrap(true);
        resultados.setWrapStyleWord(true);
        resultados.setMargin(new Insets(15, 15, 15, 15));
        resultados.setText("Seleccioná una operación.");

        JScrollPane scroll = new JScrollPane(resultados);

        JPanel centro = new JPanel(new BorderLayout(20, 0));
        centro.setOpaque(false);
        centro.add(botones, BorderLayout.WEST);
        centro.add(scroll, BorderLayout.CENTER);

        contenido.add(centro, BorderLayout.CENTER);
        add(contenido);

        crear.addActionListener(e -> crearGrupo());
        listar.addActionListener(e -> listarGrupos());
        agregar.addActionListener(e -> agregarIntegrante());
        quitar.addActionListener(e -> quitarIntegrante());
        cupo.addActionListener(e -> cambiarCupo());

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
        boton.setBorderPainted(false);
        boton.setOpaque(true);

        return boton;
    }

    private Integer pedirNumero(String mensaje) {

        String dato = JOptionPane.showInputDialog(this, mensaje);

        if (dato == null) return null;

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
        if (codigo == null) return null;

        if (!gestion.tieneDocenteRegistrado()) {
            mostrarError("Primero debés ingresar un docente.");
            return null;
        }

        if (gestion.buscarComisionPorCodigo(codigo) == null) {
            mostrarError("No existe esa comisión.");
            return null;
        }

        return codigo;
    }

    private Grupo pedirGrupo(int codigo) {

        Integer numero = pedirNumero("Número de grupo:");
        if (numero == null) return null;

        Grupo grupo = gestion.buscarGrupoEnComision(codigo, numero);

        if (grupo == null) {
            mostrarError("No existe ese grupo.");
        }

        return grupo;
    }

    private void crearGrupo() {

        Integer codigo = pedirComision();
        if (codigo == null) return;

        Integer numero = pedirNumero("Número del grupo:");
        if (numero == null) return;

        Integer cupo = pedirNumero("Cupo máximo:");
        if (cupo == null) return;

        if (gestion.buscarGrupoEnComision(codigo, numero) != null) {
            mostrarError("Ya existe un grupo con ese número.");
            return;
        }

        gestion.crearGrupo(codigo, numero, cupo);

        if (gestion.buscarGrupoEnComision(codigo, numero) != null) {
            resultados.setText(
                "Grupo creado correctamente.\n\n" +
                "Comisión: " + codigo +
                "\nGrupo: " + numero +
                "\nCupo máximo: " + cupo
            );
        } else {
            mostrarError("No se pudo crear el grupo.");
        }
    }

    private void listarGrupos() {

        Integer codigo = pedirComision();
        if (codigo == null) return;

        Comision comision = gestion.buscarComisionPorCodigo(codigo);

        if (comision.getListaGrupos().isEmpty()) {
            resultados.setText("No hay grupos registrados.");
            return;
        }

        StringBuilder texto = new StringBuilder();

        texto.append("GRUPOS DE ")
             .append(comision.getNombre())
             .append("\n\n");

        for (Grupo grupo : comision.getListaGrupos()) {

            texto.append("Grupo: ")
                 .append(grupo.getNumero())
                 .append("\nCupo máximo: ")
                 .append(grupo.getCupoMaximo())
                 .append("\nIntegrantes: ")
                 .append(grupo.getlistaAlumnos().size())
                 .append("\n");

            for (Alumno alumno : grupo.getlistaAlumnos()) {
                texto.append(" - ")
                     .append(alumno.getNombreCompleto())
                     .append(" (")
                     .append(alumno.getLegajo())
                     .append(")\n");
            }

            texto.append("-------------------------\n\n");
        }

        resultados.setText(texto.toString());
        resultados.setCaretPosition(0);
    }

    private void agregarIntegrante() {

        Integer codigo = pedirComision();
        if (codigo == null) return;

        Grupo grupo = pedirGrupo(codigo);
        if (grupo == null) return;

        Integer legajo = pedirNumero("Legajo del alumno:");
        if (legajo == null) return;

        Alumno alumno = gestion.buscarAlumnoEnComision(codigo, legajo);

        if (alumno == null) {
            mostrarError("El alumno no existe en esa comisión.");
            return;
        }

        if (grupo.getlistaAlumnos().contains(alumno)) {
            mostrarError("El alumno ya pertenece al grupo.");
            return;
        }

        if (!grupo.tieneDisponibilidad()) {
            mostrarError("El grupo no tiene cupos disponibles.");
            return;
        }

        gestion.agregarAlumnoAGrupo(
            codigo, grupo.getNumero(), legajo
        );

        if (grupo.getlistaAlumnos().contains(alumno)) {
            resultados.setText(
                "Alumno agregado correctamente.\n\n" +
                alumno.getNombreCompleto() +
                "\nGrupo: " + grupo.getNumero()
            );
        } else {
            mostrarError("No se pudo agregar el alumno.");
        }
    }

    private void quitarIntegrante() {

        Integer codigo = pedirComision();
        if (codigo == null) return;

        Grupo grupo = pedirGrupo(codigo);
        if (grupo == null) return;

        Integer legajo = pedirNumero("Legajo del alumno:");
        if (legajo == null) return;

        Alumno alumno = gestion.buscarAlumnoEnComision(codigo, legajo);

        if (alumno == null ||
                !grupo.getlistaAlumnos().contains(alumno)) {
            mostrarError("El alumno no pertenece al grupo.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(
            this,
            "¿Quitar a " + alumno.getNombreCompleto() + "?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmar != JOptionPane.YES_OPTION) return;

        gestion.quitarAlumnoDeGrupo(
            codigo, grupo.getNumero(), legajo
        );

        if (!grupo.getlistaAlumnos().contains(alumno)) {
            resultados.setText(
                "Alumno quitado correctamente del grupo."
            );
        } else {
            mostrarError("No se pudo quitar el alumno.");
        }
    }

    private void cambiarCupo() {

        Integer codigo = pedirComision();
        if (codigo == null) return;

        Grupo grupo = pedirGrupo(codigo);
        if (grupo == null) return;

        Integer nuevoCupo = pedirNumero("Nuevo cupo máximo:");
        if (nuevoCupo == null) return;

        if (nuevoCupo < grupo.getlistaAlumnos().size()) {
            mostrarError(
                "El cupo no puede ser menor a los integrantes actuales."
            );
            return;
        }

        gestion.cambiarCupoMaximo(
            codigo, grupo.getNumero(), nuevoCupo
        );

        if (grupo.getCupoMaximo() == nuevoCupo) {
            resultados.setText(
                "Cupo actualizado correctamente.\n\n" +
                "Grupo: " + grupo.getNumero() +
                "\nNuevo cupo: " + nuevoCupo
            );
        } else {
            mostrarError("No se pudo cambiar el cupo.");
        }
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
