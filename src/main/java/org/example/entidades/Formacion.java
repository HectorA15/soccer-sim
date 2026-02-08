package org.example.entidades;

/**
 * Representa la formación táctica de un equipo.
 * Define cuántos defensas, mediocampistas y delanteros hay.
 *
 * REGLA IMPORTANTE: La suma debe ser EXACTAMENTE 10 (el portero no cuenta).
 *
 * Ejemplos válidos: 4-4-2, 4-3-3, 3-5-2, 5-3-2
 * Ejemplo inválido: 4-4-3 (suma 11, no 10)
 */
public class Formacion {

    private String nombreFormacion;
    private int defensas;
    private int mediocampistas;
    private int delanteros;

    /**
     * Crea una formación.
     * El nombre se genera automáticamente (ej: defensas=4, medios=4, delanteros=2 → "4-4-2")
     *
     * NOTA: Este constructor NO valida si es válida. Debes llamar a isValida()
     * o usar setFormacion() de Equipos que sí valida.
     */
    public Formacion(int defensas, int mediocampistas, int delanteros) {
        this.defensas = defensas;
        this.mediocampistas = mediocampistas;
        this.delanteros = delanteros;
        this.nombreFormacion = defensas + "-" + mediocampistas + "-" + delanteros;
    }

    public String getNombreFormacion() {
        return nombreFormacion;
    }

    public int getDefensas() {
        return defensas;
    }

    public int getMediocampistas() {
        return mediocampistas;
    }

    public int getDelanteros() {
        return delanteros;
    }

    /**
     * Verifica que la suma sea exactamente 10 jugadores.
     * Usa esto antes de asignar la formación a un equipo.
     */
    public boolean isValida() {
        return (defensas + mediocampistas + delanteros) == 10;
    }

    @Override
    public String toString() {
        return nombreFormacion;
    }
}