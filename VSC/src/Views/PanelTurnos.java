
package Views;

import Controllers.GestionTurnero;
import Models.Turnero;
import Models.Turno;
import Models.Estado;
import Models.Modalidad;

import java.awt.*;
import javax.swing.*;

public class PanelTurnos extends JPanel {

    private GestionTurnero gestion;
    private VentanaPrincipal ventana;
    private JTextArea resultados;

    private int codigoComision;
    private int codigoInstancia;
    private Turnero turneroActual;

    public PanelTurnos(GestionTurnero gestion,
                       VentanaPrincipal ventana) {

        this.gestion = gestion;
        this.ventana = ventana;

        setLayout(new GridBagLayout());
        setBackground(EstiloTurnero.CHOCOLATE);

        JPanel contenido = new JPanel(new BorderLayout(20, 20));
        contenido.setBackground(EstiloTurnero.MARRON);
        contenido.setPreferredSize(new Dimension(850, 550));
        contenido.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(EstiloTurnero.BEIGE, 2),
            BorderFactory.createEmptyBorder(30, 35, 30, 35)
        ));

        JLabel titulo = new JLabel(
            "GESTIÓN DE TURNOS", SwingConstants.CENTER
        );
        titulo.setFont(new Font("Georgia", Font.BOLD, 32));
        titulo.setForeground(EstiloTurnero.BEIGE);
        contenido.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(7, 1, 0, 9));
        botones.setOpaque(false);
        botones.setPreferredSize(new Dimension(260, 400));

        JButton seleccionar = crearBoton("Seleccionar instancia");
        JButton listar = crearBoton("Ver turnos");
        JButton iniciar = crearBoton("Iniciar evaluación");
        JButton finalizar = crearBoton("Finalizar turno");
        JButton ausente = crearBoton("Marcar ausente");
        JButton orden = crearBoton("Cambiar orden");
        JButton volver = crearBoton("Volver a instancias");

        botones.add(seleccionar);
        botones.add(listar);
        botones.add(iniciar);
        botones.add(finalizar);
        botones.add(ausente);
        botones.add(orden);
        botones.add(volver);

        resultados = new JTextArea();
        resultados.setFont(new Font("Tahoma", Font.PLAIN, 17));
        resultados.setBackground(EstiloTurnero.BEIGE);
        resultados.setForeground(EstiloTurnero.CHOCOLATE);
        resultados.setEditable(false);
        resultados.setLineWrap(true);
        resultados.setWrapStyleWord(true);
        resultados.setMargin(new Insets(15, 15, 15, 15));
        resultados.setText("Seleccioná una instancia evaluativa.");

        JPanel centro = new JPanel(new BorderLayout(20, 0));
        centro.setOpaque(false);
        centro.add(botones, BorderLayout.WEST);
        centro.add(new JScrollPane(resultados), BorderLayout.CENTER);

        contenido.add(centro, BorderLayout.CENTER);
        add(contenido);

        seleccionar.addActionListener(e -> seleccionarInstancia());
        listar.addActionListener(e -> listarTurnos());
        iniciar.addActionListener(e -> iniciarTurno());
        finalizar.addActionListener(e -> finalizarTurno());
        ausente.addActionListener(e -> marcarAusente());
        orden.addActionListener(e -> cambiarOrden());

        volver.addActionListener(e ->
            ventana.mostrarPantalla("instancias")
        );
    }

    private JButton crearBoton(String texto) {

        JButton boton = new JButton(texto);
        boton.setFont(new Font("Georgia", Font.BOLD, 16));
        boton.setBackground(EstiloTurnero.VERDE_MUSGO);
        boton.setForeground(EstiloTurnero.BEIGE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);

        return boton;
    }

    private Integer pedirNumero(String mensaje) {

        String valor = JOptionPane.showInputDialog(this, mensaje);

        if (valor == null) return null;

        try {
            int numero = Integer.parseInt(valor.trim());

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

    private void seleccionarInstancia() {

        Integer comision = pedirNumero("Código de comisión:");
        if (comision == null) return;

        if (gestion.buscarComisionPorCodigo(comision) == null) {
            mostrarError("No existe esa comisión.");
            return;
        }

        Integer instancia = pedirNumero("Código de instancia:");
        if (instancia == null) return;

        Turnero encontrado =
            gestion.obtenerTurnero(comision, instancia);

        if (encontrado == null) {
            mostrarError("No existe un turnero para esa instancia.");
            return;
        }

        codigoComision = comision;
        codigoInstancia = instancia;
        turneroActual = encontrado;

        listarTurnos();
    }

    private boolean validarSeleccion() {

        if (turneroActual == null) {
            mostrarError("Primero seleccioná una instancia.");
            return false;
        }

        return true;
    }

    private void listarTurnos() {

        if (!validarSeleccion()) return;

        StringBuilder texto = new StringBuilder();

        texto.append("COMISIÓN: ")
             .append(codigoComision)
             .append("\nINSTANCIA: ")
             .append(codigoInstancia)
             .append("\n\n");

        if (turneroActual.getListaTurno().isEmpty()) {
            resultados.setText("No hay turnos registrados.");
            return;
        }

        int posicion = 1;

        for (Turno turno : turneroActual.getListaTurno()) {

            texto.append("Posición: ")
                 .append(posicion++)
                 .append("\nTurno N.º: ")
                 .append(turno.getNumero())
                 .append("\n");

            if (turno.getAlumno() != null) {
                texto.append("Alumno: ")
                     .append(turno.getAlumno().getNombreCompleto())
                     .append("\n");
            } else if (turno.getGrupo() != null) {
                texto.append("Grupo: ")
                     .append(turno.getGrupo().getNumero())
                     .append("\n");
            } else {
                texto.append("Evaluación general\n");
            }

            texto.append("Fecha y hora: ")
                 .append(turno.getFechaHora())
                 .append("\nEstado: ")
                 .append(turno.getEstado())
                 .append("\n");

            if (turno.getNota() != null) {
                texto.append("Nota: ")
                     .append(turno.getNota())
                     .append("\n");
            }

            texto.append("------------------------\n\n");
        }

        resultados.setText(texto.toString());
        resultados.setCaretPosition(0);
    }

    private void iniciarTurno() {

        if (!validarSeleccion()) return;

        Turno turno = turneroActual.iniciarOContinuar();

        if (turno == null) {
            mostrarError("No hay turnos pendientes.");
            return;
        }

        listarTurnos();

        JOptionPane.showMessageDialog(
            this,
            "Turno N.º " + turno.getNumero() + " en evaluación."
        );
    }

    private void finalizarTurno() {

        if (!validarSeleccion()) return;

        Turno actual = turneroActual.getTurnoActual();

        if (actual == null) {
            mostrarError("No hay ningún turno en evaluación.");
            return;
        }

        Integer nota = null;

        if (actual.getInstancia().getModalidad()
                != Modalidad.GENERAL) {

            nota = pedirNumero("Calificación (1 a 10):");
            if (nota == null) return;

            if (nota > 10) {
                mostrarError("La nota debe estar entre 1 y 10.");
                return;
            }
        }

        gestion.siguienteTurno(
            codigoComision, codigoInstancia, nota
        );

        listarTurnos();
    }

    private void marcarAusente() {

        if (!validarSeleccion()) return;

        Turno actual = turneroActual.getTurnoActual();

        if (actual == null) {
            mostrarError("No hay ningún turno en evaluación.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Marcar ausente el turno N.º " +
                actual.getNumero() + "?",
            "Confirmar ausencia",
            JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) return;

        gestion.ponerAusente(
            codigoComision, codigoInstancia
        );

        listarTurnos();
    }

    private void cambiarOrden() {

        if (!validarSeleccion()) return;

        Integer numero = pedirNumero("Número del turno:");
        if (numero == null) return;

        Integer posicion = pedirNumero(
            "Nueva posición (comienza en 1):"
        );
        if (posicion == null) return;

        Turno seleccionado = null;

        for (Turno turno : turneroActual.getListaTurno()) {
            if (turno.getNumero() == numero) {
                seleccionado = turno;
                break;
            }
        }

        if (seleccionado == null) {
            mostrarError("No existe ese turno.");
            return;
        }

        if (seleccionado.getEstado() != Estado.PENDIENTE) {
            mostrarError("Solo podés mover turnos pendientes.");
            return;
        }

        int destino = posicion - 1;

        if (destino >= turneroActual.getListaTurno().size()) {
            mostrarError("La posición no existe.");
            return;
        }

        for (int i = Math.min(
                turneroActual.getListaTurno().indexOf(seleccionado),
                destino);
                i <= Math.max(
                turneroActual.getListaTurno().indexOf(seleccionado),
                destino); i++) {

            if (turneroActual.getListaTurno().get(i)
                    .getEstado() != Estado.PENDIENTE) {

                mostrarError(
                    "No se pueden mover turnos ya iniciados."
                );
                return;
            }
        }

        gestion.cambiarOrdenTurno(
            codigoComision, codigoInstancia,
            numero, destino
        );

        listarTurnos();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
            this, mensaje, "Aviso",
            JOptionPane.WARNING_MESSAGE
        );
    }
}

