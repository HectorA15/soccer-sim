package org.example.entidades;

/**
 * Representa la formación táctica de un equipo de fútbol.
 * Define la distribución de jugadores en el campo: defensas, mediocampistas y delanteros.
 *
 * REGLA IMPORTANTE: La suma debe ser EXACTAMENTE 10 jugadores (el portero no cuenta).
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
     * Constructor de la formación.
     * El nombre se genera automáticamente en formato "D-M-A"
     * (ej: defensas=4, medios=4, delanteros=2 da "4-4-2").
     *
     * NOTA: Este constructor NO valida si la formación es válida.
     * Debes llamar a isValida() para verificar que suma 10 jugadores.
     * @param defensas Número de defensas (0-10)
     * @param mediocampistas Número de mediocampistas (0-10)
     * @param delanteros Número de delanteros (0-10)
     */
    public Formacion(int defensas, int mediocampistas, int delanteros) {
        this.defensas = defensas;
        this.mediocampistas = mediocampistas;
        this.delanteros = delanteros;
        this.nombreFormacion = defensas + "-" + mediocampistas + "-" + delanteros;
    }

    /**
     * Obtiene el número de defensas de la formación.
     * @return Cantidad de defensas
     */
    public int getDefensas() {
        return defensas;
    }

    /**
     * Obtiene el número de mediocampistas de la formación.
     * @return Cantidad de mediocampistas
     */
    public int getMediocampistas() {
        return mediocampistas;
    }

    /**
     * Obtiene el número de delanteros de la formación.
     * @return Cantidad de delanteros
     */
    public int getDelanteros() {
        return delanteros;
    }

    /**
     * Verifica que la formación sea válida.
     * Una formación es válida si la suma de defensas, mediocampistas y delanteros es exactamente 10.
     * @return true si la suma es 10, false en caso contrario
     */
    public boolean isValida() {
        return (defensas + mediocampistas + delanteros) == 10;
    }

    @Override
    public String toString() {
        return nombreFormacion;
    }
}