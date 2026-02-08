package org.example.enums;

/**
 * Define las posiciones posibles de un jugador en el campo de fútbol.
 * <p>
 * Las posiciones se organizan de atrás hacia adelante:
 * <ul>
 *   <li>{@link #PORTERIA}: arquero/guardameta</li>
 *   <li>{@link #DEFENSA}: defensores/zagueros</li>
 *   <li>{@link #MEDIOCAMP}: mediocampistas/volantes</li>
 *   <li>{@link #DELANTERO}: atacantes/delanteros</li>
 * </ul>
 * <p>
 * Usado por {@link org.example.entidades.Jugador} para indicar
 * la posición del jugador en la formación.
 *
 * @author HectorA15
 * @version 0.2
 * @see org.example.entidades.Jugador
 * @see org.example.entidades.Formacion
 */
public enum Posicion {

    /**
     * Posición de portero/arquero/guardameta.
     * <p>
     * Es el único jugador que puede usar las manos dentro del área.
     * Exclusivo de la clase {@link org.example.entidades.Portero}.
     */
    PORTERIA,

    /**
     * Posición de defensa/zaguero.
     * <p>
     * Jugadores que forman la línea defensiva, cuya función principal
     * es evitar que el rival anote. Incluye defensas centrales y laterales.
     */
    DEFENSA,

    /**
     * Posición de mediocampista/volante.
     * <p>
     * Jugadores que conectan defensa y ataque, controlan el mediocampo
     * y distribuyen el balón. Incluye medios defensivos, centrales y ofensivos.
     */
    MEDIOCAMP,

    /**
     * Posición de delantero/atacante.
     * <p>
     * Jugadores cuya función principal es marcar goles. Incluye
     * delanteros centros y extremos.
     */
    DELANTERO
}