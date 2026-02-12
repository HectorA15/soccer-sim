package org.example.entidades;

import org.example.enums.Posicion;
import org.example.nombres.JugadoresNombres;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa un equipo de fútbol completo.
 * Un equipo tiene: 1 portero, 10 jugadores titulares, suplentes y una formación.
 */
public class Equipos {
    private int goles;
    private Portero portero;
    private boolean isLocal;
    private String nombre;
    private int tarjetasAmarillas;
    private int tarjetasRojas;
    private Formacion formacion;
    private final List<Jugador> jugadores = new ArrayList<>(10);
    private final List<Jugador> reserva = new ArrayList<>();

    // Contador de cambios realizados en el partido.
    private int cambiosRealizados = 0;

    // Límite máximo de cambios permitidos por partido.
    private static final int MAX_CAMBIOS = 5;

    // ===== CONSTRUCTORES =====
    public Equipos() {
    }

    public Equipos(String nombre) {
        this.nombre = nombre;
        inicializarEquipo();
    }

    // Inicializa portero, titulares, suplentes y formación por defecto.
    private void inicializarEquipo() {
        // Crear portero
        Portero nuevoPortero = new Portero("Portero del " + nombre);
        nuevoPortero.setRandomStats();
        this.portero = nuevoPortero;

        // Crear 10 jugadores titulares
        for (int i = 0; i < 10; i++) {
            String nombreJugador = JugadoresNombres.getNombreAleatorio();
            Jugador jugador = new Jugador(nombreJugador);
            jugador.setRandomStats();
            this.jugadores.add(jugador);
        }

        // Crear 5 jugadores en la banca (suplentes)
        for (int i = 0; i < 5; i++) {
            String nombreJugador = JugadoresNombres.getNombreAleatorio();
            Jugador jugador = new Jugador(nombreJugador);
            jugador.setRandomStats();
            this.reserva.add(jugador);
        }

        // Establecer formación por defecto
        this.formacion = new Formacion(4, 4, 2);
        asignarPosiciones();
    }

    // ===== GETTERS =====
    public Portero getPortero() {
        return portero;
    }

    public Formacion getFormacion() {
        return formacion;
    }

    public Jugador getJugadorRandom() {
        return jugadores.get((int) (Math.random() * jugadores.size()));
    }

    public Jugador getJugador(int posicion) {
        return jugadores.get(posicion);
    }

    public Jugador getReserva(int posicion) {
        return reserva.get(posicion);
    }

    public String getNombre() {
        return nombre;
    }

    public int getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }
    public int getTarjetasRojas() {
        return tarjetasRojas;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public int getGoles() {
        return goles;
    }

    public Jugador[] getJugadores() {
        return jugadores.toArray(new Jugador[0]);
    }

    public Jugador[] getReserva() {
        return reserva.toArray(new Jugador[0]);
    }

    public boolean isPortero() {
        return portero != null;
    }

    public boolean isFull() {
        return portero != null && jugadores.size() == 10;
    }

    // ===== SETTERS =====
    public void setFormacion(Formacion formacion) {
        if (formacion != null && !formacion.isValida()) {
            throw new IllegalArgumentException("Formación inválida: debe sumar 10 jugadores");
        }
        this.formacion = formacion;
    }

    // Agrega un jugador a titulares. Devuelve false si ya hay 10.
    public boolean setJugador(Jugador jugador) {
        if (jugadores.size() >= 10) {
            return false;
        }
        this.jugadores.add(jugador);
        return true;
    }

    public int setTarjetasAmarillas() {
        return this.tarjetasAmarillas++;
    }
    public int setTarjetasRojas() {
        return this.tarjetasRojas++;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public void setReserva(Jugador jugador) {
        this.reserva.add(jugador);
    }

    // Asigna el portero (sobrescribe si ya existe).
    public void setPortero(Portero portero) {
        this.portero = portero;
    }

    // ===== METODOS DE EQUIPO =====
    public void asignarPosiciones() {
        if (formacion == null) {
            for (Jugador jugador : jugadores) {
                jugador.setPosicion(null);
            }
            return;
        }

        int index = 0;

        // Asignar defensas
        for (int i = 0; i < formacion.getDefensas() && index < jugadores.size(); i++, index++) {
            jugadores.get(index).setPosicion(Posicion.DEFENSA);
        }

        // Asignar mediocampistas
        for (int i = 0; i < formacion.getMediocampistas() && index < jugadores.size(); i++, index++) {
            jugadores.get(index).setPosicion(Posicion.MEDIOCAMP);
        }

        // Asignar delanteros
        for (int i = 0; i < formacion.getDelanteros() && index < jugadores.size(); i++, index++) {
            jugadores.get(index).setPosicion(Posicion.DELANTERO);
        }
    }

    public void asignarPosicionesAleatorias() {
        if (formacion == null) {
            for (Jugador jugador : jugadores) {
                jugador.setPosicion(null);
            }
            return;
        }

        Collections.shuffle(jugadores);  // ESTA es la diferencia
        asignarPosiciones();
    }

    // Realiza un cambio si cumple las validaciones.
    public boolean cambio(Jugador sale, Jugador entra) {

        if (cambiosRealizados >= MAX_CAMBIOS) {
            return false;
        }

        if (!jugadores.contains(sale)) {
            return false;
        }

        if (!reserva.contains(entra)) {
            return false;
        }

        jugadores.remove(sale);
        reserva.remove(entra);

        jugadores.add(entra);
        reserva.add(sale);

        cambiosRealizados++;

        return true;
    }

    // Reasigna posiciones segun la formacion actual.
    public boolean cambioPosiciones() {

        if (formacion == null) {
            return false;
        }

        asignarPosiciones();
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(this.nombre).append("\"").append("\n");

        if (formacion != null) {
            sb.append("Formación: ").append(formacion).append("\n");
        }

        sb.append("\n");
        sb.append(" ".repeat(35)).append("Equipo ")
                .append(isLocal ? "Local" : "Visitante").append("\n\n");

        sb.append("PORTERO\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %5s %5s %10s\n\n",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Saq", "Ref", "Posición"));
        sb.append(portero).append("\n\n");

        sb.append("JUGADORES\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %10s\n\n",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Posición"));

        for (Jugador jugador : jugadores) {
            sb.append(jugador).append("\n");
        }

        sb.append("\nRESERVA\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %10s\n\n",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Posición"));

        for (Jugador jugador : reserva) {
            sb.append(jugador).append("\n");
        }

        return sb.toString();
    }
}

