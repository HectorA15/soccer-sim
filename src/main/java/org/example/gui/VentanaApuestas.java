package org.example.gui;

import org.example.util.GestorMercado;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaApuestas extends JFrame {
    private static double saldo = 2500.0;
    private static List<ApuestaActiva> apuestasActivas = new ArrayList<>();
    private static DefaultTableModel modeloTabla;
    private GestorMercado mercado;
    private JLabel lblSaldo;
    private JTextField txtMonto;

    public VentanaApuestas(GestorMercado mercado) {
        this.mercado = mercado;
        setTitle("Centro de Apuestas - Soccer Sim");
        setSize(600, 500);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 250));
        setBackground(new Color(27, 24, 24, 255));
        JDialog.setDefaultLookAndFeelDecorated(true);

        // --- Panel Superior: Saldo ---
        JPanel panelSaldo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSaldo.setBackground(new Color(87, 16, 16));
        panelSaldo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        lblSaldo = new JLabel("Saldo Disponible: $" + String.format("%.2f", saldo));
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 18));
        lblSaldo.setForeground(Color.WHITE);
        panelSaldo.add(lblSaldo);
        add(panelSaldo, BorderLayout.NORTH);

        // --- Panel Central: Apuestas Activas ---
        String[] columnas = {"Predicción", "Monto", "Cuota", "Pago Potencial", "Acción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloquea la edición de todas las celdas
            }
        };

        // recuperar apuestas activas del mercado y mostrarlas en la tabla
        for (ApuestaActiva apuesta : apuestasActivas) {
            modeloTabla.addRow(new Object[]{
                    apuesta.tipo,
                    apuesta.monto,
                    apuesta.cuotaPactada,
                    String.format("%.2f", apuesta.monto * apuesta.cuotaPactada),
                    "ACTIVA"
            });
        }

        JTable tablaApuestas = new JTable(modeloTabla);
        tablaApuestas.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaApuestas.setRowHeight(30);
        tablaApuestas.setSelectionBackground(new Color(34, 37, 48));
        tablaApuestas.setBackground(Color.WHITE);
        tablaApuestas.setGridColor(new Color(27, 26, 26));

        JTableHeader header = tablaApuestas.getTableHeader();
        header.setBackground(new Color(46, 52, 60));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 12));


        JScrollPane scrollPane = new JScrollPane(tablaApuestas);
        scrollPane.setBackground(new Color(23, 24, 25));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- Panel Inferior: Controles para Apostar ---
        JPanel panelControl = new JPanel(new GridLayout(2, 1));
        panelControl.setBackground(new Color(0, 0, 0));
        panelControl.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel panelInputs = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelInputs.setBackground(new Color(72, 72, 80));

        JLabel lblMonto = new JLabel("Monto $:");
        lblMonto.setFont(new Font("Arial", Font.BOLD, 12));
        lblMonto.setForeground(Color.WHITE);
        panelInputs.add(lblMonto);

        txtMonto = new JTextField(10);
        txtMonto.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInputs.add(txtMonto);

        JButton btnLocal = new JButton("Apostar Local");
        JButton btnEmpate = new JButton("Apostar Empate");
        JButton btnVisita = new JButton("Apostar Visita");

        // Listeners para botones
        btnLocal.addActionListener(e -> realizarApuesta("Local", mercado.getCuotaLocal()));
        btnEmpate.addActionListener(e -> realizarApuesta("Empate", mercado.getCuotaEmpate()));
        btnVisita.addActionListener(e -> realizarApuesta("Visita", mercado.getCuotaVisitante()));

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(25, 23, 23));
        panelBotones.add(btnLocal);
        panelBotones.add(btnEmpate);
        panelBotones.add(btnVisita);

        panelControl.add(panelInputs);
        panelControl.add(panelBotones);
        add(panelControl, BorderLayout.SOUTH);

        // Timer para actualizar la interfaz si el mercado cambia
        new Timer(1000, e -> actualizarInterfaz()).start();
    }

    private void realizarApuesta(String tipo, String cuotaStr) {
        try {
            double monto = Double.parseDouble(txtMonto.getText());
            double cuota = Double.parseDouble(cuotaStr.replace(",", "."));

            if (monto > 0 && monto <= saldo) {
                saldo -= monto;
                ApuestaActiva nueva = new ApuestaActiva(tipo, monto, cuota);
                apuestasActivas.add(nueva);
                modeloTabla.addRow(new Object[]{tipo, monto, cuota, String.format("%.2f", monto * cuota), "ACTIVA"});
                actualizarInterfaz();
            } else {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente o monto inválido", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un monto válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarInterfaz() {
        lblSaldo.setText("Saldo Disponible: $" + String.format("%.2f", saldo));
    }

    // Clase interna para manejar cada apuesta
    private class ApuestaActiva {
        String tipo;
        double monto;
        double cuotaPactada;
        boolean cobrada = false;

        public ApuestaActiva(String tipo, double monto, double cuotaPactada) {
            this.tipo = tipo;
            this.monto = monto;
            this.cuotaPactada = cuotaPactada;
        }
    }

    public static void finalizarApuestas(String resultadoFinal, JFrame parent) {
        double ganancias = 0.0;

        for (ApuestaActiva apuesta : apuestasActivas) {
            if (apuesta.tipo.equals(resultadoFinal)) {
                double ganancia = apuesta.monto * apuesta.cuotaPactada;
                ganancias += ganancia;
            }
        }

        saldo += ganancias;

        // Llamamos al diálogo pasando el padre para centrarlo
        mostrarResultadoFinal(ganancias, parent);

        apuestasActivas.clear();

        // Verificamos si la tabla existe (por si la ventana está abierta)
        if (modeloTabla != null) {
            modeloTabla.setRowCount(0);
        }

    }

    private static void mostrarResultadoFinal(double ganancias, JFrame parent) {
        JDialog dialogo = new JDialog(parent, "Resultado de tus Apuestas", true);
        dialogo.setSize(400, 250);
        dialogo.setLayout(new BorderLayout(10, 10));
        dialogo.getContentPane().setBackground(new Color(245, 245, 250));

        JPanel panelMensaje = new JPanel();
        panelMensaje.setBackground(new Color(245, 245, 250));
        panelMensaje.setLayout(new BoxLayout(panelMensaje, BoxLayout.Y_AXIS));
        panelMensaje.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblResultado = new JLabel();
        lblResultado.setFont(new Font("Arial", Font.BOLD, 16));
        lblResultado.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMonto = new JLabel();
        lblMonto.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMonto.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (ganancias > 0) {
            lblResultado.setText("Ganaste: $" + String.format("%.2f", ganancias));
            lblMonto.setText("Ahora tienes: $" + String.format("%.2f", saldo));
        } else {
            lblResultado.setText("Perdiste todo :(");
            lblMonto.setText("Tu saldo actual es: $" + String.format("%.2f", saldo));
        }
        panelMensaje.add(Box.createVerticalGlue());
        panelMensaje.add(lblResultado);
        panelMensaje.add(Box.createVerticalStrut(10));
        panelMensaje.add(lblMonto);
        panelMensaje.add(Box.createVerticalGlue());

        JButton btnCerrar = new JButton("Aceptar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(87, 16, 16));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setPreferredSize(new Dimension(100, 35));
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dialogo.dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(245, 245, 250));
        panelBoton.add(btnCerrar);


        dialogo.add(panelMensaje, BorderLayout.CENTER);
        dialogo.add(panelBoton, BorderLayout.SOUTH);
        dialogo.setLocationRelativeTo(parent);
        dialogo.setVisible(true);
    }
}