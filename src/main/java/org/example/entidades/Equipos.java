package org.example.entidades;

import org.example.enums.Posicion;
import org.example.nombres.JugadoresNombres;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Representa un equipo de fútbol completo.
 * Un equipo tiene: 1 portero, 10 jugadores titulares, suplentes y una formación.
 */
public class Equipos {
    // Límite máximo de cambios permitidos por partido.
    private static final int MAX_CAMBIOS = 5;
    private final List<Jugador> jugadores = new ArrayList<>(10);
    private final List<Jugador> reserva = new ArrayList<>();
    private int goles;
    private Portero portero;
    private boolean isLocal;
    private String nombre;
    private int tarjetasAmarillas;
    private int tarjetasRojas;
    private Formacion formacion;
    // Contador de cambios realizados en el partido.
    private int cambiosRealizados = 0;

    // ===== CONSTRUCTORES =====
    public Equipos() {
    }

    public Equipos(String nombre) {
        this.nombre = nombre;
        inicializarEquipo();
    }

    // Inicializa portero, titulares, suplentes y formación por defecto.
    private void inicializarEquipo() {
        // Obtener COPIA de todos los nombres disponibles
        String[] todosLosNombres = JugadoresNombres.getJugadores();
        List<String> nombresDisponibles = new ArrayList<>(Arrays.asList(todosLosNombres));
        Collections.shuffle(nombresDisponibles); // Mezclar

        // Verificar que haya suficientes nombres
        if (nombresDisponibles.size() < 16) { // 1 portero + 10 titulares + 5 suplentes
            throw new IllegalStateException(
                    "No hay suficientes nombres disponibles. Se necesitan al menos 16."
            );
        }

        int index = 0;

        // Crear portero
        Portero nuevoPortero = new Portero(nombresDisponibles.get(index++));
        nuevoPortero.setRandomStats();
        this.portero = nuevoPortero;

        // Crear 10 jugadores titulares
        for (int i = 0; i < 10; i++) {
            Jugador jugador = new Jugador(nombresDisponibles.get(index++));
            jugador.setRandomStats();
            this.jugadores.add(jugador);
        }

        // Crear 5 jugadores en la banca (suplentes)
        for (int i = 0; i < 5; i++) {
            Jugador jugador = new Jugador(nombresDisponibles.get(index++));
            jugador.setRandomStats();
            this.reserva.add(jugador);
        }

        // Establecer formación por defecto
        this.formacion = new Formacion(4, 4, 2);
        asignarPosiciones();

        asignarDorsales();
    }

    // ===== GETTERS =====
    public Portero getPortero() {
        return portero;
    }

    public Formacion getFormacion() {
        return formacion;
    }

    // ===== SETTERS =====
    public void setFormacion(Formacion formacion) {
        if (formacion != null && !formacion.isValida()) {
            throw new IllegalArgumentException("Formación inválida: debe sumar 10 jugadores");
        }
        this.formacion = formacion;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
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

    public void setReserva(Jugador jugador) {
        this.reserva.add(jugador);
    }

    public boolean isPortero() {
        return portero != null;
    }

    // Asigna el portero (sobrescribe si ya existe).
    public void setPortero(Portero portero) {
        this.portero = portero;
    }

    public boolean isFull() {
        return portero != null && jugadores.size() == 10;
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

    // ===== METODOS DE EQUIPO =====
    /**
     * 🆕 Obtiene un jugador aleatorio que NO esté expulsado.
     * @return jugador disponible o null si todos están expulsados
     */
    public Jugador getJugadorRandom() {
        List<Jugador> disponibles = new ArrayList<>();
        for (Jugador j : jugadores) {
            if (!j.isExpulsado()) {
                disponibles.add(j);
            }
        }

        if (disponibles.isEmpty()) {
            return null; // Todos expulsados (caso extremo)
        }

        return disponibles.get((int) (Math.random() * disponibles.size()));
    }

    /**
     * 🆕 Cuenta cuántos jugadores en cancha NO están expulsados.
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
    /**
     * Realiza un cambio si cumple las validaciones.
     * @param sale Jugador titular que sale
     * @param entra Jugador suplente que entra
     * @return true si el cambio fue exitoso
     */
    public boolean cambio(Jugador sale, Jugador entra) {

        if (cambiosRealizados >= MAX_CAMBIOS) {
            return false; // Ya se hicieron 5 cambios
        }

        if (!jugadores.contains(sale)) {
            return false; // El jugador que sale no está en titulares
        }

        if (!reserva.contains(entra)) {
            return false; // El jugador que entra no está en suplentes
        }

        // No permitir que entre un jugador lesionado o expulsado
        if (entra.getLesiones() > 0 || entra.isExpulsado()) {
            return false;
        }

        // Realizar el intercambio
        jugadores.remove(sale);
        reserva.remove(entra);

        jugadores.add(entra);
        reserva.add(sale);

        // Heredar la posición del jugador que sale
        entra.setPosicion(sale.getPosicion());

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

    public void asignarDorsales() {
        // Asignar dorsal 1 al portero
        if (portero != null) {
            portero.setDorsal(1);
        }

        // Asignar dorsales 2-11 a titulares
        int dorsalActual = 2;
        for (Jugador jugador : jugadores) {
            jugador.setDorsal(dorsalActual);
            dorsalActual++;
        }

        // Asignar dorsales 12+ a suplentes
        for (Jugador jugador : reserva) {
            jugador.setDorsal(dorsalActual);
            dorsalActual++;
        }
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

