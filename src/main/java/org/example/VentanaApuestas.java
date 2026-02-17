package org.example;

import javax.swing.*;
import java.awt.*;

public class VentanaApuestas extends JFrame {

    public VentanaApuestas() {
        setTitle("Soccer Sim - Apuestas");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel mensaje = new JLabel(
                "Sección de Apuestas (En construcción)",
                SwingConstants.CENTER
        );

        mensaje.setFont(new Font("Arial", Font.BOLD, 18));
        add(mensaje, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Regresar al Inicio");
        btnVolver.addActionListener(e -> {
            dispose();
            new Inicio().setVisible(true);
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnVolver);

        add(panelInferior, BorderLayout.SOUTH);
    }
}