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

    private InfoJugadores ventanaEquipos = null;

    // ===== CONSTRUCTORES =====
    public SimuladorFutbol() {
        setTitle("Simulador de Fútbol");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] equipos = EquiposNombres.getEquipos();

        // ===== PANEL SUPERIOR: Selección de equipos =====
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

        // ===== CENTRO: Área de texto para eventos =====
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);

        // ===== PANEL INFERIOR: Botones =====
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        // Botón "Iniciar Partido"
        botonIniciar = new JButton("Iniciar Partido");
        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarPartido();
            }
        });
        panelBotones.add(botonIniciar);

        // Botón "Ver Equipos"
        JButton btnVerEquipos = new JButton("Ver Equipos");
        btnVerEquipos.addActionListener(e -> {
            if (equipoLocal != null && equipoVisitante != null) {

                // 🆕 Verificar si ya existe y está visible
                if (ventanaEquipos != null && ventanaEquipos.isVisible()) {
                    ventanaEquipos.toFront(); // Traer al frente
                    ventanaEquipos.requestFocus(); // Dar foco
                    return; // Salir sin crear nueva ventana
                }

                // Crear nueva ventana solo si no existe
                ventanaEquipos = new InfoJugadores(
                        equipoLocal,
                        equipoVisitante,
                        SimuladorFutbol.this
                );

                // Posicionar a la derecha
                Point ubicacion = SimuladorFutbol.this.getLocation();
                int anchoVentanaPrincipal = SimuladorFutbol.this.getWidth();
                ventanaEquipos.setLocation(
                        ubicacion.x + anchoVentanaPrincipal + 10,
                        ubicacion.y
                );

                // 🆕 Listener para limpiar la referencia cuando se cierre
                ventanaEquipos.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        ventanaEquipos = null; // Limpiar referencia
                    }
                });

                ventanaEquipos.setVisible(true);

            } else {
                JDialog.setDefaultLookAndFeelDecorated(true);
                JOptionPane.showMessageDialog(
                        SimuladorFutbol.this,
                        "Primero debes iniciar un partido para ver los equipos",
                        "Equipos no disponibles",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });
        panelBotones.add(btnVerEquipos);

        add(panelBotones, BorderLayout.SOUTH);

        random = new Random();
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

        // Configurar el temporizador
        timer = new Timer(1000, e -> {
            String evento = partido.procesarMinuto(minuto, equipoLocal, equipoVisitante);
            System.out.println(evento);
            areaTexto.append(evento + "\n");

            // 🆕 Actualizar ventana de equipos si está abierta
            if (ventanaEquipos != null && ventanaEquipos.isVisible()) {
                ventanaEquipos.actualizarTablas();
            }

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

                // 🆕 Actualización final
                if (ventanaEquipos != null && ventanaEquipos.isVisible()) {
                    ventanaEquipos.actualizarTablas();
                }

                botonIniciar.setEnabled(true);
            }
        });
        timer.start();
    }
}