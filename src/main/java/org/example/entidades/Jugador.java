package org.example.entidades;

import org.example.enums.Posicion;

public class Jugador {

    private final String nombre;
    private Posicion posicion;
    private int velocidad;
    private int tiro;
    private int pase;
    private int defensa;
    private int fisico;
    private int dorsal;

    private int lesiones;
    private int tarjetasAmarillas;
    private int tarjetasRojas;
    /**
     * Constructor básico: solo nombre, stats en 0.
     * Debes llamar a setRandomStats() o usar los setters para asignar valores.
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.posicion = null;
    }

    /** Constructor con nombre y posición inicial. */
    public Jugador(String nombre, Posicion posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
    }

    /** Constructor con todos los valores de stats. Útil para testing o jugadores predefinidos. */
    public Jugador(String nombre, int velocidad, int tiro, int pase, int defensa, int fisico) {
        this.nombre = nombre;
        this.posicion = null;
        this.velocidad = velocidad;
        this.tiro = tiro;
        this.pase = pase;
        this.defensa = defensa;
        this.fisico = fisico;
    }

    // ===== SETTERS PROTECTED =====
    // Solo se exponen en subclases.
    protected void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    protected void setTiro(int tiro) {
        this.tiro = tiro;
    }

    protected void setPase(int pase) {
        this.pase = pase;
    }

    protected void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    protected void setFisico(int fisico) {
        this.fisico = fisico;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    public void setLesiones(int lesiones) {
        this.lesiones = lesiones;
    }

    public void setTarjetasAmarillas(int tarjetasAmarillas) {
        this.tarjetasAmarillas = tarjetasAmarillas;
    }

    public void setTarjetasRojas(int tarjetasRojas) {
        this.tarjetasRojas = tarjetasRojas;
    }

    // ===== GETTERS =====

    public String getNombre() {
        return nombre;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getTiro() {
        return tiro;
    }

    public int getPase() {
        return pase;
    }

    public int getDefensa() {
        return defensa;
    }

    public int getFisico() {
        return fisico;
    }

    public int getDorsal() {
        return dorsal;
    }

    public int getLesiones() {
        return lesiones;
    }

    public int getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    public int getTarjetasRojas() {
        return tarjetasRojas;
    }

    // Asigna valores aleatorios (1-100) a todas las estadisticas.
    public void setRandomStats() {
        setVelocidad(1 + (int)(Math.random() * 100));
        setTiro(1 + (int)(Math.random() * 100));
        setPase(1 + (int)(Math.random() * 100));
        setDefensa(1 + (int)(Math.random() * 100));
        setFisico(1 + (int)(Math.random() * 100));
    }

    public String getStats() {
        String posStr = (posicion != null) ? posicion.toString() : "N/A";
        return String.format("%-25s %5d %5d %5d %5d %5d %10s",
                nombre, velocidad, tiro, pase, defensa, fisico, posStr);
    }

    @Override
    public String toString() {
        return getStats();
    }
}