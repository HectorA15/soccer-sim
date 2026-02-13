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

    /**
     * Constructor que crea un equipo completo con jugadores aleatorios.
     * @param nombre Nombre del equipo
     */
    public Equipos(String nombre) {
        this.nombre = nombre;
        inicializarEquipo();
    }

    // ===== GETTERS =====

    public Portero getPortero() {
        return portero;
    }
    public Formacion getFormacion() {
        return formacion;
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
    public int getCambiosRealizados() {
        return cambiosRealizados;
    }

    public Jugador getJugadorRandom() {
        List<Jugador> disponibles = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            if (!jugador.isExpulsado()) {
                disponibles.add(jugador);
            }
        }
        if (disponibles.isEmpty()) {
            return null;
        }
        return disponibles.get((int) (Math.random() * disponibles.size()));
    }

    public boolean isPortero() {
        return portero != null;
    }




    // ===== SETTERS =====

    public void setFormacion(Formacion formacion) {
        if (formacion != null && !formacion.isValida()) {
            throw new IllegalArgumentException("Formación inválida: debe sumar 10 jugadores");
        }
        this.formacion = formacion;
    }

    public boolean setJugador(Jugador jugador) {
        if (jugadores.size() >= 10) {
            return false;
        }
        this.jugadores.add(jugador);
        return true;
    }

    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setLocal(boolean isLocal) {this.isLocal = isLocal;}
    public void setGoles(int goles) {this.goles = goles;}
    public void setReserva(Jugador jugador) {this.reserva.add(jugador);}
    public void setPortero(Portero portero) {this.portero = portero;}

    // ===== MÉTODOS DE EQUIPO =====
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

}