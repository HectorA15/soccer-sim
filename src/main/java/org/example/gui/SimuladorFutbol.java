package org.example.gui;

import org.example.core.Partido;
import org.example.entidades.Equipos;
import org.example.entidades.Formacion;
import org.example.nombres.EquiposNombres;
import org.example.util.GestorMercado;
import org.example.util.Visual;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Ventana principal del simulador de futbol.
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
    private JLabel lblGolesLocal;
    private JLabel lblGolesVisitante;
    private JLabel lblVs;

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
        Visual.FondoPanel fondo = new Visual.FondoPanel("/ImagenEquipo.jpg");
        this.setContentPane(fondo);
        setLayout(new BorderLayout());
        JDialog.setDefaultLookAndFeelDecorated(true);

        random = new Random();

        // Inicializar toda la interfaz gráfica
        inicializarComponentes();

        // Configurar las acciones de los botones
        configurarListeners();
    }

    /**
     * Configura paneles, botones y combos.
     */
    private void inicializarComponentes() {
        String[] equipos = EquiposNombres.getEquipos();
        String[] formaciones = {"4-4-2", "4-3-3", "3-5-2", "5-3-2", "3-4-3", "4-5-1"};

        // --- PANEL SUPERIOR (Selección) ---
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);
        JLabel titulo = new JLabel("SOCCER SIM", SwingConstants.CENTER);
        titulo.setFont(new Font("Impact", Font.BOLD, 36));
        titulo.setForeground(new Color(34, 139, 34));

        JPanel panelEquipos = new JPanel(new GridLayout(4, 2, 5, 5));
        panelEquipos.setBorder(BorderFactory.createTitledBorder("Configuración del Partido"));
        panelEquipos.setOpaque(false);

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

        panelSuperior.add(panelEquipos, BorderLayout.NORTH);


        // --- AREA CENTRAL SUPERIOR (goles) ---
        JPanel panelGoles = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panelGoles.setOpaque(false);
        panelGoles.setBorder(BorderFactory.createTitledBorder("Marcador Actual"));
        JPanel Fondo = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,5));


        lblGolesLocal = new JLabel("0");
            lblVs = new JLabel("- VS -");
        lblGolesVisitante = new JLabel("0");

        Font fontGoles = new Font("Arial Black", Font.PLAIN, 30);
        lblGolesLocal.setFont(fontGoles);
        lblGolesLocal.setForeground(new Color(0, 100, 0)); // Verde

        lblGolesVisitante.setFont(fontGoles);
        lblGolesVisitante.setForeground(new Color(0, 0, 100)); // Azul

        lblVs.setFont(new Font("Arial Black", Font.BOLD, 30));

        JPanel panelCentro = new Visual.PanelDegradado(new Color(20,20,20) , new Color(60,60,60));


        panelCentro.add(lblGolesLocal);
        panelCentro.add(lblVs);
        panelCentro.add(lblGolesVisitante);
        panelGoles.add(panelCentro);


        panelSuperior.add(panelEquipos, BorderLayout.NORTH);
        panelSuperior.add(panelGoles, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        // --- AREA CENTRAL (Log del partido) ---
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 18));
        areaTexto.setForeground(Color.BLACK);
        areaTexto.setMargin(new Insets(10, 10, 10, 10));
        areaTexto.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);;
        add(scrollPane, BorderLayout.CENTER);

        // --- PANEL INFERIOR (Botones y Cuotas) ---
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder( 0, 10, 10, 10)); // 10px en todos lados

        // Sub-panel de Cuotas (Nuevo)
        JPanel panelCuotas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        panelCuotas.setOpaque(false);
        panelCuotas.setBorder(BorderFactory.createTitledBorder("Mercado en Vivo"));
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
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));

        Font fontBotones = new Font("Segoe UI", Font.BOLD, 14);

        Color colorFondo = new Color(21, 101, 192);


        JButton btnRegresar = new JButton("Regresar al Inicio");
        btnRegresar.addActionListener(e -> {
            if (timer != null && timer.isRunning()) timer.stop();
            dispose();
            new Inicio().setVisible(true);
        });

        JButton btnApuestas = new JButton("Ir a Apuestas");
        btnApuestas.addActionListener(e -> {
            if (mercado != null) {
                new VentanaApuestas(mercado).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Inicia el partido para ver las cuotas");
            }
        });


        botonIniciar = new JButton("Iniciar Partido");
        botonIniciar.setIcon(Visual.icon("Iniciar.png"));

        btnVerEquipos = new JButton("Ver Equipos");
        btnVerEquipos.setIcon(Visual.icon("lista.png"));

        btnApuestas.setIcon(Visual.icon("Apuesta.png"));

        btnRegresar.setIcon(Visual.icon("Inicio.png"));



        for (JButton btn : new JButton[]{btnRegresar, btnApuestas, botonIniciar, btnVerEquipos}) {
            btn.setBorderPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));// Quita el borde 3D con relieve
            btn.setFont(fontBotones);
            btn.setBackground(colorFondo);
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setHorizontalTextPosition(SwingConstants.RIGHT);
            btn.setIconTextGap(10);
        }

        panelBotones.add(Box.createHorizontalStrut(10)); // Espacio fijo
        panelBotones.add(btnRegresar);
        panelBotones.add(Box.createHorizontalGlue()); // Espacio flexible
        panelBotones.add(botonIniciar);
        panelBotones.add(Box.createHorizontalStrut(10)); // Espacio fijo
        panelBotones.add(btnVerEquipos);
        panelBotones.add(Box.createHorizontalGlue()); // Espacio flexible
        panelBotones.add(btnApuestas);
        panelBotones.add(Box.createHorizontalStrut(10)); // Espacio fijo

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

        lblGolesLocal.setText("0");
        lblGolesVisitante.setText("0");

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

        lblGolesLocal.setText(equipoLocal.getNombre() + ": " + golesLocal);

        lblGolesVisitante.setText(golesVisitante + " : " + equipoVisitante.getNombre());

        // B. Actualizar Mercado de Apuestas
        mercado.actualizarCuotas(minuto, golesLocal, golesVisitante, equipoLocal, equipoVisitante);

        // C. Reflejar Cuotas en la UI
        if(minuto  > 85) {
            lblCuotaLocal.setText("Local: MERCADO CERRADO");
            lblCuotaEmpate.setText("Empate: MERCADO CERRADO");
            lblCuotaVisita.setText("Visita: MERCADO CERRADO");
        } else {
            lblCuotaLocal.setText("Local: " + mercado.getCuotaLocal());
            lblCuotaEmpate.setText("Empate: " + mercado.getCuotaEmpate());
            lblCuotaVisita.setText("Visita: " + mercado.getCuotaVisitante());
        }



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
        String resultadoApuestas;
        if (golesLocal > golesVisitante) {
            resultadoApuestas = "Local";
            areaTexto.append("Ganador: " + equipoLocal.getNombre() + "\n");
        } else if (golesVisitante > golesLocal) {
            resultadoApuestas = "Visita";
            areaTexto.append("Ganador: " + equipoVisitante.getNombre() + "\n");
        } else {
            resultadoApuestas = "Empate";
            areaTexto.append("Resultado: EMPATE\n");
        }


        VentanaApuestas.finalizarApuestas(resultadoApuestas, this);

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

}
