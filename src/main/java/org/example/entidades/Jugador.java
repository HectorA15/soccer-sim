package org.example.entidades;

import org.example.enums.Posicion;

/**
 * Representa un jugador de fútbol con sus estadísticas básicas.
 * <p>
 * Cada jugador tiene 5 atributos numéricos que determinan su desempeño:
 * velocidad, tiro, pase, defensa y físico. Los valores van de 1 a 100.
 * <p>
 * Esta clase es la base para {@link Portero}, que extiende las estadísticas
 * con atributos específicos del arquero.
 *
 * @author HectorA15
 * @version 0.2
 * @since 2026-02-07
 */
public class Jugador {

    /** Nombre completo del jugador. Inmutable después de la creación. */
    private final String nombre;

    /** Posición del jugador en el campo. */
    private Posicion posicion;

    /** Velocidad del jugador (1-100). Afecta carreras y recuperación. */
    private int velocidad;

    /** Capacidad de disparo (1-100). Determina precisión y potencia. */
    private int tiro;

    /** Habilidad de pase (1-100). Influye en asistencias y posesión. */
    private int pase;

    /** Capacidad defensiva (1-100). Afecta recuperaciones e intercepciones. */
    private int defensa;

    /** Resistencia física (1-100). Reduce probabilidad de lesiones. */
    private int fisico;

    /**
     * Crea un jugador con nombre especificado y estadísticas en 0.
     * <p>
     * La posición se asigna como {@code null}. Usar {@link #setPosicion(Posicion)}
     * para asignarla, o usar el constructor {@link #Jugador(String, Posicion)}.
     * <p>
     * Usar {@link #setRandomStats()} para asignar valores aleatorios,
     * o los setters protegidos para valores específicos.
     *
     * @param nombre el nombre completo del jugador
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.posicion = null;
    }

    /**
     * Crea un jugador con nombre y posición especificados.
     * <p>
     * Las estadísticas se inicializan en 0. Usar {@link #setRandomStats()}
     * o los setters protegidos para asignar valores.
     *
     * @param nombre el nombre completo del jugador
     * @param posicion la posición del jugador en el campo
     */
    public Jugador(String nombre, Posicion posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
    }

    /**
     * Crea un jugador con nombre y estadísticas específicas.
     * <p>
     * La posición se asigna como {@code null}. No valida rangos (1-100).
     *
     * @param nombre el nombre del jugador
     * @param velocidad valor inicial de velocidad
     * @param tiro valor inicial de tiro
     * @param pase valor inicial de pase
     * @param defensa valor inicial de defensa
     * @param fisico valor inicial de físico
     */
    public Jugador(String nombre, int velocidad, int tiro, int pase, int defensa, int fisico) {
        this.nombre = nombre;
        this.posicion = null;
        this.velocidad = velocidad;
        this.tiro = tiro;
        this.pase = pase;
        this.defensa = defensa;
        this.fisico = fisico;
    }

    // --- Setters protegidos ---

    /**
     * Modifica la velocidad del jugador.
     *
     * @param velocidad nuevo valor de velocidad (sin validación de rango)
     */
    protected void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    protected void setTiro(int tiro) { this.tiro = tiro; }
    protected void setPase(int pase) { this.pase = pase; }
    protected void setDefensa(int defensa) { this.defensa = defensa; }
    protected void setFisico(int fisico) { this.fisico = fisico; }

    /**
     * Asigna la posición del jugador.
     *
     * @param posicion la posición en el campo
     */
    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    // --- Getters ---

    /**
     * Obtiene el nombre del jugador.
     *
     * @return nombre completo inmutable
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene la posición del jugador.
     *
     * @return posición del jugador, o {@code null} si no está asignada
     */
    public Posicion getPosicion() { return posicion; }

    public int getVelocidad() { return velocidad; }
    public int getTiro() { return tiro; }
    public int getPase() { return pase; }
    public int getDefensa() { return defensa; }
    public int getFisico() { return fisico; }

    /**
     * Asigna valores aleatorios (1-100) a todas las estadísticas.
     * <p>
     * Utiliza {@link Math#random()} para generar valores. Puede ser
     * sobreescrito en subclases para agregar stats adicionales.
     *
     * @see Portero#setRandomStats()
     */
    public void setRandomStats() {
        setVelocidad(1 + (int)(Math.random() * 100));
        setTiro(1 + (int)(Math.random() * 100));
        setPase(1 + (int)(Math.random() * 100));
        setDefensa(1 + (int)(Math.random() * 100));
        setFisico(1 + (int)(Math.random() * 100));
    }

    /**
     * Genera una representación formateada de las estadísticas.
     * <p>
     * Formato: nombre (25 chars) + 5 stats (5 chars cada una) + posición (8 chars).
     *
     * @return string formateado con nombre, estadísticas y posición
     */
    public String getStats() {
        String posStr = (posicion != null) ? posicion.toString() : "N/A";
        return String.format("%-25s %5d %5d %5d %5d %5d %10s",
                nombre, velocidad, tiro, pase, defensa, fisico, posStr);
    }

    /**
     * Devuelve la representación en string del jugador.
     * <p>
     * Delega a {@link #getStats()} para mantener formato consistente.
     *
     * @return estadísticas formateadas
     */
    @Override
    public String toString() {
        return getStats();
    }
}