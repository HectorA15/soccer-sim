package org.example.nombres;

import java.util.Arrays;
import java.util.Random;

/**
 * Clase utilitaria que proporciona nombres de jugadores de fútbol.
 * Utilizada para asignar nombres aleatorios a los jugadores en las simulaciones.
 */
public final class JugadoresNombres {

    private static final String[] JUGADORES = {
            "Lionel Messi", "Cristiano Ronaldo", "Kylian Mbappe", "Erling Haaland", "Jude Bellingham",
            "Vinicius Jr.", "Kevin De Bruyne", "Harry Kane", "Mohamed Salah", "Robert Lewandowski",
            "Rodri", "Luka Modric", "Neymar Jr.", "Virgil van Dijk", "Manuel Neuer",
            "Antoine Griezmann", "Toni Kroos", "Son Heung-min", "Lautaro Martinez", "Lamine Yamal",
            "Pele", "Diego Maradona", "Johan Cruyff", "Zinedine Zidane", "Ronaldo Nazario",
            "Ronaldinho", "Paolo Maldini", "Franz Beckenbauer", "Andres Iniesta", "Gianluigi Buffon",
            "Sergio Ramos", "Gerard Pique", "Xavi Hernandez", "Karim Benzema", "Luis Suarez",
            "Eden Hazard", "Sergio Aguero", "Gareth Bale", "Paul Pogba", "NGolo Kante",
            "Sadio Mane", "Raheem Sterling", "Phil Foden", "Jadon Sancho", "Bruno Fernandes",
            "Casemiro", "Fabinho", "Thibaut Courtois", "Alisson Becker", "Marc-Andre ter Stegen",
            "Ederson", "Jan Oblak", "Hugo Lloris", "Angel Di Maria", "Marco Reus",
            "Thomas Muller", "Joshua Kimmich", "Bernardo Silva", "Joao Felix", "Rafael Leao"
    };

    /**
     * Obtiene una copia del array completo de nombres de jugadores.
     * Retorna una copia para evitar modificaciones del array original.
     * @return Array con todos los nombres de jugadores disponibles
     */
    public static String[] getJugadores() {
        return Arrays.copyOf(JUGADORES, JUGADORES.length);
    }


}