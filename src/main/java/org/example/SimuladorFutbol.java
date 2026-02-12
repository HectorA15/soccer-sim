package org.example;

import org.example.core.Partido;
import org.example.entidades.Equipos;
import org.example.nombres.EquiposNombres;
import org.example.nombres.JugadoresNombres;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SimuladorFutbol extends JFrame {

    Equipos equipoLocal, equipoVisitante;
    JugadoresNombres jugadoresNombres;
    private final JTextArea areaTexto;
    private final JButton botonIniciar;
    private final JComboBox<String> comboLocal;
    private final JComboBox<String> comboVisitante;
    private Timer timer;
    private int minuto;
    private int golesLocal, golesVisitante;
    private int tarjetasAmarillasLocal, tarjetasAmarillasVisitante;
    private int tarjetasRojasLocal, tarjetasRojasVisitante;
    private final Random random;

    // ===== CONSTRUCTORES =====
    public SimuladorFutbol() {
        setTitle("Simulador de Fútbol");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] equipos = EquiposNombres.getEquipos();

        JPanel panelEquipos = new JPanel();
        panelEquipos.setLayout(new GridLayout(2, 2));

        JLabel labelLocal = new JLabel("Equipo Local:");
        comboLocal = new JComboBox<>(equipos);
        JLabel labelVisitante = new JLabel("Equipo Visitante:");
        comboVisitante = new JComboBox<>(equipos);

        panelEquipos.add(labelLocal);
        panelEquipos.add(comboLocal);
        panelEquipos.add(labelVisitante);
        panelEquipos.add(comboVisitante);

        add(panelEquipos, BorderLayout.NORTH);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);

        botonIniciar = new JButton("Iniciar Partido");
        add(botonIniciar, BorderLayout.SOUTH);

        random = new Random();

        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarPartido();
            }
        });
    }

    // ===== PUNTO DE ENTRADA =====
    static void main(String[] args) {
        SimuladorFutbol simulador = new SimuladorFutbol();
        simulador.setVisible(true);
    }

    // ===== METODOS PRIVADOS =====
    private void iniciarPartido() {
        String strEquipoLocal = (String) comboLocal.getSelectedItem();
        String strEquipoVisitante = (String) comboVisitante.getSelectedItem();

        equipoLocal = new Equipos(strEquipoLocal);
        equipoVisitante = new Equipos(strEquipoVisitante);


        if (strEquipoLocal.equals(strEquipoVisitante)) {
            areaTexto.setText("Los equipos local y visitante no pueden ser el mismo.\n");
            return;
        }

        Partido partido = new Partido(equipoLocal, equipoVisitante);
        equipoLocal = partido.getEquipoLocal();
        equipoVisitante = partido.getEquipoVisitante();


        areaTexto.setText("Inicia el partido entre " + equipoLocal.getNombre() + " y " + equipoVisitante.getNombre() + "...\n");
        minuto = 0;
        golesLocal = 0;
        golesVisitante = 0;
        botonIniciar.setEnabled(false);

        // Configurar el temporizador para simular minuto a minuto
        // 1000 ms representa un minuto simulado
        timer = new Timer(1000, e -> {
            String evento = partido.procesarMinuto(minuto, equipoLocal, equipoVisitante);
            System.out.println(evento); // Depuracion
            areaTexto.append(evento + "\n");
            minuto++;

            if (minuto == 90) {
                timer.stop();
                areaTexto.append("\n¡Final del partido!\n");

                String nombreLocal = equipoLocal.getNombre();
                String nombreVisitante = equipoVisitante.getNombre();
                golesLocal = equipoLocal.getGoles();
                golesVisitante = equipoVisitante.getGoles();
                tarjetasAmarillasLocal = equipoLocal.getTarjetasAmarillas();
                tarjetasAmarillasVisitante = equipoVisitante.getTarjetasAmarillas();
                tarjetasRojasLocal = equipoLocal.getTarjetasRojas();
                tarjetasRojasVisitante = equipoVisitante.getTarjetasRojas();

                areaTexto.append("Resultado Final: " + nombreLocal + " " + golesLocal + " - " + golesVisitante + " " + nombreVisitante + "\n");
                areaTexto.append("Tarjetas amarillas - " + nombreLocal + ": " + tarjetasAmarillasLocal + ", " + nombreVisitante + ": " + tarjetasAmarillasVisitante + "\n");
                areaTexto.append("Tarjetas rojas - " + nombreLocal + ": " + tarjetasRojasLocal + ", " + nombreVisitante + ": " + tarjetasRojasVisitante + "\n");

                if (golesLocal > golesVisitante) {
                    areaTexto.append("¡" + nombreLocal + " gana el partido!\n");
                } else if (golesLocal < golesVisitante) {
                    areaTexto.append("¡" + nombreVisitante + " gana el partido!\n");
                } else {
                    areaTexto.append("El partido termina en empate.\n");
                }
                botonIniciar.setEnabled(true);
            }
        });
        timer.start();
    }
}