package org.example;

import org.example.core.Partido;
import org.example.entidades.Equipos;
import org.example.entidades.Formacion;
import org.example.nombres.EquiposNombres;
import org.example.util.GestorMercado;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Ventana principal del simulador de futbol.
 * REFACTORIZADA: Integra GestorMercado y separa la lógica de UI en métodos privados.
 */
public class SimuladorFutbol extends JFrame {

    // Componentes de UI
    private JTextArea areaTexto;
    private JButton botonIniciar;
    private JButton btnVerEquipos;
    private JComboBox<String> comboLocal;
    private JComboBox<String> comboVisitante;
    private JComboBox<String> comboFormacionLocal;
    private JComboBox<String> comboFormacionVisitante;

    // Etiquetas para mostrar las cuotas en vivo
    private JLabel lblCuotaLocal;
    private JLabel lblCuotaEmpate;
    private JLabel lblCuotaVisita;

    // Lógica del Juego
    private final Random random;
    private Equipos equipoLocal;
    private Equipos equipoVisitante;
    private Partido partido;
    private GestorMercado mercado; // Nuestro "Corredor de Apuestas"
    private InfoJugadores ventanaEquipos = null;
    private Timer timer;

    // Estado del Partido
    private int minuto;
    private int golesLocal, golesVisitante;

    public SimuladorFutbol() {
        setTitle("Soccer Sim - Simulador Profesional");
        setSize(900, 700); // Un poco más grande para que quepan las cuotas
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        random = new Random();

        // 1. Inicializar toda la interfaz gráfica
        inicializarComponentes();

        // 2. Configurar las acciones de los botones
        configurarListeners();
    }

    /**
     * Configura paneles, botones y combos. Mantiene el constructor limpio.
     */
    private void inicializarComponentes() {
        String[] equipos = EquiposNombres.getEquipos();
        String[] formaciones = {"4-4-2", "4-3-3", "3-5-2", "5-3-2", "3-4-3", "4-5-1"};

        // --- PANEL SUPERIOR (Selección) ---
        JPanel panelEquipos = new JPanel(new GridLayout(4, 2, 5, 5));
        panelEquipos.setBorder(BorderFactory.createTitledBorder("Configuración del Partido"));

        panelEquipos.add(new JLabel("Equipo Local:"));
        comboLocal = new JComboBox<>(equipos);
        panelEquipos.add(comboLocal);

        panelEquipos.add(new JLabel("Formación Local:"));
        comboFormacionLocal = new JComboBox<>(formaciones);
        panelEquipos.add(comboFormacionLocal);

        panelEquipos.add(new JLabel("Equipo Visitante:"));
        comboVisitante = new JComboBox<>(equipos);
        comboVisitante.setSelectedIndex(1);
        panelEquipos.add(comboVisitante);

        panelEquipos.add(new JLabel("Formación Visitante:"));
        comboFormacionVisitante = new JComboBox<>(formaciones);
        panelEquipos.add(comboFormacionVisitante);

        add(panelEquipos, BorderLayout.NORTH);

        // --- AREA CENTRAL (Log del partido) ---
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        add(scrollPane, BorderLayout.CENTER);

        // --- PANEL INFERIOR (Botones y Cuotas) ---
        JPanel panelInferior = new JPanel(new BorderLayout());

        // Sub-panel de Cuotas (Nuevo)
        JPanel panelCuotas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panelCuotas.setBorder(BorderFactory.createTitledBorder("Mercado de Apuestas en Vivo"));
        panelCuotas.setBackground(new Color(240, 248, 255));

        lblCuotaLocal = new JLabel("Local: -");
        lblCuotaEmpate = new JLabel("Empate: -");
        lblCuotaVisita = new JLabel("Visita: -");

        // Estilo a las cuotas
        Font fontCuotas = new Font("Arial", Font.BOLD, 14);
        lblCuotaLocal.setFont(fontCuotas);
        lblCuotaEmpate.setFont(fontCuotas);
        lblCuotaVisita.setFont(fontCuotas);
        lblCuotaLocal.setForeground(new Color(0, 100, 0)); // Verde oscuro

        panelCuotas.add(lblCuotaLocal);
        panelCuotas.add(lblCuotaEmpate);
        panelCuotas.add(lblCuotaVisita);

        panelInferior.add(panelCuotas, BorderLayout.NORTH);

        // Sub-panel de Botones
        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnRegresar = new JButton("Regresar al Inicio");
        btnRegresar.addActionListener(e -> {
            if (timer != null && timer.isRunning()) timer.stop();
            dispose();
            new Inicio().setVisible(true);
        });
        panelBotones.add(btnRegresar);

        JButton btnApuestas = new JButton("Ir a Apuestas");
        btnApuestas.addActionListener(e -> new VentanaApuestas().setVisible(true));
        panelBotones.add(btnApuestas);

        botonIniciar = new JButton("Iniciar Partido");
        botonIniciar.setFont(new Font("Arial", Font.BOLD, 12));
        panelBotones.add(botonIniciar);

        btnVerEquipos = new JButton("Ver Equipos");
        panelBotones.add(btnVerEquipos);

        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void configurarListeners() {
        botonIniciar.addActionListener(e -> iniciarPartido());

        btnVerEquipos.addActionListener(e -> mostrarVentanaEquipos());
    }

    /**
     * Inicializa la lógica del partido, equipos y el timer.
     */
    private void iniciarPartido() {
        // 1. Configurar Equipos
        equipoLocal = new Equipos((String) comboLocal.getSelectedItem());
        equipoVisitante = new Equipos((String) comboVisitante.getSelectedItem());

        equipoLocal.setFormacion(parsearFormacion((String) comboFormacionLocal.getSelectedItem()));
        equipoVisitante.setFormacion(parsearFormacion((String) comboFormacionVisitante.getSelectedItem()));

        equipoLocal.asignarPosiciones();
        equipoVisitante.asignarPosiciones();

        // 2. Iniciar Motores (Partido y Mercado)
        partido = new Partido(equipoLocal, equipoVisitante);
        mercado = new GestorMercado();

        // 3. Resetear UI
        areaTexto.setText("=== INICIO DEL PARTIDO ===\n");
        areaTexto.append(equipoLocal.getNombre() + " vs " + equipoVisitante.getNombre() + "\n\n");
        minuto = 0;
        golesLocal = 0;
        golesVisitante = 0;
        botonIniciar.setEnabled(false);

        // 4. Configurar Timer (Bucle del juego)
        timer = new Timer(1000, e -> procesarMinuto());
        timer.start();
    }

    /**
     * Lógica que se ejecuta cada "minuto" (segundo) del partido.
     */
    private void procesarMinuto() {
        // A. Procesar evento de fútbol
        String evento = partido.procesarMinuto(minuto, equipoLocal, equipoVisitante);
        System.out.println(evento);
        areaTexto.append(evento + "\n");

        // Actualizar marcadores locales (por si hubo gol)
        golesLocal = equipoLocal.getGoles();
        golesVisitante = equipoVisitante.getGoles();

        // B. Actualizar Mercado de Apuestas
        mercado.actualizarCuotas(minuto, golesLocal, golesVisitante, equipoLocal, equipoVisitante);

        // C. Reflejar Cuotas en la UI
        lblCuotaLocal.setText("Local: " + mercado.getCuotaLocal());
        lblCuotaEmpate.setText("Empate: " + mercado.getCuotaEmpate());
        lblCuotaVisita.setText("Visita: " + mercado.getCuotaVisitante());

        // D. Actualizar tablas de jugadores si están abiertas
        if (ventanaEquipos != null && ventanaEquipos.isVisible()) {
            ventanaEquipos.actualizarTablas();
        }

        minuto++;

        // E. Verificar Fin del Partido
        if (minuto > 90) {
            finalizarPartido();
        }
    }

    private void finalizarPartido() {
        timer.stop();
        areaTexto.append("\n=== FINAL DEL PARTIDO ===\n");
        areaTexto.append("Marcador Final: " + equipoLocal.getNombre() + " " + golesLocal + " - " + golesVisitante + " " + equipoVisitante.getNombre() + "\n");

        // Mostrar ganador
        if (golesLocal > golesVisitante) areaTexto.append("Ganador: " + equipoLocal.getNombre() + "\n");
        else if (golesLocal < golesVisitante) areaTexto.append("Ganador: " + equipoVisitante.getNombre() + "\n");
        else areaTexto.append("Resultado: EMPATE\n");

        botonIniciar.setEnabled(true);
    }

    private void mostrarVentanaEquipos() {
        if (equipoLocal != null && equipoVisitante != null) {
            if (ventanaEquipos != null && ventanaEquipos.isVisible()) {
                ventanaEquipos.toFront();
                return;
            }
            ventanaEquipos = new InfoJugadores(equipoLocal, equipoVisitante);
            ventanaEquipos.setLocationRelativeTo(this);
            ventanaEquipos.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Primero inicia un partido.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Formacion parsearFormacion(String formacionStr) {
        String[] partes = formacionStr.split("-");
        return new Formacion(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Inicio().setVisible(true));
    }
}