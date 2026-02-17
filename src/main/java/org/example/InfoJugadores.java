package org.example;

import org.example.entidades.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InfoJugadores extends JFrame {

    private Equipos equipoLocal;
    private Equipos equipoVisitante;

    private JTable tablaLocal;
    private JTable tablaVisitante;
    private JTable tablaSuplentesLocal;
    private JTable tablaSuplentesVisitante;

    private JLabel lblStatsPoderLocal;
    private JLabel lblStatsPoderVisitante;

    public InfoJugadores(Equipos local, Equipos visitante) {
        this.equipoLocal = local;
        this.equipoVisitante = visitante;

        setTitle("Información de Equipos");
        setSize(1400, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(1, 2, 10, 0));

        // Panel equipo local
        JPanel panelLocal = new JPanel();
        panelLocal.setLayout(new BoxLayout(panelLocal, BoxLayout.Y_AXIS));

        JLabel nombreLocal = new JLabel(local.getNombre());
        nombreLocal.setFont(new Font("Arial", Font.BOLD, 24));
        nombreLocal.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLocal.add(nombreLocal);

        JLabel formacionLocal = new JLabel(local.getFormacion().toString());
        formacionLocal.setFont(new Font("Arial", Font.BOLD, 40));
        formacionLocal.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblStatsPoderLocal = new JLabel(formatPoder(local));
        lblStatsPoderLocal.setFont(new Font("Arial", Font.ITALIC, 16));
        lblStatsPoderLocal.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLocal.add(lblStatsPoderLocal);

        panelLocal.add(formacionLocal);
        panelLocal.add(Box.createVerticalStrut(20));

        JLabel lblTitularesLocal = new JLabel("TITULARES");
        lblTitularesLocal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitularesLocal.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLocal.add(lblTitularesLocal);

        tablaLocal = crearTabla(local.getJugadores());
        panelLocal.add(new JScrollPane(tablaLocal));
        panelLocal.add(Box.createVerticalStrut(20));

        JLabel lblSuplentesLocal = new JLabel("SUPLENTES");
        lblSuplentesLocal.setFont(new Font("Arial", Font.BOLD, 16));
        lblSuplentesLocal.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelLocal.add(lblSuplentesLocal);

        tablaSuplentesLocal = crearTablaSuplentes(local.getReserva());
        panelLocal.add(new JScrollPane(tablaSuplentesLocal));

        // Panel equipo visitante
        JPanel panelVisitante = new JPanel();
        panelVisitante.setLayout(new BoxLayout(panelVisitante, BoxLayout.Y_AXIS));

        JLabel nombreVisitante = new JLabel(visitante.getNombre());
        nombreVisitante.setFont(new Font("Arial", Font.BOLD, 24));
        nombreVisitante.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelVisitante.add(nombreVisitante);

        JLabel formacionVisitante = new JLabel(visitante.getFormacion().toString());
        formacionVisitante.setFont(new Font("Arial", Font.BOLD, 40));
        formacionVisitante.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblStatsPoderVisitante = new JLabel(formatPoder(visitante));
        lblStatsPoderVisitante.setFont(new Font("Arial", Font.ITALIC, 16));
        lblStatsPoderVisitante.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelVisitante.add(lblStatsPoderVisitante);

        panelVisitante.add(formacionVisitante);
        panelVisitante.add(Box.createVerticalStrut(20));

        JLabel lblTitularesVisitante = new JLabel("TITULARES");
        lblTitularesVisitante.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitularesVisitante.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelVisitante.add(lblTitularesVisitante);

        tablaVisitante = crearTabla(visitante.getJugadores());
        panelVisitante.add(new JScrollPane(tablaVisitante));
        panelVisitante.add(Box.createVerticalStrut(20));

        JLabel lblSuplentesVisitante = new JLabel("SUPLENTES");
        lblSuplentesVisitante.setFont(new Font("Arial", Font.BOLD, 16));
        lblSuplentesVisitante.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelVisitante.add(lblSuplentesVisitante);

        tablaSuplentesVisitante = crearTablaSuplentes(visitante.getReserva());
        panelVisitante.add(new JScrollPane(tablaSuplentesVisitante));

        panelPrincipal.add(panelLocal);
        panelPrincipal.add(panelVisitante);
        add(panelPrincipal, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);
    }

    private JTable crearTabla(Jugador[] jugadores) {
        String[] columnas = {"Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Dorsal", "Pos", "Goles", "TA", "TR"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

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
        tabla.setRowHeight(25);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        return tabla;
    }

    private JTable crearTablaSuplentes(Jugador[] suplentes) {
        String[] columnas = {"Nombre", "Dorsal", "Goles", "TA", "TR"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

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
        tabla.setRowHeight(25);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }

        return tabla;
    }

    public void actualizarTablas() {
        DefaultTableModel modeloLocal = (DefaultTableModel) tablaLocal.getModel();
        Jugador[] jugadoresLocal = equipoLocal.getJugadores();
        for (int i = 0; i < jugadoresLocal.length; i++) {
            modeloLocal.setValueAt(jugadoresLocal[i].getGoles(), i, 8);
            modeloLocal.setValueAt(jugadoresLocal[i].getTarjetasAmarillas(), i, 9);
            modeloLocal.setValueAt(jugadoresLocal[i].getTarjetasRojas(), i, 10);
        }

        DefaultTableModel modeloVisitante = (DefaultTableModel) tablaVisitante.getModel();
        Jugador[] jugadoresVisitante = equipoVisitante.getJugadores();
        for (int i = 0; i < jugadoresVisitante.length; i++) {
            modeloVisitante.setValueAt(jugadoresVisitante[i].getGoles(), i, 8);
            modeloVisitante.setValueAt(jugadoresVisitante[i].getTarjetasAmarillas(), i, 9);
            modeloVisitante.setValueAt(jugadoresVisitante[i].getTarjetasRojas(), i, 10);
        }

        DefaultTableModel modeloSuplentesLocal = (DefaultTableModel) tablaSuplentesLocal.getModel();
        Jugador[] suplentesLocal = equipoLocal.getReserva();
        for (int i = 0; i < suplentesLocal.length; i++) {
            modeloSuplentesLocal.setValueAt(suplentesLocal[i].getGoles(), i, 2);
            modeloSuplentesLocal.setValueAt(suplentesLocal[i].getTarjetasAmarillas(), i, 3);
            modeloSuplentesLocal.setValueAt(suplentesLocal[i].getTarjetasRojas(), i, 4);
        }

        DefaultTableModel modeloSuplentesVisitante = (DefaultTableModel) tablaSuplentesVisitante.getModel();
        Jugador[] suplentesVisitante = equipoVisitante.getReserva();
        for (int i = 0; i < suplentesVisitante.length; i++) {
            modeloSuplentesVisitante.setValueAt(suplentesVisitante[i].getGoles(), i, 2);
            modeloSuplentesVisitante.setValueAt(suplentesVisitante[i].getTarjetasAmarillas(), i, 3);
            modeloSuplentesVisitante.setValueAt(suplentesVisitante[i].getTarjetasRojas(), i, 4);
        }
    }

    private String formatPoder(Equipos equipo) {
        return String.format("Ataque: %.1f | Defensa: %.1f",
                equipo.getPoderOfensivo(),
                equipo.getPoderDefensivo());
    }
}