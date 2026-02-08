package org.example.entidades;

import org.example.enums.Posicion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa un equipo de fútbol completo con jugadores titulares y suplentes.
 * <p>
 * Un equipo se compone de:
 * <ul>
 *   <li>1 portero ({@link Portero})</li>
 *   <li>10 jugadores de campo (lista {@code jugadores})</li>
 *   <li>Suplentes ilimitados (lista {@code reserva})</li>
 *   <li>Una formación táctica ({@link Formacion})</li>
 * </ul>
 *
 * @author HectorA15
 * @version 0.2
 */
public class Equipos {

    /** El portero del equipo. Puede ser null si no está asignado. */
    private Portero portero;

    /** Indica si el equipo juega como local (true) o visitante (false). */
    private boolean isLocal;

    /** Nombre del equipo. */
    private String nombre;

    /** Formación táctica del equipo (ej: 4-4-2). */
    private Formacion formacion;

    /** Lista de jugadores titulares (máximo 10). */
    private List<Jugador> jugadores = new ArrayList<>(10);

    /** Lista de jugadores suplentes (sin límite). */
    private List<Jugador> reserva = new ArrayList<>();

    /**
     * Crea un equipo vacío sin jugadores ni nombre asignado.
     */
    public Equipos() {
    }

    /**
     * Establece el nombre del equipo.
     *
     * @param nombre el nombre del equipo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Asigna el portero del equipo.
     * <p>
     * <b>Advertencia</b>: No valida si ya existe un portero.
     * Llamadas múltiples sobrescriben el anterior.
     *
     * @param portero el portero a asignar
     */
    public void setPortero(Portero portero) {
        this.portero = portero;
    }

    /**
     * Obtiene el portero del equipo.
     *
     * @return el portero, o {@code null} si no está asignado
     */
    public Portero getPortero() {
        return portero;
    }

    /**
     * Establece la formación táctica del equipo.
     *
     * @param formacion la formación a asignar
     * @throws IllegalArgumentException si la formación es inválida (suma != 10)
     */
    public void setFormacion(Formacion formacion) {
        if (formacion != null && !formacion.isValida()) {
            throw new IllegalArgumentException("Formación inválida: debe sumar 10 jugadores");
        }
        this.formacion = formacion;
    }

    /**
     * Obtiene la formación táctica del equipo.
     *
     * @return la formación, o {@code null} si no está asignada
     */
    public Formacion getFormacion() {
        return formacion;
    }

    /**
     * Agrega un jugador a la lista de titulares.
     * <p>
     * Valida que no se exceda el límite de 10 jugadores titulares.
     *
     * @param jugador el jugador a agregar
     * @return {@code true} si se agregó exitosamente, {@code false} si el equipo ya está lleno
     */
    public boolean setJugador(Jugador jugador) {
        if (jugadores.size() >= 10) {
            return false;  // Equipo titular completo
        }
        this.jugadores.add(jugador);
        return true;
    }

    /**
     * Agrega un jugador a la lista de suplentes.
     *
     * @param jugador el jugador suplente
     */
    public void setReserva(Jugador jugador) {
        this.reserva.add(jugador);
    }

    /**
     * Define si el equipo es local o visitante.
     *
     * @param isLocal {@code true} para local, {@code false} para visitante
     */
    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    /**
     * Obtiene un jugador titular por su posición en la lista.
     *
     * @param posicion índice del jugador (0-based)
     * @return el jugador en esa posición
     * @throws IndexOutOfBoundsException si posición inválida
     */
    public Jugador getJugador(int posicion) {
        return jugadores.get(posicion);
    }

    /**
     * Obtiene un jugador suplente por su posición en la lista.
     *
     * @param posicion índice del suplente (0-based)
     * @return el suplente en esa posición
     * @throws IndexOutOfBoundsException si posición inválida
     */
    public Jugador getReserva(int posicion) {
        return reserva.get(posicion);
    }

    /**
     * Obtiene el nombre del equipo.
     *
     * @return el nombre del equipo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Verifica si el equipo es local.
     *
     * @return {@code true} si es local, {@code false} si es visitante
     */
    public boolean isLocal() {
        return isLocal;
    }

    /**
     * Obtiene todos los jugadores titulares como arreglo.
     * <p>
     * Crea una copia para evitar modificaciones externas.
     *
     * @return arreglo con los jugadores titulares
     */
    public Jugador[] getJugadores() {
        return jugadores.toArray(new Jugador[0]);
    }

    /**
     * Obtiene todos los suplentes como arreglo.
     *
     * @return arreglo con los jugadores suplentes
     */
    public Jugador[] getReserva() {
        return reserva.toArray(new Jugador[0]);
    }

    /**
     * Verifica si el equipo tiene portero asignado.
     *
     * @return {@code true} si hay portero, {@code false} en caso contrario
     */
    public boolean isPortero() {
        return portero != null;
    }

    /**
     * Verifica si el equipo está completo (portero + 10 jugadores de campo).
     * <p>
     * Un equipo completo tiene 11 jugadores en total: 1 portero + 10 de campo.
     *
     * @return {@code true} si hay portero y 10 jugadores de campo
     */
    public boolean isFull() {
        return portero != null && jugadores.size() == 10;
    }

    /**
     * Asigna posiciones a los jugadores titulares según la formación,
     * mezclando el orden aleatoriamente antes de distribuir.
     * <p>
     * Esto permite que jugadores con diferentes estadísticas ocupen
     * diferentes posiciones en cada simulación.
     */
    public void asignarPosiciones() {
        if (formacion == null) {
            // Sin formación, todas las posiciones quedan null
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

        // Mezclar jugadores aleatoriamente
        Collections.shuffle(jugadores);

        // Ahora asignar en orden
        asignarPosiciones();
    }

    /**
     * Genera representación en texto del equipo completo.
     * <p>
     * Formato:
     * <ul>
     *   <li>Nombre del equipo y tipo (Local/Visitante)</li>
     *   <li>Formación (si está asignada)</li>
     *   <li>Encabezados de estadísticas</li>
     *   <li>Portero</li>
     *   <li>Jugadores titulares</li>
     *   <li>Jugadores suplentes</li>
     * </ul>
     *
     * @return string formateado con toda la información del equipo
     */
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

        // Header para PORTERO (con Saq y Ref)
        sb.append("PORTERO").append("\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %5s %5s %10s",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Saq", "Ref", "Posición\n\n"));
        sb.append(portero).append("\n\n");

        // Header para JUGADORES (sin Saq ni Ref)
        sb.append("JUGADORES").append("\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %10s",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Posición\n\n"));
        for (Jugador jugador : jugadores) {
            sb.append(jugador).append("\n");
        }

        sb.append("\n");
        sb.append("RESERVA").append("\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %10s",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Posición\n\n"));
        for (Jugador jugador : reserva) {
            sb.append(jugador).append("\n");
        }
        return sb.toString();
    }
}