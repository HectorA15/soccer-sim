package org.example.entidades;

import org.example.enums.Posicion;

/**
 * Representa un jugador de fútbol con sus estadísticas básicas.
 * <p>
 * Cada jugador tiene 5 atributos numéricos que determinan su desempeño:
 * velocidad, tiro, pase, defensa y físico. Los valores van de 1 a 100.
 * <p>
 * Esta clase es la base para {@link Portero}, que extiende las estadísticas
 * con atributos específicos del portero.
 *
 * @author HectorA15
 * @version 0.1
 * @since 2026-02-07
 */
public class Jugador {

    /** Nombre completo del jugador. Inmutable después de la creación. */
    private final String nombre;

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

    private Posicion posicion;

    /**
     * Crea un jugador con nombre especificado y estadísticas en 0.
     * <p>
     * Usar {@link #setRandomStats()} para asignar valores aleatorios,
     * o los setters protegidos para valores específicos.
     *
     * @param nombre el nombre completo del jugador
     * @throws NullPointerException si nombre es null (sin validación actual)
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Crea un jugador con nombre y estadísticas específicas.
     * <p>
     * No asigna rangos (1-100). Responsabilidad del llamador.
     */
    public Jugador(String nombre, int velocidad, int tiro, int pase, int defensa, int fisico) {
        this.nombre = nombre;
        this.velocidad = velocidad;
        this.tiro = tiro;
        this.pase = pase;
        this.defensa = defensa;
        this.fisico = fisico;
    }

    // --- Setters protegidos ---

    /**
     * Modifican las estadisticas del jugador.
     * <p>
     * Métdo protegido para permitir que {@link Portero} herede
     * la capacidad de modificar stats sin exponer setters públicos.
     */
    protected void setVelocidad(int velocidad) { this.velocidad = velocidad; }
    protected void setTiro(int tiro) { this.tiro = tiro; }
    protected void setPase(int pase) { this.pase = pase; }
    protected void setDefensa(int defensa) { this.defensa = defensa; }
    protected void setFisico(int fisico) { this.fisico = fisico; }
    protected void setPosicion(Posicion posicion) { this.posicion = posicion; }

    // --- Getters ---

    /**
     * Obtiene el nombre y estadisticas del jugador.
     */
    public String getNombre() { return nombre; }

    public int getVelocidad() { return velocidad; }
    public int getTiro() { return tiro; }
    public int getPase() { return pase; }
    public int getDefensa() { return defensa; }
    public int getFisico() { return fisico; }
    public Posicion getPosicion() { return posicion; }

    /**
     * Asigna valores aleatorios (1-100) a todas las estadísticas.
     * <p>
     * Utiliza {@link Math#random()} para generar valores.
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
     * Imprime de manera formateada de las estadísticas.
     * <p>
     * Formato: nombre (25 chars) + 5 stats (5 chars cada una, alineadas derecha).
     *
     * @return string formateado con nombre y estadísticas
     */
    public String getStats() {
        return String.format("%-25s %5d %5d %5d %5d %5d",
                nombre, velocidad, tiro, pase, defensa, fisico);
    }
    @Override
    public String toString() {
        return getStats();
    }
}