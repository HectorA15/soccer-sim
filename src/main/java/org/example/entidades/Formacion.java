package org.example.entidades;

/**
 * Representa la formación táctica de un equipo de fútbol.
 * <p>
 * Define la distribución de jugadores en el campo (sin contar al portero):
 * <ul>
 *   <li><b>Defensas</b>: línea defensiva</li>
 *   <li><b>Mediocampistas</b>: línea media</li>
 *   <li><b>Delanteros</b>: línea de ataque</li>
 * </ul>
 * <p>
 * <b>Importante</b>: La suma de las tres posiciones debe ser 10 (portero no incluido).
 * El método {@link #isValida()} verifica esta restricción.
 * <p>
 * Ejemplos de formaciones comunes:
 * <ul>
 *   <li>4-4-2: cuatro defensas, cuatro mediocampistas, dos delanteros</li>
 *   <li>4-3-3: cuatro defensas, tres mediocampistas, tres delanteros</li>
 *   <li>3-5-2: tres defensas, cinco mediocampistas, dos delanteros</li>
 * </ul>
 *
 * @author HectorA15
 * @version 0.2
 */
public class Formacion {

    /** Nombre de la formación en formato "D-M-A" (ej: "4-4-2"). */
    private String nombreFormacion;

    /** Cantidad de jugadores en posición de defensa. */
    private int defensas;

    /** Cantidad de jugadores en posición de mediocampo. */
    private int mediocampistas;

    /** Cantidad de jugadores en posición de ataque. */
    private int delanteros;

    /**
     * Crea una formación táctica con la distribución especificada.
     * <p>
     * El nombre se genera automáticamente en formato "D-M-A".
     * <p>
     * <b>Advertencia</b>: No valida automáticamente que la suma sea 10.
     * Es responsabilidad del llamador verificar con {@link #isValida()}.
     *
     * @param defensas cantidad de defensores (típicamente 3-5)
     * @param mediocampistas cantidad de mediocampistas (típicamente 3-5)
     * @param delanteros cantidad de delanteros (típicamente 1-3)
     * @throws IllegalArgumentException si algún valor es negativo (no implementado actualmente)
     */
    public Formacion(int defensas, int mediocampistas, int delanteros) {
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
     * Verifica si la formación es válida (suma = 10 jugadores).
     * <p>
     * No toma en cuenta el portero, solo jugadores de campo.
     *
     * @return {@code true} si la suma es 10, {@code false} en caso contrario
     */
    public boolean isValida() {
        return (defensas + mediocampistas + delanteros) == 10;
    }

    /**
     * Genera representación en texto de la formación.
     *
     * @return nombre de la formación en formato "D-M-A"
     */
    @Override
    public String toString() {
        return nombreFormacion;
    }
}