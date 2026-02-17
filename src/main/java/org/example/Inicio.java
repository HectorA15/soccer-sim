package org.example;
//la clase que inicia el programa con una ventana de inicio

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Inicio extends JFrame {

    public Inicio() {
        setTitle("Soccer Sim");
        setSize(700, 530);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Titulo principal de la ventana del inicio
        JLabel titulo = new JLabel("SOCCER SIM", SwingConstants.CENTER);
        titulo.setFont(new Font("Impact", Font.BOLD, 50));
        titulo.setForeground(new Color(34, 139, 34));
        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton btnEntrar = new JButton("Entrar");
        JButton btnSalir = new JButton("Salir");

        panelBotones.add(btnEntrar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);


        btnEntrar.addActionListener((ActionEvent e) -> {
            dispose(); // cierra la ventana del inicio

            new SimuladorFutbol().setVisible(true);
        });


        btnSalir.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
    }
}
