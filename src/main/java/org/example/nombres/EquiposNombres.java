package org.example.nombres;

import java.util.Arrays;

/**
 * Clase utilitaria que proporciona nombres de equipos de fútbol famosos.
 * Incluye equipos de ligas europeas y de la Liga MX.
 * Utilizada para seleccionar nombres de equipos en las simulaciones.
 */
public final class EquiposNombres {

    /**
     * Constructor privado para evitar instanciación de esta clase utilitaria.
     */
    private EquiposNombres() {
    }

    private static final String[] EQUIPOS = {
            "Real Madrid", "FC Barcelona", "Manchester City", "Liverpool", "Bayern Munich",
            "Paris Saint-Germain", "Juventus", "AC Milan", "Inter de Milan", "Arsenal",
            "Chelsea", "Manchester United", "Atletico de Madrid", "Borussia Dortmund",
            "Club America", "C.F. Monterrey", "Tigres UANL", "Chivas Guadalajara", "Cruz Azul", "Pumas UNAM"
    };

    /**
     * Obtiene una copia del array completo de nombres de equipos.
     * Retorna una copia para evitar modificaciones del array original.
     * @return Array con todos los nombres de equipos disponibles
     */
    public static String[] getEquipos() {
        return Arrays.copyOf(EQUIPOS, EQUIPOS.length);
    }
}