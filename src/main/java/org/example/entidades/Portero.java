package org.example.entidades;

import org.example.enums.Posicion;

/**
 * Representa un portero con estadísticas adicionales: saque y reflejos.
 * La posición se asigna automáticamente como PORTERIA.
 */
public class Portero extends Jugador {

    private int saque;
    private int reflejos;

    public Portero(String nombre) {
        super(nombre, Posicion.PORTERIA);
    }

    public int getSaque() {
        return saque;
    }

    public int getReflejos() {
        return reflejos;
    }

    /**
     * Asigna valores aleatorios a TODAS las stats (las 5 base + saque + reflejos).
     * Llama primero a super.setRandomStats() para las stats base,
     * luego asigna las específicas del portero.
     */
    @Override
    public void setRandomStats() {
        super.setRandomStats();  // Esto asigna velocidad, tiro, pase, defensa, físico
        saque    = 1 + (int)(Math.random() * 100);
        reflejos = 1 + (int)(Math.random() * 100);
    }

    /**
     * toString personalizado para mostrar las 7 stats (5 base + saque + reflejos).
     * Nota: El formato es diferente a Jugador porque tiene 2 columnas extra.
     */
    @Override
    public String toString() {
        String posStr = getPosicion().toString();
        return String.format("%-25s %5d %5d %5d %5d %5d %5d %5d %10s",
                getNombre(), getVelocidad(), getTiro(), getPase(),
                getDefensa(), getFisico(), saque, reflejos, posStr);
    }
}