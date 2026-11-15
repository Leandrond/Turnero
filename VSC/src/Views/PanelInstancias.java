
package Views;

import Controllers.GestionTurnero;
import Models.Comision;
import Models.Modalidad;
import Models.Turnero;
import Models.Instancias.InstanciaEvaluativa;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.*;

public class PanelInstancias extends JPanel {

    private GestionTurnero gestion;
    private VentanaPrincipal ventana;
    private JTextArea resultados;

    public PanelInstancias(GestionTurnero gestion,
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
            "INSTANCIAS EVALUATIVAS",
            SwingConstants.CENTER
        );
        titulo.setFont(new Font("Georgia", Font.BOLD, 31));
        titulo.setForeground(EstiloTurnero.BEIGE);
        contenido.add(titulo, BorderLayout.NORTH);

        JPanel botones = new JPanel(new GridLayout(6, 1, 0, 12));
        botones.setOpaque(false);
        botones.setPreferredSize(new Dimension(270, 390));

        JButton crear = crearBoton("Crear instancia");
        JButton listar = crearBoton("Listar instancias");
        JButton fecha = crearBoton("Modificar fecha");
        JButton hora = crearBoton("Modificar horario");
        JButton turnos = crearBoton("Gestionar turnos");
        JButton volver = crearBoton("Volver al menú");

        botones.add(crear);
        botones.add(listar);
        botones.add(fecha);
        botones.add(hora);
        botones.add(turnos);
        botones.add(volver);

        resultados = new JTextArea();
        resultados.setFont(new Font("Tahoma", Font.PLAIN, 17));
        resultados.setBackground(EstiloTurnero.BEIGE);
        resultados.setForeground(EstiloTurnero.CHOCOLATE);
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

        crear.addActionListener(e -> crearInstancia());
        listar.addActionListener(e -> listarInstancias());
        fecha.addActionListener(e -> modificarFecha());
        hora.addActionListener(e -> modificarHora());
        turnos.addActionListener(e -> ventana.mostrarPantalla("turnos"));


        volver.addActionListener(e ->
            ventana.mostrarPantalla("menu")
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

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
            this, mensaje, "Aviso",
            JOptionPane.WARNING_MESSAGE
        );
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

    private Comision pedirComision() {

        if (!gestion.tieneDocenteRegistrado()) {
            mostrarError("Primero debés registrar un docente.");
            return null;
        }

        Integer codigo = pedirNumero("Código de comisión:");
        if (codigo == null) return null;

        Comision comision = gestion.buscarComisionPorCodigo(codigo);

        if (comision == null) {
            mostrarError("No existe esa comisión.");
        }

        return comision;
    }

    private InstanciaEvaluativa buscarInstancia(
            Comision comision, int codigo) {

        for (InstanciaEvaluativa instancia :
                comision.getListaInstanciasEvaluativas()) {

            if (instancia.getCodigo() == codigo) {
                return instancia;
            }
        }

        return null;
    }

    private void crearInstancia() {

        Comision comision = pedirComision();
        if (comision == null) return;

        JComboBox<String> tipos = new JComboBox<>(
            new String[] {
                "Defensa parcial",
                "Examen final",
                "Presentación TPI"
            }
        );

        JComboBox<Modalidad> modalidades =
            new JComboBox<>(Modalidad.values());

        JTextField campoFecha = new JTextField("2026-11-20");
        JTextField campoHora = new JTextField("09:00");
        JTextField campoDuracion = new JTextField("20");
        JTextField campoCodigo = new JTextField();

        JPanel formulario = new JPanel(
            new GridLayout(6, 2, 10, 12)
        );

        formulario.add(new JLabel("Tipo:"));
        formulario.add(tipos);

        formulario.add(new JLabel("Modalidad:"));
        formulario.add(modalidades);

        formulario.add(new JLabel("Fecha (AAAA-MM-DD):"));
        formulario.add(campoFecha);

        formulario.add(new JLabel("Hora (HH:MM):"));
        formulario.add(campoHora);

        formulario.add(new JLabel("Duración en minutos:"));
        formulario.add(campoDuracion);

        formulario.add(new JLabel("Código de instancia:"));
        formulario.add(campoCodigo);

        int opcion = JOptionPane.showConfirmDialog(
            this, formulario, "Crear instancia evaluativa",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (opcion != JOptionPane.OK_OPTION) return;

        try {
            String fecha = campoFecha.getText().trim();
            String hora = campoHora.getText().trim();

            LocalDate.parse(fecha);
            LocalTime.parse(hora);

            double duracion = Double.parseDouble(
                campoDuracion.getText().trim()
            );

            int codigo = Integer.parseInt(
                campoCodigo.getText().trim()
            );

            if (codigo <= 0 || !Double.isFinite(duracion)
                    || duracion < 1 || duracion > 1440) {

                mostrarError("Revisá el código y la duración.");
                return;
            }

            Modalidad modalidad =
                (Modalidad) modalidades.getSelectedItem();

            Turnero turnero = new Turnero();
            boolean creada = false;

            switch (tipos.getSelectedIndex()) {

                case 0:
                    creada = gestion.crearDefensaParcial(
                        comision, fecha, hora, duracion,
                        codigo, gestion.getDocenteActual(),
                        turnero, modalidad
                    );
                    break;

                case 1:
                    creada = gestion.crearExamenFinal(
                        comision, fecha, hora, duracion,
                        codigo, gestion.getDocenteActual(),
                        turnero, modalidad
                    );
                    break;

                case 2:
                    creada = gestion.crearPresentacionTPI(
                        comision, fecha, hora, duracion,
                        codigo, gestion.getDocenteActual(),
                        turnero, modalidad
                    );
                    break;
            }

            if (creada) {
                resultados.setText(
                    "Instancia creada correctamente.\n\n" +
                    "Código: " + codigo +
                    "\nTipo: " + tipos.getSelectedItem() +
                    "\nModalidad: " + modalidad +
                    "\nFecha: " + fecha +
                    "\nHora: " + hora
                );
            } else {
                mostrarError(
                    "No se pudo crear la instancia.\n" +
                    "Verificá que el código no esté repetido " +
                    "y que haya alumnos o grupos disponibles."
                );
            }

        } catch (NumberFormatException |
                 java.time.format.DateTimeParseException ex) {

            mostrarError("Fecha, hora, código o duración inválidos.");
        }
    }

    private void listarInstancias() {

        Comision comision = pedirComision();
        if (comision == null) return;

        if (comision.getListaInstanciasEvaluativas().isEmpty()) {
            resultados.setText(
                "No hay instancias evaluativas registradas."
            );
            return;
        }

        StringBuilder texto = new StringBuilder();
        texto.append("INSTANCIAS EVALUATIVAS\n\n");

        for (InstanciaEvaluativa instancia :
                comision.getListaInstanciasEvaluativas()) {

            texto.append("Código: ")
                 .append(instancia.getCodigo())
                 .append("\nTipo: ")
                 .append(instancia.getTipo())
                 .append("\nModalidad: ")
                 .append(instancia.getModalidad())
                 .append("\nFecha: ")
                 .append(instancia.getFecha())
                 .append("\nHora: ")
                 .append(instancia.getHoraInicio())
                 .append("\nDuración: ")
                 .append(instancia.getDuracionEstimada())
                 .append(" minutos\n")
                 .append("-------------------------\n\n");
        }

        resultados.setText(texto.toString());
        resultados.setCaretPosition(0);
    }

    private void modificarFecha() {

        Comision comision = pedirComision();
        if (comision == null) return;

        Integer codigo = pedirNumero("Código de instancia:");
        if (codigo == null) return;

        InstanciaEvaluativa instancia =
            buscarInstancia(comision, codigo);

        if (instancia == null) {
            mostrarError("No existe esa instancia.");
            return;
        }

        if (instancia.getTurnero().estaIniciado()) {
            mostrarError("La evaluación ya comenzó.");
            return;
        }

        String fecha = JOptionPane.showInputDialog(
            this, "Nueva fecha (AAAA-MM-DD):"
        );

        if (fecha == null) return;

        try {
            LocalDate nuevaFecha = LocalDate.parse(fecha.trim());

            gestion.modificarFechaInstancia(
                comision.getCodigoComision(),
                codigo, nuevaFecha.toString()
            );

            resultados.setText(
                "Fecha actual de la instancia " + codigo +
                ": " + instancia.getFecha()
            );

        } catch (java.time.format.DateTimeParseException ex) {
            mostrarError("La fecha no es válida.");
        }
    }

    private void modificarHora() {

        Comision comision = pedirComision();
        if (comision == null) return;

        Integer codigo = pedirNumero("Código de instancia:");
        if (codigo == null) return;

        InstanciaEvaluativa instancia =
            buscarInstancia(comision, codigo);

        if (instancia == null) {
            mostrarError("No existe esa instancia.");
            return;
        }

        if (instancia.getTurnero().estaIniciado()) {
            mostrarError("La evaluación ya comenzó.");
            return;
        }

        String hora = JOptionPane.showInputDialog(
            this, "Nueva hora (HH:MM):"
        );

        if (hora == null) return;

        try {
            LocalTime nuevaHora = LocalTime.parse(hora.trim());

            gestion.modificarHoraInstancia(
                comision.getCodigoComision(),
                codigo, nuevaHora.toString()
            );

            resultados.setText(
                "Hora actual de la instancia " + codigo +
                ": " + instancia.getHoraInicio()
            );

        } catch (java.time.format.DateTimeParseException ex) {
            mostrarError("El horario no es válido.");
        }
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
