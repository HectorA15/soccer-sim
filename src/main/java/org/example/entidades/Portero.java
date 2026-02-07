package org.example.entidades;

/**
 * Representa un portero con estadísticas específicas adicionales.
 * <p>
 * Extiende {@link Jugador} agregando dos atributos exclusivos:
 * <ul>
 *   <li><b>Saque</b>: calidad de despejes y saques de meta</li>
 *   <li><b>Reflejos</b>: capacidad de reacción ante disparos</li>
 * </ul>
 *
 * @author HectorA15
 * @version 0.1
 * @see Jugador
 */
public class Portero extends Jugador {

    /** Habilidad de saque (1-100). Afecta despejes y distribución. */
    private int saque;

    /** Reflejos del portero (1-100). Determina probabilidad de atajadas. */
    private int reflejos;

    /**
     * Crea un portero con nombre especificado y estadísticas en 0.
     * <p>
     * Usar {@link #setRandomStats()} para inicializar valores.
     *
     */
    public Portero(String nombre) {
        super(nombre);
    }

    /**
     * Obtiene el valor de saque del portero.
     *
     * @return valor de saque (1-100)
     */
    public int getSaque() {
        return saque;
    }

    /**
     * Obtiene el valor de reflejos del portero.
     *
     * @return valor de reflejos (1-100)
     */
    public int getReflejos() {
        return reflejos;
    }

    /**
     * Asigna valores aleatorios a las estadísticas del portero.
     * <p>
     * Invoca {@link Jugador#setRandomStats()} para las stats base,
     * luego asigna valores a saque y reflejos.
     */
    @Override
    public void setRandomStats() {
        super.setRandomStats();
        saque    = 1 + (int)(Math.random() * 100);
        reflejos = 1 + (int)(Math.random() * 100);
    }

    /**
     * Genera representación formateada incluyendo stats de portero.
     * <p>
     * Formato: [stats de Jugador] + saque + reflejos (5 chars cada uno).
     *
     * @return string con todas las estadísticas
     */
    @Override
    public String toString() {
        return String.format("%s %5d %5d", super.toString(), saque, reflejos);
    }
}