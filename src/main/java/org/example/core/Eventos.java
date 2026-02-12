package org.example.core;

import org.example.entidades.Portero;
import org.example.entidades.Jugador;
import java.util.Random;

public class Eventos {

    Random random = new Random();

    public Eventos(){
    }

    // ===== EVENTOS DE GOL =====
    public boolean tiroPuerta(Jugador atacante, Portero portero) {
        double probabilidad = (double) atacante.getTiro() /
                (atacante.getTiro() + portero.getReflejos()) * 100;

        return random.nextDouble() * 100 < probabilidad;
    }

    // Penal con tiro ajustado.
    public boolean penal(Jugador tirador, Portero portero) {
        double tiroAjustado = tirador.getTiro() * 1.5;

        double probabilidad = tiroAjustado /
                (tiroAjustado + portero.getReflejos()) * 100;

        return random.nextDouble() * 100 < probabilidad;
    }

    public boolean tiroLibre(Jugador ejecutor, Portero portero) {
        if (ejecutor.getTiro() > 75) {
            return tiroPuerta(ejecutor, portero);
        }

        int probCentro = ejecutor.getPase();
        boolean centroBueno = random.nextInt(100) < probCentro;

        if (!centroBueno) return false;

        return tiroPuerta(ejecutor, portero);
    }

    public boolean tiroEsquina(Jugador atacante, Jugador defensor, Portero portero) {
        int fuerzaAtacante = atacante.getFisico();
        int fuerzaDefensor = defensor.getFisico() + defensor.getDefensa();

        double probabilidad = (double) fuerzaAtacante /
                (fuerzaAtacante + fuerzaDefensor) * 100;

        boolean ganaAtacante = random.nextDouble() * 100 < probabilidad;

        if (!ganaAtacante) return false;

        return tiroPuerta(atacante, portero);
    }

    // ===== EVENTOS DE JUEGO =====
    public boolean saqueBanda(Jugador ejecutor, Jugador defensor) {
        int ataque = ejecutor.getPase();
        int defensa = defensor.getDefensa() + defensor.getVelocidad();

        double probabilidad = (double) ataque / (ataque + defensa) * 100;

        return random.nextDouble() * 100 < probabilidad;
    }

    public boolean fueraDeJuego(Jugador atacante, Jugador defensor) {
        int diferencia = atacante.getVelocidad() - defensor.getDefensa();
        int probabilidad = 10 + diferencia / 5;

        if (probabilidad < 5) probabilidad = 5;
        if (probabilidad > 40) probabilidad = 40;

        return random.nextInt(100) < probabilidad;
    }

    // ===== TARJETAS =====
    public boolean tarjetaAmarilla(Jugador jugador) {
        double probabilidad = (100 - jugador.getDefensa()) / 8.0;

        return random.nextDouble() * 100 < probabilidad;
    }

    public boolean tarjetaRoja(Jugador jugador) {
        jugador.setTarjetasRojas(jugador.getTarjetasRojas() + 1);
        return true;
    }

    // ===== LESIONES =====
    public boolean lesion(Jugador jugador) {
        double probabilidad = (100 - jugador.getFisico()) / 10.0;

        return random.nextDouble() * 100 < probabilidad;
    }

}