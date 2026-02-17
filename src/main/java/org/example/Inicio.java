package org.example;
//la clase que inicia el programa con una ventana de inicio

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Inicio extends JFrame {

    public Inicio() {
        setTitle("Soccer Sim");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(25, 42, 86)); // azul oscuro color
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        JLabel lblTitulo = new JLabel("SOCCER SIM"); //titulo con font y color
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblTitulo.setForeground(Color.WHITE);

        panelPrincipal.add(Box.createVerticalStrut(80));
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createVerticalStrut(60));

        JButton btnEntrar = new JButton("Iniciar Juego");
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBackground(new Color(36, 165, 91)); // verde color
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnEntrar.setMaximumSize(new Dimension(200, 50));

        btnEntrar.addActionListener(e -> {
            new SimuladorFutbol().setVisible(true);
            dispose();
        });

        panelPrincipal.add(btnEntrar);
        panelPrincipal.add(Box.createVerticalStrut(20));


        JButton btnSalir = new JButton("Salir");
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setFocusPainted(false);
        btnSalir.setBackground(new Color(231, 76, 60)); // rojo color
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnSalir.setMaximumSize(new Dimension(200, 45));

        btnSalir.addActionListener(e -> System.exit(0));

        panelPrincipal.add(btnSalir);

        add(panelPrincipal, BorderLayout.CENTER);
    }
}