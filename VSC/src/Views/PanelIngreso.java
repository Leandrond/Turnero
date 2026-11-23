
package Views;

import Controllers.GestionTurnero;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;
import javax.swing.*;

public class PanelIngreso extends JPanel {

    private GestionTurnero gestion;
    private VentanaPrincipal ventana;

    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoLegajo;

    public PanelIngreso(GestionTurnero gestion, VentanaPrincipal ventana) {

        this.gestion = gestion;
        this.ventana = ventana;

        setLayout(new GridBagLayout());
        setBackground(EstiloTurnero.CHOCOLATE);

        JPanel contenido = new PanelRedondeado(EstiloTurnero.MARRON);
        contenido.setLayout(new BorderLayout(0, 10));
        contenido.setPreferredSize(new Dimension(790, 540));
        contenido.setBorder(
            BorderFactory.createEmptyBorder(20, 45, 25, 45)
        );

        // Encabezado
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);
        encabezado.setPreferredSize(new Dimension(700, 135));

        JPanel decoracion = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );

                int centro = getWidth() / 2;

                g2.setColor(EstiloTurnero.VERDE_MUSGO);
                g2.fillRoundRect(centro - 220, 34, 130, 5, 5, 5);
                g2.fillRoundRect(centro + 90, 34, 130, 5, 5, 5);

                // Birrete
                g2.setColor(EstiloTurnero.BEIGE);

                int[] x = {
                    centro - 48, centro, centro + 48, centro
                };
                int[] y = {31, 9, 31, 51};

                g2.fillPolygon(x, y, 4);
                g2.fillRoundRect(centro - 27, 44, 54, 13, 5, 5);

                g2.setStroke(new BasicStroke(3));
                g2.drawLine(centro + 43, 32, centro + 43, 63);
                g2.fillOval(centro + 38, 61, 10, 10);

                g2.dispose();
            }
        };

        decoracion.setOpaque(false);
        decoracion.setPreferredSize(new Dimension(700, 78));

        JLabel titulo = new JLabel(
            "INGRESO DE DOCENTE",
            SwingConstants.CENTER
        );
        titulo.setFont(new Font("Georgia", Font.BOLD, 34));
        titulo.setForeground(EstiloTurnero.BEIGE);

        encabezado.add(decoracion, BorderLayout.NORTH);
        encabezado.add(titulo, BorderLayout.CENTER);

        contenido.add(encabezado, BorderLayout.NORTH);

        // Formulario
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setOpaque(false);

        campoNombre = crearCampo();
        campoApellido = crearCampo();
        campoLegajo = crearCampo();

        agregarFila(formulario, "Nombre:", campoNombre, 0);
        agregarFila(formulario, "Apellido:", campoApellido, 1);
        agregarFila(formulario, "Legajo:", campoLegajo, 2);

        contenido.add(formulario, BorderLayout.CENTER);

        // Botón
        JButton botonIngresar = new BotonRedondeado("Ingresar");
        botonIngresar.setFont(new Font("Georgia", Font.BOLD, 23));
        botonIngresar.setForeground(EstiloTurnero.BEIGE);
        botonIngresar.setPreferredSize(new Dimension(310, 60));
        botonIngresar.setFocusPainted(false);

        botonIngresar.addActionListener(e -> ingresar(false));

        JButton botonRegistrar = new BotonRedondeado("Registrar docente");
        botonRegistrar.setFont(botonIngresar.getFont());
        botonRegistrar.setForeground(EstiloTurnero.BEIGE);
        botonRegistrar.setPreferredSize(botonIngresar.getPreferredSize());
        botonRegistrar.setFocusPainted(false);
        botonRegistrar.addActionListener(e -> ingresar(true));

        JPanel panelBoton = new JPanel(new FlowLayout(
            FlowLayout.CENTER, 0, 10
        ));
        panelBoton.setOpaque(false);
        panelBoton.add(botonIngresar);
        panelBoton.add(botonRegistrar);

        contenido.add(panelBoton, BorderLayout.SOUTH);
        add(contenido);
    }

    private JTextField crearCampo() {

        JTextField campo = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(EstiloTurnero.BEIGE);
                g2.fillRoundRect(
                    1, 1, getWidth() - 2, getHeight() - 2,
                    18, 18
                );

                g2.setColor(EstiloTurnero.CHOCOLATE);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(
                    1, 1, getWidth() - 3, getHeight() - 3,
                    18, 18
                );

                g2.dispose();
                super.paintComponent(g);
            }
        };

        campo.setOpaque(false);
        campo.setFont(new Font("Tahoma", Font.PLAIN, 20));
        campo.setForeground(EstiloTurnero.CHOCOLATE);
        campo.setCaretColor(EstiloTurnero.CHOCOLATE);
        campo.setPreferredSize(new Dimension(390, 55));
        campo.setBorder(
            BorderFactory.createEmptyBorder(6, 15, 6, 15)
        );

        return campo;
    }

    private void agregarFila(
        JPanel panel, String texto, JTextField campo, int fila
    ) {

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = fila;
        c.insets = new Insets(8, 5, 8, 5);
        c.anchor = GridBagConstraints.CENTER;

        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Georgia", Font.BOLD, 23));
        etiqueta.setForeground(EstiloTurnero.BEIGE);
        etiqueta.setBorder(
            BorderFactory.createEmptyBorder(0, 18, 0, 5)
        );

        JPanel fondoEtiqueta = new PanelRedondeado(
            EstiloTurnero.CHOCOLATE
        );
        fondoEtiqueta.setLayout(new BorderLayout());
        fondoEtiqueta.setPreferredSize(new Dimension(190, 55));
        fondoEtiqueta.add(etiqueta, BorderLayout.CENTER);

        c.gridx = 0;
        panel.add(fondoEtiqueta, c);

        c.gridx = 1;
        panel.add(campo, c);
    }

    private void ingresar(boolean registrar) {

        String nombre = campoNombre.getText().trim();
        String apellido = campoApellido.getText().trim();
        String legajo = campoLegajo.getText().trim();

        if (legajo.isEmpty() || (registrar && (nombre.isEmpty() || apellido.isEmpty()))) {
            JOptionPane.showMessageDialog(
                this, registrar ? "Completá todos los campos." : "Ingresá el legajo docente."
            );
            return;
        }

        try {
            if (registrar) {
                gestion.registrarDocente(nombre, apellido, legajo);
            } else if (gestion.cargarDocentePorLegajo(legajo) == null) {
                JOptionPane.showMessageDialog(this, "No hay un docente registrado con ese legajo.");
                return;
            }
            gestion.cargarComisionesDelDocente();
            gestion.cargarAlumnosDeComisiones();
            gestion.cargarGruposDeComisiones();
            gestion.cargarInstanciasDeComisiones();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage()
            );
            return;
        }

        JOptionPane.showMessageDialog(
            this,
            "Docente: " +
            gestion.getDocenteActual().getNombreCompleto()
        );

        ventana.mostrarPantalla("menu");
    }

    // Panel con esquinas redondeadas
    private static class PanelRedondeado extends JPanel {

        private Color color;

        public PanelRedondeado(Color color) {
            this.color = color;
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

            g2.setColor(color);
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

    // Botón verde redondeado
    private static class BotonRedondeado extends JButton {

        public BotonRedondeado(String texto) {
            super(texto);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(EstiloTurnero.VERDE_MUSGO);
            g2.fillRoundRect(
                1, 1, getWidth() - 2, getHeight() - 2,
                22, 22
            );

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
