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

    /**
     * Constructor básico con solo el nombre.
     * Las estadísticas deben asignarse posteriormente con setRandomStats() o los setters.
     * @param nombre Nombre del jugador
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.posicion = null;
        this.goles = 0;
        this.expulsado = false;
    }


    // ===== GETTERS =====

    /**
     * Obtiene el nombre del jugador.
     * @return Nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la posición del jugador en el campo.
     * @return Posición del jugador
     */
    public Posicion getPosicion() {
        return posicion;
    }

    /**
     * Obtiene la velocidad del jugador.
     * @return Velocidad (1-100)
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Obtiene la capacidad de tiro del jugador.
     * @return Tiro (1-100)
     */
    public int getTiro() {
        return tiro;
    }

    /**
     * Obtiene la capacidad de pase del jugador.
     * @return Pase (1-100)
     */
    public int getPase() {
        return pase;
    }

    /**
     * Obtiene la capacidad defensiva del jugador.
     * @return Defensa (1-100)
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * Obtiene la resistencia física del jugador.
     * @return Físico (1-100)
     */
    public int getFisico() {
        return fisico;
    }

    /**
     * Obtiene el número de dorsal del jugador.
     * @return Número de dorsal
     */
    public int getDorsal() {
        return dorsal;
    }

    /**
     * Obtiene el número de lesiones del jugador.
     * @return Cantidad de lesiones
     */
    public int getLesiones() {
        return lesiones;
    }

    /**
     * Obtiene el número de tarjetas amarillas recibidas.
     * @return Cantidad de tarjetas amarillas
     */
    public int getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }

    /**
     * Obtiene el número de tarjetas rojas recibidas.
     * @return Cantidad de tarjetas rojas
     */
    public int getTarjetasRojas() {
        return tarjetasRojas;
    }

    /**
     * Obtiene el número de goles marcados por el jugador.
     * @return Cantidad de goles
     */
    public int getGoles() {
        return goles;
    }

    /**
     * Verifica si el jugador está expulsado.
     * @return true si está expulsado, false en caso contrario
     */
    public boolean isExpulsado() {
        return expulsado;
    }

    // ===== SETTERS =====

    /**
     * Asigna la velocidad del jugador.
     * @param velocidad Velocidad (1-100)
     */
    protected void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Asigna la capacidad de tiro del jugador.
     * @param tiro Tiro (1-100)
     */
    protected void setTiro(int tiro) {
        this.tiro = tiro;
    }

    /**
     * Asigna la capacidad de pase del jugador.
     * @param pase Pase (1-100)
     */
    protected void setPase(int pase) {
        this.pase = pase;
    }

    /**
     * Asigna la capacidad defensiva del jugador.
     * @param defensa Defensa (1-100)
     */
    protected void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    /**
     * Asigna la resistencia física del jugador.
     * @param fisico Físico (1-100)
     */
    protected void setFisico(int fisico) {
        this.fisico = fisico;
    }

    /**
     * Asigna la posición del jugador en el campo.
     * @param posicion Nueva posición
     */
    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    /**
     * Asigna el número de dorsal del jugador.
     * @param dorsal Número de dorsal
     */
    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    /**
     * Asigna el número de lesiones del jugador.
     * @param lesiones Cantidad de lesiones
     */
    public void setLesiones(int lesiones) {
        this.lesiones = lesiones;
    }

    /**
     * Asigna el número de tarjetas amarillas.
     * @param tarjetasAmarillas Cantidad de tarjetas amarillas
     */
    public void setTarjetasAmarillas(int tarjetasAmarillas) {
        this.tarjetasAmarillas = tarjetasAmarillas;
    }

    /**
     * Asigna el número de tarjetas rojas.
     * @param tarjetasRojas Cantidad de tarjetas rojas
     */
    public void setTarjetasRojas(int tarjetasRojas) {
        this.tarjetasRojas = tarjetasRojas;
    }

    /**
     * Asigna el número de goles del jugador.
     * @param goles Cantidad de goles
     */
    public void setGoles(int goles) {
        this.goles = goles;
    }

    /**
     * Asigna el estado de expulsión del jugador.
     * @param expulsado true si está expulsado, false si no
     */
    public void setExpulsado(boolean expulsado) {
        this.expulsado = expulsado;
    }

    // ===== MÉTODOS DE TARJETAS =====

    /**
     * Agrega una tarjeta amarilla al jugador.
     * Si ya tiene una amarilla, se convierte en roja y el jugador es expulsado.
     * @return true si fue expulsado por segunda amarilla, false si solo recibió amarilla
     */
    public boolean agregarTarjetaAmarilla() {
        this.tarjetasAmarillas++;

        if (this.tarjetasAmarillas >= 2) {
            this.tarjetasRojas++;
            this.expulsado = true;
            return true;
        }

        return false;
    }

    /**
     * Agrega una tarjeta roja directa al jugador y lo expulsa del partido.
     */
    public void agregarTarjetaRoja() {
        this.tarjetasRojas++;
        this.expulsado = true;
    }

    /**
     * Incrementa el contador de goles del jugador en 1.
     */
    public void marcarGol() {
        this.goles++;
    }

    // ===== MÉTODOS DE ESTADÍSTICAS =====

    /**
     * Asigna valores aleatorios (1-100) a todas las estadísticas del jugador.
     */
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