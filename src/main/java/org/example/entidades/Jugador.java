package org.example.entidades;

import org.example.enums.Posicion;

/**
 * Representa un jugador de fútbol con sus estadísticas y estado durante el partido.
 * Incluye atributos como velocidad, tiro, pase, defensa, físico, goles, tarjetas y lesiones.
 */
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
    private int goles;
    private boolean expulsado;


    // Las estadísticas deben asignarse despues con setRandomStats() o los setters.
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.posicion = null;
        this.goles = 0;
        this.expulsado = false;
    }

    // ===== SETTERS =====

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
    protected void setFisico(int fisico) { this.fisico = fisico; }
    protected void setDorsal(int dorsal) {this.dorsal = dorsal;}
    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
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
    public void setGoles(int goles) {
        this.goles = goles;
    }
    public void setExpulsado(boolean expulsado) {
        this.expulsado = expulsado;
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
    public int getGoles() {
        return goles;
    }
    public boolean isExpulsado() {
        return expulsado;
    }



    // ===== MÉTODOS DE TARJETAS =====

    // Agrega una tarjeta amarilla al jugador.
    // Si ya tiene una amarilla, se convierte en roja y el jugador es expulsado.
    public boolean agregarTarjetaAmarilla() {
        this.tarjetasAmarillas++;

        if (this.tarjetasAmarillas >= 2) {
            this.tarjetasRojas++;
            this.expulsado = true;
            return true;
        }

        return false;
    }

    // Agrega una tarjeta roja directa al jugador y lo expulsa del partido.
    public void agregarTarjetaRoja() {
        this.tarjetasRojas++;
        this.expulsado = true;
    }

    // Incrementa el contador de goles del jugador en 1
    public void marcarGol() {
        this.goles++;
    }

    // ===== MÉTODOS DE ESTADÍSTICAS =====

    //Asigna valores aleatorios (1-100) a todas las estadísticas del jugador
    public void setRandomStats() {
        setVelocidad(1 + (int)(Math.random() * 100));
        setTiro(1 + (int)(Math.random() * 100));
        setPase(1 + (int)(Math.random() * 100));
        setDefensa(1 + (int)(Math.random() * 100));
        setFisico(1 + (int)(Math.random() * 100));
    }


    @Override
    public String toString() {
        return nombre + " (#" + dorsal + " - " +
                (posicion != null ? posicion : "N/A") + ")";
    }
}