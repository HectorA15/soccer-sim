package org.example;

import org.example.entidades.Portero;
import org.example.entidades.Jugador;
import java.util.Random;
/**
 * Gestiona el RESULTADO de los eventos del partido.
 *
 * Esta clase NO decide CUÁNDO ocurren los eventos (eso es responsabilidad de Partido.java).
 * Solo decide si tienen éxito cuando ya ocurrieron.
 *
 * FÓRMULA BÁSICA:
 * probabilidad = (statAtacante / (statAtacante + statDefensor)) * 100
 * Si random(0-100) < probabilidad → ÉXITO
 */
public class Eventos {

    Random random = new Random();

    public Eventos(){
    }

    // ========== EVENTOS DE GOL ==========

    /**
     * Simula un tiro normal a portería.
     *
     * Lógica:
     * Compara la habilidad de tiro del atacante contra
     * los reflejos del portero.
     *
     * Mientras mayor sea el tiro del jugador,
     * mayor probabilidad de gol.
     */
    public boolean tiroPuerta(Jugador atacante, Portero portero) {

        // Fórmula de probabilidad basada en stats
        double probabilidad = (double) atacante.getTiro() /
                (atacante.getTiro() + portero.getReflejos()) * 100;

        // Se genera un número aleatorio de 0 a 100 y se compara con la probabilidad
        return random.nextDouble() * 100 < probabilidad;
    }

    /**
     * Simula un penal.
     *
     * Los penales en el fútbol real tienen mayor probabilidad de gol,
     * por eso aumentamos el tiro del jugador.
     */
    public boolean penal(Jugador tirador, Portero portero) {

        // Se aumenta el tiro para hacer el penal más fácil
        double tiroAjustado = tirador.getTiro() * 1.5;

        double probabilidad = tiroAjustado /
                (tiroAjustado + portero.getReflejos()) * 100;

        return random.nextDouble() * 100 < probabilidad;
    }

    /**
     * Simula un tiro libre.
     *
     * Si el jugador tiene muy buen tiro,
     * dispara directo a portería.
     *
     * Si no, intenta mandar un centro.
     */
    public boolean tiroLibre(Jugador ejecutor, Portero portero) {

        // Jugadores con alto tiro disparan directo
        if (ejecutor.getTiro() > 75) {
            return tiroPuerta(ejecutor, portero);
        }

        // Si no dispara, intenta centro al área
        int probCentro = ejecutor.getPase();

        boolean centroBueno = random.nextInt(100) < probCentro;

        // Si el centro falla, no hay gol
        if (!centroBueno) return false;

        // Si el centro fue bueno, se intenta remate
        return tiroPuerta(ejecutor, portero);
    }

    /**
     * Simula un tiro de esquina.
     *
     * Tiene 2 fases:
     * 1. Disputa aérea (físico atacante vs defensa + físico defensor)
     * 2. Si gana el atacante, intenta rematar a gol
     */
    public boolean tiroEsquina(Jugador atacante, Jugador defensor, Portero portero) {

        int fuerzaAtacante = atacante.getFisico();
        int fuerzaDefensor = defensor.getFisico() + defensor.getDefensa();

        double probabilidad = (double) fuerzaAtacante /
                (fuerzaAtacante + fuerzaDefensor) * 100;

        boolean ganaAtacante = random.nextDouble() * 100 < probabilidad;

        // Si gana el defensor, se despeja el balón
        if (!ganaAtacante) return false;

        // Si gana el atacante, intenta gol
        return tiroPuerta(atacante, portero);
    }

    // ========== EVENTOS DE JUEGO ==========


    /**
     * Simula un saque de banda.
     *
     * Se compara el pase del ejecutor
     * contra la capacidad del defensor para interceptar.
     */
    public boolean saqueBanda(Jugador ejecutor, Jugador defensor) {

        int ataque = ejecutor.getPase();
        int defensa = defensor.getDefensa() + defensor.getVelocidad();

        double probabilidad = (double) ataque / (ataque + defensa) * 100;

        return random.nextDouble() * 100 < probabilidad;
    }


    /**
     * Determina si hay fuera de juego.
     *
     * Se basa en la diferencia entre:
     * velocidad del atacante y defensa del defensor.
     *
     * Mientras más rápido sea el atacante,
     * mayor probabilidad de estar adelantado.
     */
    public boolean fueraDeJuego(Jugador atacante, Jugador defensor) {

        int diferencia = atacante.getVelocidad() - defensor.getDefensa();

        // Probabilidad base del 10%
        int probabilidad = 10 + diferencia / 5;

        // Se limita el rango para evitar valores irreales
        if (probabilidad < 5) probabilidad = 5;
        if (probabilidad > 40) probabilidad = 40;

        return random.nextInt(100) < probabilidad;
    }
    // ========== TARJETAS ==========

    /**
     * Determina si un jugador recibe tarjeta amarilla.
     *
     * Jugadores con baja defensa tienen más probabilidad
     * de cometer faltas peligrosas.
     */
    public boolean tarjetaAmarilla(Jugador jugador) {

        double probabilidad = (100 - jugador.getDefensa()) / 8.0;

        return random.nextDouble() * 100 < probabilidad;
    }


    /**
     * Determina si hay tarjeta roja directa
     *
     * Si el jugador es ultimo defensor,
     * aumenta considerablemente la probabilidad
     *
     * @param jugador
     * @return
     */
    public boolean tarjetaRoja(Jugador jugador) {

        int probabilidad = 2;

        return random.nextInt(100) < probabilidad;
    }


    // ========== LESIONES Y CAMBIOS ==========

    /**
     * Determina si un jugador se lesiona.
     *
     * Jugadores con bajo físico se lesionan más fácilmente.
     */
    public boolean lesion(Jugador jugador) {

        double probabilidad = (100 - jugador.getFisico()) / 10.0;

        return random.nextDouble() * 100 < probabilidad;
    }

    /**
     * Realiza un cambio (sustitución).
     *
     * PROBLEMA: Este metdo necesita modificar las listas del equipo.
     *
     * SOLUCIONES:
     *
     * OPCIÓN 1: Moverlo a la clase Equipos
     * - Equipos.realizarCambio(Jugador sale, Jugador entra)
     * - Tiene sentido porque Equipos tiene las listas
     *
     * OPCIÓN 2: Hacer que este metdo reciba el Equipo
     * - cambio(Equipos equipo, Jugador sale, Jugador entra)
     *
     * VALIDACIONES NECESARIAS:
     * - El que sale debe estar en titulares
     * - El que entra debe estar en reserva
     * - Máximo 5 cambios por partido
     * - No se puede reemplazar a un expulsado (el equipo queda con menos jugadores)
     *
     * @param sale quien sale de la cancha
     * @param entra quien entra de la banca
     * @return true si el cambio es válido, false si no
     */
    public boolean cambio(Jugador sale, Jugador entra) {
        // TODO: Considerar mover este método a Equipos.java
        // O cambiar la firma para recibir Equipos
        return false;
    }

    /**
     * Cambia la formación táctica durante el partido.
     *
     * CUÁNDO ES ÚTIL:
     * - Equipo va perdiendo → formación ofensiva (ej: 3-3-4)
     * - Equipo va ganando → formación defensiva (ej: 5-4-1)
     * - Hay expulsión → ajustar formación (ej: de 4-4-2 a 4-3-2)
     *
     * LÓGICA:
     * 1. Cambiar la formación del equipo
     * 2. Reasignar posiciones de los jugadores
     *
     * NOTA: Similar a cambio(), este metdo necesita acceso al Equipo.
     * Considera moverlo a Equipos.java o agregarlo en Partido.java
     *
     * @return true si se realizó el cambio
     */
    public boolean cambioPosiciones() {
        // TODO: Considerar mover este método a Equipos.java
        // O implementarlo directamente en Partido.java
        return false;
    }
}