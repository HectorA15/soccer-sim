package org.example.core;

import org.example.entidades.Portero;
import org.example.entidades.Jugador;
import java.util.Random;

/**
 * Gestiona todos los eventos que pueden ocurrir durante un partido de fútbol.
 * Incluye: goles (tiros, penales, tiros libres, esquinas), tarjetas, lesiones y eventos de juego.
 * Cada metdo calcula probabilidades basadas en las estadísticas de los jugadores involucrados.
 */
public class Eventos {

    Random random = new Random();

    /**
     * Constructor por defecto de la clase Eventos.
     */
    public Eventos(){
    }

    // ===== EVENTOS DE GOL =====

    /**
     * Simula un tiro a puerta.
     * La probabilidad de gol depende del tiro del atacante vs los reflejos del portero.
     * Si es gol, incrementa automáticamente el contador de goles del atacante.
     * @param atacante Jugador que ejecuta el tiro
     * @param portero Portero que intenta detener el tiro
     * @return true si fue gol, false si el portero atajó
     */
    public boolean tiroPuerta(Jugador atacante, Portero portero) {
        double probabilidad = (double) atacante.getTiro() /
                (atacante.getTiro() + portero.getReflejos()) * 100;
        boolean esGol = random.nextDouble() * 100 < probabilidad;
        if (esGol) {
            atacante.marcarGol();
        }
        return esGol;
    }

    /**
     * Simula un penal con tiro ajustado al 150%.
     * La capacidad de tiro se multiplica por 1.5 para reflejar la ventaja del tirador.
     * Si es gol, incrementa automáticamente el contador de goles del tirador.
     * @param tirador Jugador que ejecuta el penal
     * @param portero Portero que intenta detener el penal
     * @return true si fue gol, false si el portero atajó
     */
    public boolean penal(Jugador tirador, Portero portero) {
        double tiroAjustado = tirador.getTiro() * 1.5;
        double probabilidad = tiroAjustado /
                (tiroAjustado + portero.getReflejos()) * 100;

        boolean esGol = random.nextDouble() * 100 < probabilidad;
        if (esGol) {
            tirador.marcarGol();
        }
        return esGol;
    }

    /**
     * Simula un tiro libre.
     * Si el jugador tiene más de 75 de tiro, dispara directamente.
     * Si no, primero intenta un centro basado en su capacidad de pase.
     * Si es gol, incrementa automáticamente el contador de goles del ejecutor.
     * @param ejecutor Jugador que ejecuta el tiro libre
     * @param portero Portero del equipo rival
     * @return true si fue gol, false si no
     */
    public boolean tiroLibre(Jugador ejecutor, Portero portero) {
        if (ejecutor.getTiro() > 75) {
            return tiroPuerta(ejecutor, portero);
        }

        int probCentro = ejecutor.getPase();
        boolean centroBueno = random.nextInt(100) < probCentro;

        if (!centroBueno) return false;

        return tiroPuerta(ejecutor, portero);
    }

    /**
     * Simula un tiro de esquina.
     * Primero se determina si el atacante gana el duelo físico con el defensor.
     * Si gana el duelo, se ejecuta un tiro a puerta.
     * Si es gol, incrementa automáticamente el contador de goles del atacante.
     * @param atacante Jugador que remata el balón
     * @param defensor Jugador que defiende
     * @param portero Portero del equipo rival
     * @return true si fue gol, false si no
     */
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

    /**
     * Simula un saque de banda.
     * La probabilidad de éxito depende del pase del ejecutor vs la defensa+velocidad del rival.
     * @param ejecutor Jugador que saca de banda
     * @param defensor Jugador que intenta interceptar
     * @return true si el saque fue exitoso, false si el defensor lo interceptó
     */
    public boolean saqueBanda(Jugador ejecutor, Jugador defensor) {
        int ataque = ejecutor.getPase();
        int defensa = defensor.getDefensa() + defensor.getVelocidad();

        double probabilidad = (double) ataque / (ataque + defensa) * 100;

        return random.nextDouble() * 100 < probabilidad;
    }

    /**
     * Determina si hay fuera de juego.
     * La probabilidad base es 10% y se ajusta según la diferencia de velocidad entre atacante y defensor.
     * @param atacante Jugador en posible fuera de juego
     * @param defensor Último defensor
     * @return true si hay fuera de juego, false si no
     */
    public boolean fueraDeJuego(Jugador atacante, Jugador defensor) {
        int diferencia = atacante.getVelocidad() - defensor.getDefensa();
        int probabilidad = 10 + diferencia / 5;

        if (probabilidad < 5) probabilidad = 5;
        if (probabilidad > 40) probabilidad = 40;

        return random.nextInt(100) < probabilidad;
    }

    // ===== TARJETAS =====

    /**
     * Determina si un jugador recibe tarjeta amarilla.
     * Jugadores con menos capacidad defensiva tienen más probabilidad de ser amonestados.
     * Si el jugador ya tiene una amarilla, recibe roja y es expulsado automáticamente.
     * @param jugador Jugador que puede recibir tarjeta
     * @return "EXPULSION" si fue la segunda amarilla, "AMARILLA" si es la primera, null si no hubo tarjeta
     */
    public String tarjetaAmarilla(Jugador jugador) {
        double probabilidad = (100 - jugador.getDefensa()) / 8.0;

        boolean hayTarjeta = random.nextDouble() * 100 < probabilidad;

        if (hayTarjeta) {
            boolean expulsado = jugador.agregarTarjetaAmarilla();
            return expulsado ? "EXPULSION" : "AMARILLA";
        }

        return null;
    }

    /**
     * Muestra tarjeta roja directa al jugador y lo expulsa del partido.
     * @param jugador Jugador que recibe la tarjeta roja
     * @return siempre true (siempre hay expulsión con roja directa)
     */
    public boolean tarjetaRoja(Jugador jugador) {
        jugador.agregarTarjetaRoja();
        return true;
    }

    // ===== LESIONES =====

    /**
     * Determina si un jugador sufre una lesión.
     * Jugadores con menos resistencia física tienen mayor probabilidad de lesionarse.
     * @param jugador Jugador que puede lesionarse
     * @return true si se lesiona, false si no
     */
    public boolean lesion(Jugador jugador) {
        double probabilidad = (100 - jugador.getFisico()) / 10.0;

        return random.nextDouble() * 100 < probabilidad;
    }
}