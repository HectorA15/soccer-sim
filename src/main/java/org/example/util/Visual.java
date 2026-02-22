package org.example.util;

import javax.swing.*;
import java.awt.*;

public class Visual {

    public static class FondoPanel extends JPanel {

        private Image imagen;

        public FondoPanel(String rutaImagen) {
            try {
                imagen = new ImageIcon(getClass().getResource(rutaImagen)
                ).getImage();
            } catch (Exception e) {
                System.out.println("No se encontró la imagen: " + rutaImagen);
            }

            setOpaque(false);
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (imagen != null) {
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

                g.setColor(new Color(0,0,0,120));
                g.fillRect(0,0,getWidth(),getHeight());
            }
        }
    }

    public static ImageIcon icon(String nombre) {

        java.net.URL url = Visual.class.getResource("/" + nombre);

        if (url == null) {
            System.out.println("Icono no encontrado: " + nombre);
            return null;
        }
        return new ImageIcon(url);
    }

    public static class PanelDegradado extends JPanel {

        private Color colorArriba;
        private Color colorAbajo;

        public PanelDegradado(Color arriba, Color abajo) {
            this.colorArriba = arriba;
            this.colorAbajo = abajo;

            setOpaque(false);
            setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
            setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        }
            @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);



            GradientPaint degradado = new GradientPaint(
                    0, 0, colorArriba,
                    0, getHeight(), colorAbajo
            );

            g2.setPaint(degradado);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
