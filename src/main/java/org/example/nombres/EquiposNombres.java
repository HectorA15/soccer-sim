package org.example.nombres;

import java.util.Arrays;

public final class EquiposNombres {

    private EquiposNombres() {

    }

    private static final String[] EQUIPOS = {
            "Real Madrid", "FC Barcelona", "Manchester City", "Liverpool", "Bayern Munich",
            "Paris Saint-Germain", "Juventus", "AC Milan", "Inter de Milán", "Arsenal",
            "Chelsea", "Manchester United", "Atlético de Madrid", "Borussia Dortmund",
            "Club América", "C.F. Monterrey", "Tigres UANL", "Chivas Guadalajara", "Cruz Azul", "Pumas UNAM"
    };

    public static String[] getEquipos() {
        return Arrays.copyOf(EQUIPOS, EQUIPOS.length);
    }



}
