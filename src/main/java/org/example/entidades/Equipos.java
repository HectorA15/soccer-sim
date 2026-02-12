package org.example.entidades;

import org.example.enums.Posicion;
import org.example.nombres.JugadoresNombres;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Representa un equipo de fútbol completo.
 * Un equipo tiene: 1 portero, 10 jugadores titulares, 5 suplentes y una formación.
 * Maneja cambios, expulsiones, goles y tarjetas del equipo.
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

    private int cambiosRealizados = 0;
    private static final int MAX_CAMBIOS = 5;

    // ===== CONSTRUCTORES =====

    public Equipos() {
    }

    /**
     * Constructor que crea un equipo completo con jugadores aleatorios.
     * @param nombre Nombre del equipo
     */
    public Equipos(String nombre) {
        this.nombre = nombre;
        inicializarEquipo();
    }

    /**
     * Inicializa el equipo con portero, 10 titulares, 5 suplentes y formación por defecto.
     * Asigna nombres únicos a todos los jugadores y dorsales.
     */
    private void inicializarEquipo() {
        String[] todosLosNombres = JugadoresNombres.getJugadores();
        List<String> nombresDisponibles = new ArrayList<>(Arrays.asList(todosLosNombres));
        Collections.shuffle(nombresDisponibles);

        if (nombresDisponibles.size() < 16) {
            throw new IllegalStateException(
                    "No hay suficientes nombres disponibles. Se necesitan al menos 16."
            );
        }

        int index = 0;

        Portero nuevoPortero = new Portero(nombresDisponibles.get(index++));
        nuevoPortero.setRandomStats();
        this.portero = nuevoPortero;

        for (int i = 0; i < 10; i++) {
            Jugador jugador = new Jugador(nombresDisponibles.get(index++));
            jugador.setRandomStats();
            this.jugadores.add(jugador);
        }

        for (int i = 0; i < 5; i++) {
            Jugador jugador = new Jugador(nombresDisponibles.get(index++));
            jugador.setRandomStats();
            this.reserva.add(jugador);
        }

        this.formacion = new Formacion(4, 4, 2);
        asignarPosiciones();
        asignarDorsales();
    }

    // ===== GETTERS =====

    /**
     * Obtiene el portero del equipo.
     * @return Portero del equipo
     */
    public Portero getPortero() {
        return portero;
    }

    /**
     * Obtiene la formación actual del equipo.
     * @return Formación del equipo
     */
    public Formacion getFormacion() {
        return formacion;
    }

    /**
     * Obtiene un jugador aleatorio que NO esté expulsado.
     * @return Jugador disponible o null si todos están expulsados
     */
    public Jugador getJugadorRandom() {
        List<Jugador> disponibles = new ArrayList<>();
        for (Jugador j : jugadores) {
            if (!j.isExpulsado()) {
                disponibles.add(j);
            }
        }

        if (disponibles.isEmpty()) {
            return null;
        }

        return disponibles.get((int) (Math.random() * disponibles.size()));
    }

    /**
     * Obtiene el nombre del equipo.
     * @return Nombre del equipo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el total de tarjetas amarillas del equipo.
     * @return Cantidad de tarjetas amarillas
     */
    public int getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    /**
     * Obtiene el total de tarjetas rojas del equipo.
     * @return Cantidad de tarjetas rojas
     */
    public int getTarjetasRojas() {
        return tarjetasRojas;
    }

    /**
     * Verifica si el equipo es local.
     * @return true si es local, false si es visitante
     */
    public boolean isLocal() {
        return isLocal;
    }

    /**
     * Obtiene el total de goles del equipo.
     * @return Cantidad de goles
     */
    public int getGoles() {
        return goles;
    }

    /**
     * Obtiene todos los jugadores titulares como array.
     * @return Array de jugadores titulares
     */
    public Jugador[] getJugadores() {
        return jugadores.toArray(new Jugador[0]);
    }

    /**
     * Obtiene todos los jugadores suplentes como array.
     * @return Array de jugadores suplentes
     */
    public Jugador[] getReserva() {
        return reserva.toArray(new Jugador[0]);
    }

    /**
     * Verifica si el equipo tiene portero asignado.
     * @return true si tiene portero, false si no
     */
    public boolean isPortero() {
        return portero != null;
    }

    /**
     * Obtiene la cantidad de cambios realizados en el partido.
     * @return Número de cambios realizados
     */
    public int getCambiosRealizados() {
        return cambiosRealizados;
    }

    /**
     * Cuenta cuántos jugadores en cancha NO están expulsados.
     * @return Cantidad de jugadores disponibles
     */
    public int contarJugadoresDisponibles() {
        int count = 0;
        for (Jugador j : jugadores) {
            if (!j.isExpulsado()) {
                count++;
            }
        }
        return count;
    }

    // ===== SETTERS =====

    /**
     * Asigna la formación del equipo.
     * @param formacion Nueva formación (debe sumar 10 jugadores)
     * @throws IllegalArgumentException si la formación no es válida
     */
    public void setFormacion(Formacion formacion) {
        if (formacion != null && !formacion.isValida()) {
            throw new IllegalArgumentException("Formación inválida: debe sumar 10 jugadores");
        }
        this.formacion = formacion;
    }

    /**
     * Agrega un jugador a titulares.
     * @param jugador Jugador a agregar
     * @return true si se agregó exitosamente, false si ya hay 10 titulares
     */
    public boolean setJugador(Jugador jugador) {
        if (jugadores.size() >= 10) {
            return false;
        }
        this.jugadores.add(jugador);
        return true;
    }

    /**
     * Asigna el nombre del equipo.
     * @param nombre Nombre del equipo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Asigna si el equipo es local o visitante.
     * @param isLocal true para local, false para visitante
     */
    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    /**
     * Asigna el total de goles del equipo.
     * @param goles Cantidad de goles
     */
    public void setGoles(int goles) {
        this.goles = goles;
    }

    /**
     * Agrega un jugador a la banca de suplentes.
     * @param jugador Jugador a agregar como suplente
     */
    public void setReserva(Jugador jugador) {
        this.reserva.add(jugador);
    }

    /**
     * Asigna el portero del equipo (reemplaza si ya existe uno).
     * @param portero Nuevo portero
     */
    public void setPortero(Portero portero) {
        this.portero = portero;
    }

    // ===== MÉTODOS DE EQUIPO =====

    /**
     * Asigna posiciones a los jugadores según la formación actual.
     * Los primeros jugadores serán defensas, luego mediocampistas y finalmente delanteros.
     */
    public void asignarPosiciones() {
        if (formacion == null) {
            for (Jugador jugador : jugadores) {
                jugador.setPosicion(null);
            }
            return;
        }

        int index = 0;

        for (int i = 0; i < formacion.getDefensas() && index < jugadores.size(); i++, index++) {
            jugadores.get(index).setPosicion(Posicion.DEFENSA);
        }

        for (int i = 0; i < formacion.getMediocampistas() && index < jugadores.size(); i++, index++) {
            jugadores.get(index).setPosicion(Posicion.MEDIOCAMP);
        }

        for (int i = 0; i < formacion.getDelanteros() && index < jugadores.size(); i++, index++) {
            jugadores.get(index).setPosicion(Posicion.DELANTERO);
        }
    }


    /**
     * Asigna dorsales únicos a todos los jugadores del equipo.
     * Portero: dorsal 1
     * Titulares: dorsales 2-11
     * Suplentes: dorsales 12-16
     */
    public void asignarDorsales() {
        if (portero != null) {
            portero.setDorsal(1);
        }

        int dorsalActual = 2;
        for (Jugador jugador : jugadores) {
            jugador.setDorsal(dorsalActual);
            dorsalActual++;
        }

        for (Jugador jugador : reserva) {
            jugador.setDorsal(dorsalActual);
            dorsalActual++;
        }
    }

    /**
     * Realiza un cambio de jugadores si cumple con todas las validaciones.
     * Valida: máximo de cambios, que los jugadores existan en las listas correctas,
     * y que el jugador que entra no esté lesionado ni expulsado.
     * @param sale Jugador titular que sale del campo
     * @param entra Jugador suplente que entra al campo
     * @return true si el cambio fue exitoso, false si no cumple las validaciones
     */
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

        if (entra.getLesiones() > 0 || entra.isExpulsado()) {
            return false;
        }

        jugadores.remove(sale);
        reserva.remove(entra);

        jugadores.add(entra);
        reserva.add(sale);

        entra.setPosicion(sale.getPosicion());

        cambiosRealizados++;

        return true;
    }


    //Solo se uso para mostrar el equipo en consola, no es necesario modificarlo y no tiene ningun uso actualmente
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