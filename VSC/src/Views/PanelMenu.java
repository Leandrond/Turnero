package Views;

import java.awt.*;
import javax.swing.*;

public class PanelMenu extends JPanel {

    public PanelMenu(VentanaPrincipal ventana) {

        setLayout(new GridBagLayout());
        setBackground(EstiloTurnero.CHOCOLATE);

        JPanel contenido = new PanelRedondeado();
        contenido.setLayout(new BorderLayout(0, 25));
        contenido.setPreferredSize(new Dimension(790, 540));
        contenido.setBorder(
            BorderFactory.createEmptyBorder(35, 65, 35, 65)
        );

        // Encabezado
        JPanel encabezado = new JPanel();
        encabezado.setOpaque(false);
        encabezado.setLayout(new BoxLayout(
            encabezado, BoxLayout.Y_AXIS
        ));

        JLabel icono = new JLabel("\uD83C\uDF93");
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("MENÚ PRINCIPAL");
        titulo.setFont(new Font("Georgia", Font.BOLD, 34));
        titulo.setForeground(EstiloTurnero.BEIGE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel(
            "Sistema de gestión de turnos"
        );
        subtitulo.setFont(new Font("Tahoma", Font.PLAIN, 17));
        subtitulo.setForeground(EstiloTurnero.BEIGE);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        encabezado.add(icono);
        encabezado.add(Box.createVerticalStrut(12));
        encabezado.add(titulo);
        encabezado.add(Box.createVerticalStrut(10));
        encabezado.add(subtitulo);

        contenido.add(encabezado, BorderLayout.NORTH);

        // Botones del menú
        JPanel botones = new JPanel(new GridBagLayout());
        botones.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(10, 0, 10, 0);

        JButton botonComisiones = crearBoton(
            "Comisiones", EstiloTurnero.VERDE_MUSGO
        );

        JButton botonInstancias = crearBoton(
            "Instancias evaluativas",
            EstiloTurnero.VERDE_MUSGO
        );

        JButton botonSalir = crearBoton(
            "Salir", EstiloTurnero.CHOCOLATE
        );

        c.gridy = 0;
        botones.add(botonComisiones, c);

        c.gridy = 1;
        botones.add(botonInstancias, c);

        c.gridy = 2;
        botones.add(botonSalir, c);

        contenido.add(botones, BorderLayout.CENTER);

        // Acciones originales
       
        botonComisiones.addActionListener(e ->
             ventana.mostrarPantalla("comisiones")
        );

        botonInstancias.addActionListener(e ->
            ventana.mostrarPantalla("instancias")
        );


        botonSalir.addActionListener(e -> System.exit(0));

        add(contenido);
    }

    private JButton crearBoton(String texto, Color color) {

        JButton boton = new JButton(texto) {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(color);
                g2.fillRoundRect(
                    0, 0, getWidth(), getHeight(), 22, 22
                );

                g2.dispose();
                super.paintComponent(g);
            }
        };

        boton.setFont(new Font("Georgia", Font.BOLD, 22));
        boton.setForeground(EstiloTurnero.BEIGE);
        boton.setPreferredSize(new Dimension(370, 62));
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setOpaque(false);
        boton.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        return boton;
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
