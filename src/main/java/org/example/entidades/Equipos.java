package org.example.entidades;

import org.example.enums.Posicion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa un equipo de fútbol completo con jugadores titulares y suplentes.
 * <p>
 * Un equipo se compone de:
 * <ul>
 *   <li>1 portero ({@link Portero})</li>
 *   <li>10 jugadores de campo (lista {@code jugadores})</li>
 *   <li>Suplentes ilimitados (lista {@code reserva})</li>
 * </ul>
 * <p>
 * <b>Nota</b>: Actualmente {@link #isFull()} valida 10 jugadores, pero
 * un equipo completo en cancha tiene 11 (10 + portero).
 *
 * @author HectorA15
 * @version 0.1
 */
public class Equipos {

    private final Map<Jugador, Posicion> posicionesAsignadas = new LinkedHashMap<>();

    /**
     * El portero del equipo. Puede ser null si no está asignado.
     */
    private Portero portero;

    /**
     * Indica si el equipo juega como local (true) o visitante (false).
     */
    private boolean isLocal;

    /**
     * Nombre del equipo.
     */
    private String nombre;

    /**
     * Lista de jugadores titulares (máximo 10).
     */
    private final List<Jugador> jugadores = new ArrayList<>(10);

    /**
     * Lista de jugadores suplentes (sin límite).
     */
    private final List<Jugador> reserva = new ArrayList<>();

    private Formacion formacion;

    /**
     * Crea un equipo vacío sin jugadores ni nombre asignado.
     */
    public Equipos() {
    }

    /**
     * Establece el nombre del equipo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Asigna el portero del equipo.
     * <p>
     * <b>Advertencia</b>: No valida si ya existe un portero.
     * Llamadas múltiples sobrescriben el anterior.
     */
    public void setPortero(Portero portero) {
        this.portero = portero;
    }

    public void setFormacion(Formacion formacion) {
        this.formacion = formacion;
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
     * Agrega un jugador a la lista de titulares.
     *
     * @param jugador el jugador a agregar
     */
    public boolean setJugador(Jugador jugador) {
        if (jugadores.size() >= 10) {
            return false;  // No se pudo agregar
        }
        this.jugadores.add(jugador);
        return true;
    }

    /**
     * Agrega un jugador a la lista de suplentes.
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
     * @param posicion índice del jugador
     * @return el jugador en esa posición
     * @throws IndexOutOfBoundsException si posición inválida
     */
    public Jugador getJugador(int posicion) {
        return jugadores.get(posicion);
    }

    /**
     * Obtiene un jugador suplente por su posición en la lista.
     *
     * @param posicion índice del suplente
     * @return el suplente en esa posición
     * @throws IndexOutOfBoundsException si posición inválida
     */
    public Jugador getReserva(int posicion) {
        return reserva.get(posicion);
    }

    /**
     * Obtiene el nombre del equipo.
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
     * Verifica si la lista de titulares está completa (10 jugadores).
     * <p>
     * <b>Nota</b>: Un equipo en cancha tiene 11 jugadores (10 + portero).
     *
     * @return {@code true} si hay exactamente 10 jugadores titulares
     */
    public boolean isTitularesCompletos() {
        return jugadores.size() == 10;
    }

    /**
     * Verifica si el equipo esta completo (11 jugadores).
     *
     * @return
     */

    public boolean isFull() {
        return (isTitularesCompletos() && portero != null);
    }

    public void asignarPosiciones(Random random){

        int defensas = formacion.getDefensas();
        int mediocampistas = formacion.getMediocampistas();
        int delanteros = formacion.getDelanteros();


        portero.setPosicion(Posicion.PORTERIA);


        for (int i = 0; i < defensas; i++) {
            jugadores.get(i).setPosicion(Posicion.DEFENSA);
        }
        for (int i = defensas; i < defensas + mediocampistas; i++) {
            jugadores.get(i).setPosicion(Posicion.MEDIOCAMP);
        }
        for (int i = defensas + mediocampistas; i < jugadores.size(); i++) {
            jugadores.get(i).setPosicion(Posicion.DELANTERO);
        }
    }


    public Posicion getPosicionAsignada(Jugador jugador) {
        return posicionesAsignadas.get(jugador);
    }

    public Map<Jugador, Posicion> getPosicionesAsignadas() {
        return Map.copyOf(posicionesAsignadas);
    }
    }
    /**
     * Genera representación en texto del equipo completo.
     * <p>
     * Formato:
     * <ul>
     *   <li>Nombre del equipo y tipo (Local/Visitante)</li>
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
        sb.append("\"").append(this.nombre).append("\"").append("\n\n");
        sb.append(" ".repeat(35)).append("Equipo ")
                .append(isLocal ? "Local" : "Visitante").append("\n\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %5s %5s %5s %5s",
                "Nombre", "Posicion", "Vel", "Tiro", "Pase", "Def", "Fis", "Saq", "Ref", "Pos\n\n"));
        sb.append("PORTERO").append("\n");
        sb.append(portero).append("\n\n");

        sb.append("JUGADORES").append("\n");
        for (Jugador jugador : jugadores) {
            sb.append(jugador).append("\n");
        }
        sb.append("\n");
        sb.append("RESERVA").append("\n");
        for (Jugador jugador : reserva) {
            sb.append(jugador).append("\n");
        }
        return sb.toString();
    }
}