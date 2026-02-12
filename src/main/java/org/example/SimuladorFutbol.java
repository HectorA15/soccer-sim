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

    private JTextArea areaTexto;
    private JButton botonIniciar;
    private JComboBox<String> comboLocal, comboVisitante;
    private Timer timer;
    private int minuto;
    private int golesLocal, golesVisitante;
    private int tarjetasAmarillasLocal, tarjetasAmarillasVisitante;
    private int tarjetasRojasLocal, tarjetasRojasVisitante;
    private Random random;

    Equipos equipoLocal, equipoVisitante;
    JugadoresNombres jugadoresNombres;


    public SimuladorFutbol() {
        setTitle("Simulador de Fútbol");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Lista de equipos mexicanos
        String[] equipos = EquiposNombres.getEquipos();


        // Panel superior para seleccionar los equipos
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

        // Configurar el área de texto para mostrar los eventos
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para iniciar el partido
        botonIniciar = new JButton("Iniciar Partido");
        add(botonIniciar, BorderLayout.SOUTH);

        random = new Random();

        // Acción al presionar el botón "Iniciar Partido"
        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarPartido();
            }
        });
    }

    // Metdo para iniciar el partido
    private void iniciarPartido() {
        // Obtener los nombres de los equipos seleccionados
        String strEquipoLocal = (String) comboLocal.getSelectedItem();
        String strEquipoVisitante = (String) comboVisitante.getSelectedItem();

        // Crear las instancias de Equipos
        equipoLocal = new Equipos(strEquipoLocal);
        equipoVisitante = new Equipos(strEquipoVisitante);


        // Verificar que los equipos no sean iguales
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
            System.out.println(evento); // Imprimir en consola para depuración
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
                botonIniciar.setEnabled(true); // Habilitar el botón para jugar otra vez
            }
        });
        timer.start(); // Iniciar el temporizador para simular el partido
    }

    public static void main(String[] args) {
        // Crear la ventana del simulador
        SimuladorFutbol simulador = new SimuladorFutbol();
        simulador.setVisible(true);
    }
}