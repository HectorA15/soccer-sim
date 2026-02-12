package org.example;

import org.example.core.Partido;
import org.example.entidades.Equipos;
import org.example.entidades.Formacion;
import org.example.nombres.EquiposNombres;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Ventana principal del simulador de futbol.
 * Permite seleccionar equipos, formaciones e iniciar la simulacion del partido.
 */
public class SimuladorFutbol extends JFrame {

    Equipos equipoLocal, equipoVisitante;
    private InfoJugadores ventanaEquipos = null;
    private final JTextArea areaTexto;
    private final JButton botonIniciar;
    private final JComboBox<String> comboLocal;
    private final JComboBox<String> comboVisitante;
    private final JComboBox<String> comboFormacionLocal;
    private final JComboBox<String> comboFormacionVisitante;
    private Timer timer;
    private int minuto;
    private int golesLocal, golesVisitante;
    private int tarjetasAmarillasLocal, tarjetasAmarillasVisitante;
    private int tarjetasRojasLocal, tarjetasRojasVisitante;
    private final Random random;

    public SimuladorFutbol() {
        setTitle("Simulador de Futbol");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        String[] equipos = EquiposNombres.getEquipos();
        String[] formaciones = {"4-4-2", "4-3-3", "3-5-2", "5-3-2", "3-4-3", "4-5-1"};

        // Panel superior con seleccion de equipos y formaciones
        JPanel panelEquipos = new JPanel();
        panelEquipos.setLayout(new GridLayout(4, 2, 5, 5));
        panelEquipos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelLocal = new JLabel("Equipo Local:");
        comboLocal = new JComboBox<>(equipos);

        JLabel labelFormacionLocal = new JLabel("Formacion Local:");
        comboFormacionLocal = new JComboBox<>(formaciones);

        JLabel labelVisitante = new JLabel("Equipo Visitante:");
        comboVisitante = new JComboBox<>(equipos);
        comboVisitante.setSelectedIndex(1); // Seleccionar un equipo diferente por defecto

        JLabel labelFormacionVisitante = new JLabel("Formacion Visitante:");
        comboFormacionVisitante = new JComboBox<>(formaciones);

        panelEquipos.add(labelLocal);
        panelEquipos.add(comboLocal);
        panelEquipos.add(labelFormacionLocal);
        panelEquipos.add(comboFormacionLocal);
        panelEquipos.add(labelVisitante);
        panelEquipos.add(comboVisitante);
        panelEquipos.add(labelFormacionVisitante);
        panelEquipos.add(comboFormacionVisitante);

        add(panelEquipos, BorderLayout.NORTH);

        // Area de texto para eventos
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        botonIniciar = new JButton("Iniciar Partido");
        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarPartido();
            }
        });
        panelBotones.add(botonIniciar);

        JButton btnVerEquipos = new JButton("Ver Equipos");

        btnVerEquipos.addActionListener(e -> {
            JDialog.setDefaultLookAndFeelDecorated(true);
            if (equipoLocal != null && equipoVisitante != null) {

                if (ventanaEquipos != null && ventanaEquipos.isVisible()) {
                    ventanaEquipos.toFront();
                    ventanaEquipos.requestFocus();
                    return;
                }

                ventanaEquipos = new InfoJugadores(
                        equipoLocal,
                        equipoVisitante,
                        SimuladorFutbol.this
                );

                Point ubicacion = SimuladorFutbol.this.getLocation();
                int anchoVentanaPrincipal = SimuladorFutbol.this.getWidth();
                ventanaEquipos.setLocation(
                        ubicacion.x + anchoVentanaPrincipal + 10,
                        ubicacion.y
                );

                ventanaEquipos.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        ventanaEquipos = null;
                    }
                });

                ventanaEquipos.setVisible(true);

            } else {
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

    public static void main(String[] args) {
        SimuladorFutbol simulador = new SimuladorFutbol();
        simulador.setVisible(true);
    }

    /**
     * Inicializa y comienza la simulacion del partido.
     * Crea los equipos, asigna formaciones y configura el timer para simular minuto a minuto.
     */
    private void iniciarPartido() {
        String strEquipoLocal = (String) comboLocal.getSelectedItem();
        String strEquipoVisitante = (String) comboVisitante.getSelectedItem();

        if (strEquipoLocal.equals(strEquipoVisitante)) {
            areaTexto.setText("Los equipos local y visitante no pueden ser el mismo.\n");
            return;
        }

        // Crear equipos
        equipoLocal = new Equipos(strEquipoLocal);
        equipoVisitante = new Equipos(strEquipoVisitante);

        // Obtener formaciones seleccionadas
        String formacionLocalStr = (String) comboFormacionLocal.getSelectedItem();
        String formacionVisitanteStr = (String) comboFormacionVisitante.getSelectedItem();

        // Parsear formaciones (formato "4-4-2")
        Formacion formacionLocal = parsearFormacion(formacionLocalStr);
        Formacion formacionVisitante = parsearFormacion(formacionVisitanteStr);

        // Asignar formaciones
        equipoLocal.setFormacion(formacionLocal);
        equipoVisitante.setFormacion(formacionVisitante);

        equipoLocal.asignarPosiciones();
        equipoVisitante.asignarPosiciones();

        // Crear partido
        Partido partido = new Partido(equipoLocal, equipoVisitante);

        areaTexto.setText("Inicia el partido entre " + equipoLocal.getNombre() +
                " (" + formacionLocalStr + ") y " + equipoVisitante.getNombre() +
                " (" + formacionVisitanteStr + ")...\n");
        minuto = 0;
        golesLocal = 0;
        golesVisitante = 0;
        botonIniciar.setEnabled(false);

        // Timer para simular minuto a minuto
        timer = new Timer(1000, e -> {
            String evento = partido.procesarMinuto(minuto, equipoLocal, equipoVisitante);
            System.out.println(evento);
            areaTexto.append(evento + "\n");

            if (ventanaEquipos != null && ventanaEquipos.isVisible()) {
                ventanaEquipos.actualizarTablas();
            }

            minuto++;

            if (minuto == 90) {
                timer.stop();
                areaTexto.append("\nFinal del partido!\n");

                String nombreLocal = equipoLocal.getNombre();
                String nombreVisitante = equipoVisitante.getNombre();
                golesLocal = equipoLocal.getGoles();
                golesVisitante = equipoVisitante.getGoles();
                tarjetasAmarillasLocal = equipoLocal.getTarjetasAmarillas();
                tarjetasAmarillasVisitante = equipoVisitante.getTarjetasAmarillas();
                tarjetasRojasLocal = equipoLocal.getTarjetasRojas();
                tarjetasRojasVisitante = equipoVisitante.getTarjetasRojas();

                areaTexto.append("Resultado Final: " + nombreLocal + " " + golesLocal +
                        " - " + golesVisitante + " " + nombreVisitante + "\n");
                areaTexto.append("Tarjetas amarillas - " + nombreLocal + ": " + tarjetasAmarillasLocal +
                        ", " + nombreVisitante + ": " + tarjetasAmarillasVisitante + "\n");
                areaTexto.append("Tarjetas rojas - " + nombreLocal + ": " + tarjetasRojasLocal +
                        ", " + nombreVisitante + ": " + tarjetasRojasVisitante + "\n");

                if (golesLocal > golesVisitante) {
                    areaTexto.append(nombreLocal + " gana el partido!\n");
                } else if (golesLocal < golesVisitante) {
                    areaTexto.append(nombreVisitante + " gana el partido!\n");
                } else {
                    areaTexto.append("El partido termina en empate.\n");
                }

                if (ventanaEquipos != null && ventanaEquipos.isVisible()) {
                    ventanaEquipos.actualizarTablas();
                }

                botonIniciar.setEnabled(true);
            }
        });
        timer.start();
    }

    /**
     * Convierte un string de formacion (ej: "4-4-2") en un objeto Formacion.
     * @param formacionStr String con formato "D-M-A" (defensas-mediocampistas-delanteros)
     * @return Objeto Formacion correspondiente
     */
    private Formacion parsearFormacion(String formacionStr) {
        String[] partes = formacionStr.split("-");
        int defensas = Integer.parseInt(partes[0]);
        int mediocampistas = Integer.parseInt(partes[1]);
        int delanteros = Integer.parseInt(partes[2]);
        return new Formacion(defensas, mediocampistas, delanteros);
    }
}