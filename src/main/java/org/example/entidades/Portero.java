package org.example.entidades;

import org.example.enums.Posicion;

/**
 * Representa un portero con estadísticas adicionales específicas de su posición.
 * Además de las estadísticas base de un jugador, incluye saque y reflejos.
 * La posición se asigna automáticamente como PORTERIA.
 */
public class Portero extends Jugador {

    private int saque;
    private int reflejos;

    /**
     * Constructor del portero.
     * La posición se asigna automáticamente como PORTERIA.
     * @param nombre Nombre del portero
     */
    public Portero(String nombre) {
        super(nombre);
        setPosicion(Posicion.PORTERIA);
    }

    /**
     * Obtiene la capacidad de saque del portero.
     * @return Saque (1-100)
     */
    public int getSaque() {
        return saque;
    }

    /**
     * Obtiene la capacidad de reflejos del portero.
     * @return Reflejos (1-100)
     */
    public int getReflejos() {
        return reflejos;
    }

    /**
     * Asigna valores aleatorios (1-100) a todas las estadísticas del portero.
     * Incluye las 5 estadísticas base más saque y reflejos.
     */
    @Override
    public void setRandomStats() {
        super.setRandomStats();
        saque    = 1 + (int)(Math.random() * 100);
        reflejos = 1 + (int)(Math.random() * 100);
    }

    /**
     * Genera una cadena formateada con todas las estadísticas del portero.
     * @return String con nombre, 7 estadísticas y posición
     */
    @Override
    public String toString() {
        String posStr = getPosicion().toString();
        return String.format("%-25s %5d %5d %5d %5d %5d %5d %5d %10s",
                getNombre(), getVelocidad(), getTiro(), getPase(),
                getDefensa(), getFisico(), saque, reflejos, posStr);
    }
}