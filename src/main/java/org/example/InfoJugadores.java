package org.example;

import org.example.entidades.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Ventana que muestra información detallada de los jugadores de ambos equipos.
 * Incluye: formación, portero, titulares, suplentes y estadísticas.
 */
public class InfoJugadores extends JFrame {

    // ===== COMPONENTES GUI =====
    private final JTable tablaPorteroLocal;
    private final JTable tablaPorteroVisitante;
    private final JTable tablaLocal;
    private final JTable tablaVisitante;
    private final JTable tablaSuplentesLocal;
    private final JTable tablaSuplentesVisitante;
    private final JButton btnCerrar;

    // ===== DATOS =====
    private final Equipos equipoLocal;
    private final Equipos equipoVisitante;

    public InfoJugadores(Equipos equipoLocal, Equipos equipoVisitante, JFrame ventanaPadre) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;

        // Configuración de la ventana
        setTitle("Información de Equipos");
        setSize(1400, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== CREAR TABLAS =====
        tablaPorteroLocal = crearTablaPortero(equipoLocal.getPortero());
        tablaPorteroVisitante = crearTablaPortero(equipoVisitante.getPortero());
        tablaLocal = crearTablaJugadores(equipoLocal.getJugadores());
        tablaVisitante = crearTablaJugadores(equipoVisitante.getJugadores());
        tablaSuplentesLocal = crearTablaSuplentes(equipoLocal.getReserva());
        tablaSuplentesVisitante = crearTablaSuplentes(equipoVisitante.getReserva());

        // ===== CONSTRUIR INTERFAZ =====
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 20, 0));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelPrincipal.add(crearPanelEquipo(
                equipoLocal.getNombre(),
                equipoLocal.getFormacion().toString(),
                tablaPorteroLocal,
                tablaLocal,
                tablaSuplentesLocal
        ));

        panelPrincipal.add(crearPanelEquipo(
                equipoVisitante.getNombre(),
                equipoVisitante.getFormacion().toString(),
                tablaPorteroVisitante,
                tablaVisitante,
                tablaSuplentesVisitante
        ));

        add(panelPrincipal, BorderLayout.CENTER);

        // ===== BOTÓN CERRAR =====
        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);
    }

    /**
     * Crea un panel completo para un equipo.
     */
    private JPanel crearPanelEquipo(String nombreEquipo, String formacion,
                                    JTable tablaPortero,
                                    JTable tablaTitulares,
                                    JTable tablaSuplentes) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Nombre del equipo
        JLabel lblNombre = new JLabel(nombreEquipo);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 24));
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Formación
        JLabel lblFormacion = new JLabel(formacion);
        lblFormacion.setFont(new Font("Arial", Font.BOLD, 48));
        lblFormacion.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🆕 Título portero
        JLabel lblPortero = new JLabel("PORTERO");
        lblPortero.setFont(new Font("Arial", Font.BOLD, 16));
        lblPortero.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título titulares
        JLabel lblTitulares = new JLabel("TITULARES");
        lblTitulares.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulares.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título suplentes
        JLabel lblSuplentes = new JLabel("SUPLENTES");
        lblSuplentes.setFont(new Font("Arial", Font.BOLD, 16));
        lblSuplentes.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Agregar componentes
        panel.add(lblNombre);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lblFormacion);
        panel.add(Box.createVerticalStrut(15));

        // 🆕 Agregar portero
        panel.add(lblPortero);
        panel.add(Box.createVerticalStrut(5));
        panel.add(new JScrollPane(tablaPortero));
        panel.add(Box.createVerticalStrut(15));

        panel.add(lblTitulares);
        panel.add(Box.createVerticalStrut(5));
        panel.add(new JScrollPane(tablaTitulares));
        panel.add(Box.createVerticalStrut(15));
        panel.add(lblSuplentes);
        panel.add(Box.createVerticalStrut(5));
        panel.add(new JScrollPane(tablaSuplentes));

        return panel;
    }

    /**
     * 🆕 Crea una tabla para el portero con sus estadísticas especiales.
     */
    private JTable crearTablaPortero(Portero portero) {
        String[] columnas = {
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fís",
                "Saq", "Ref", "Dorsal", "Goles", "TA", "TR"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (portero != null) {
            Object[] fila = {
                    portero.getNombre(),
                    portero.getVelocidad(),
                    portero.getTiro(),
                    portero.getPase(),
                    portero.getDefensa(),
                    portero.getFisico(),
                    portero.getSaque(),
                    portero.getReflejos(),
                    portero.getDorsal(),
                    portero.getGoles(),
                    portero.getTarjetasAmarillas(),
                    portero.getTarjetasRojas()
            };
            modelo.addRow(fila);
        }

        JTable tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(25);

        centrarTextoTabla(tabla);

        return tabla;
    }

    /**
     * Crea una tabla con las estadísticas completas de los jugadores titulares.
     */
    private JTable crearTablaJugadores(Jugador[] jugadores) {
        String[] columnas = {
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fís",
                "Dorsal", "Pos", "Goles", "TA", "TR"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Jugador j : jugadores) {
            Object[] fila = {
                    j.getNombre(),
                    j.getVelocidad(),
                    j.getTiro(),
                    j.getPase(),
                    j.getDefensa(),
                    j.getFisico(),
                    j.getDorsal(),
                    j.getPosicion() != null ? j.getPosicion().toString() : "N/A",
                    j.getGoles(),
                    j.getTarjetasAmarillas(),
                    j.getTarjetasRojas()
            };
            modelo.addRow(fila);
        }

        JTable tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(25);

        centrarTextoTabla(tabla);
        ajustarAnchoColumnas(tabla);

        return tabla;
    }

    /**
     * Crea una tabla simplificada para suplentes.
     */
    private JTable crearTablaSuplentes(Jugador[] suplentes) {
        String[] columnas = {"Nombre", "Dorsal", "Goles", "TA", "TR"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Jugador j : suplentes) {
            Object[] fila = {
                    j.getNombre(),
                    j.getDorsal(),
                    j.getGoles(),
                    j.getTarjetasAmarillas(),
                    j.getTarjetasRojas()
            };
            modelo.addRow(fila);
        }

        JTable tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(25);

        centrarTextoTabla(tabla);

        return tabla;
    }

    /**
     * Aplica un renderer para centrar el texto en todas las columnas.
     */
    private void centrarTextoTabla(JTable tabla) {
        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }

    /**
     * Ajusta el ancho de columnas para mejor visualización.
     */
    private void ajustarAnchoColumnas(JTable tabla) {
        tabla.getColumnModel().getColumn(0).setPreferredWidth(150); // Nombre

        for (int i = 1; i <= 5; i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(50); // Stats
        }

        tabla.getColumnModel().getColumn(6).setPreferredWidth(60);  // Dorsal
        tabla.getColumnModel().getColumn(7).setPreferredWidth(80);  // Posición
        tabla.getColumnModel().getColumn(8).setPreferredWidth(50);  // Goles
        tabla.getColumnModel().getColumn(9).setPreferredWidth(40);  // TA
        tabla.getColumnModel().getColumn(10).setPreferredWidth(40); // TR
    }

    /**
     * 🆕 Actualiza las tablas con los datos más recientes de los equipos.
     * Llama a este método durante el partido para reflejar cambios.
     */
    public void actualizarTablas() {
        actualizarTablaPortero(tablaPorteroLocal, equipoLocal.getPortero());
        actualizarTablaPortero(tablaPorteroVisitante, equipoVisitante.getPortero());
        actualizarTablaJugadores(tablaLocal, equipoLocal.getJugadores());
        actualizarTablaJugadores(tablaVisitante, equipoVisitante.getJugadores());
        actualizarTablaSuplentes(tablaSuplentesLocal, equipoLocal.getReserva());
        actualizarTablaSuplentes(tablaSuplentesVisitante, equipoVisitante.getReserva());
    }

    /**
     * 🆕 Actualiza los datos del portero.
     */
    private void actualizarTablaPortero(JTable tabla, Portero portero) {
        if (portero == null) return;

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        if (modelo.getRowCount() > 0) {
            modelo.setValueAt(portero.getGoles(), 0, 9);             // Columna Goles
            modelo.setValueAt(portero.getTarjetasAmarillas(), 0, 10); // Columna TA
            modelo.setValueAt(portero.getTarjetasRojas(), 0, 11);     // Columna TR
        }
    }

    /**
     * Actualiza los datos de una tabla de titulares.
     */
    private void actualizarTablaJugadores(JTable tabla, Jugador[] jugadores) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        for (int i = 0; i < jugadores.length && i < modelo.getRowCount(); i++) {
            Jugador j = jugadores[i];
            modelo.setValueAt(j.getGoles(), i, 8);             // Columna Goles
            modelo.setValueAt(j.getTarjetasAmarillas(), i, 9); // Columna TA
            modelo.setValueAt(j.getTarjetasRojas(), i, 10);    // Columna TR
        }
    }

    /**
     * Actualiza los datos de una tabla de suplentes.
     */
    private void actualizarTablaSuplentes(JTable tabla, Jugador[] suplentes) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        for (int i = 0; i < suplentes.length && i < modelo.getRowCount(); i++) {
            Jugador j = suplentes[i];
            modelo.setValueAt(j.getGoles(), i, 2);             // Columna Goles
            modelo.setValueAt(j.getTarjetasAmarillas(), i, 3); // Columna TA
            modelo.setValueAt(j.getTarjetasRojas(), i, 4);     // Columna TR
        }
    }
}