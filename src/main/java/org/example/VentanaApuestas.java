package org.example;

import javax.swing.*;
import java.awt.*;

public class VentanaApuestas extends JFrame {

    public VentanaApuestas() {
        setTitle("Soccer Sim - Apuestas");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel mensaje = new JLabel(
                "Sección de Apuestas (En construcción)",
                SwingConstants.CENTER
        );

        mensaje.setFont(new Font("Arial", Font.BOLD, 18));
        add(mensaje, BorderLayout.CENTER);



        JPanel panelInferior = new JPanel();;

        add(panelInferior, BorderLayout.SOUTH);
    }
}