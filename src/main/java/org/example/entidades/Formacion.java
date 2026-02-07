package org.example.entidades;

/**
 * Representa la formacion de un equipo de futbol.
 * <p>
 * Define la distribución de jugadores en el campo (sin contar al portero):
 * <ul>
 *   <li><b>Defensas</b>: línea defensiva</li>
 *   <li><b>Mediocampistas</b>: línea media</li>
 *   <li><b>Delanteros</b>: línea de ataque</li>
 * </ul>
 * <p>
 * <b>Importante</b>: La suma de las tres posiciones debe ser 10 (portero no incluido).
 * Actualmente no se valida esta restricción.
 * <p>
 * @author HectorA15
 * @version 0.1
 */


public class Formacion {

    /** El nombre de la Formacion */
    String nombreFormacion;

    /** Capacidad de jugadores para la posicion de Defensa */
    int defensas;

    /** Capacidad de jugadores para la posicion de Mediocampista */
    int mediocampistas;

    /** Capacidad de jugadores para la posicion de Delantero */
    int delanteros;

    /**
     * Crea una Formacion que establece la  cantidad de jugadores que pueden haber en una posicion
     *
     * <b>Advertencia</b>: No valida que la suma sea exactamente 10.
     * Es responsabilidad del llamador que la suma entre los jugadores sea 10.
     *
     * @param defensas
     * @param mediocampistas
     * @param delanteros
     * @throws IllegalArgumentException si algún valor es negativo (no implementado)
     */
    public Formacion(int defensas, int mediocampistas, int delanteros){
        this.defensas = defensas;
        this.mediocampistas = mediocampistas;
        this.delanteros = delanteros;
        this.nombreFormacion = defensas + "-" + mediocampistas + "-" + delanteros;
    }


    /**
     * Obtiene el nombre de la formación.
     *
     * @return nombre en formato "D-M-A" (ej: "4-3-3")
     */
    public String getNombreFormacion() {
        return nombreFormacion;
    }

    /**
     * Obtiene la cantidad de defensas.
     *
     * @return número de defensores en la formación
     */
    public int getDefensas() {
        return defensas;
    }

    /**
     * Obtiene la cantidad de mediocampistas.
     *
     * @return número de mediocampistas en la formación
     */
    public int getMediocampistas() {
        return mediocampistas;
    }

    /**
     * Obtiene la cantidad de delanteros.
     *
     * @return número de delanteros en la formación
     */
    public int getDelanteros() {
        return delanteros;
    }

    /**
     * Verifica si la formación es válida (suma = 10 jugadores) no toma en cuenta el portero.
     *
     * @return {@code true} si la suma es 10, {@code false} en caso contrario
     */
    public boolean isValida() {
        return (defensas + mediocampistas + delanteros) == 10;
    }

    /**
     * Genera representación en texto de la formación.
     *
     * @return nombre de la formación
     */
    @Override
    public String toString() {
        return nombreFormacion;
    }
}