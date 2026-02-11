package org.example.nombres;

import java.util.Arrays;

public final class JugadoresNombres {


    public JugadoresNombres(){

    }

    private static final String[] JUGADORES = {
            "Lionel Messi", "Cristiano Ronaldo", "Kylian Mbappé", "Erling Haaland", "Jude Bellingham",
            "Vinícius Jr.", "Kevin De Bruyne", "Harry Kane", "Mohamed Salah", "Robert Lewandowski",
            "Rodri", "Luka Modrić", "Neymar Jr.", "Virgil van Dijk", "Manuel Neuer",
            "Antoine Griezmann", "Toni Kroos", "Son Heung-min", "Lautaro Martínez", "Lamine Yamal",
            "Pelé", "Diego Maradona", "Johan Cruyff", "Zinedine Zidane", "Ronaldo Nazário",
            "Ronaldinho", "Paolo Maldini", "Franz Beckenbauer", "Andrés Iniesta", "Gianluigi Buffon"
    };

    public static String[] getJugadores() {
        return Arrays.copyOf(JUGADORES, JUGADORES.length);
    }



}
