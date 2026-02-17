package org.example.core;

import org.example.entidades.Portero;
import org.example.entidades.Jugador;
import java.util.Random;

/**
 * Gestiona la resolución lógica de los eventos del partido.
 * REFACTORIZADO: Ahora el portero tiene mucho más peso (Reflejos x3.5)
 * y las probabilidades son más realistas para evitar marcadores excesivos.
 */
public class Eventos {

    private final Random random = new Random();

    // FACTOR DE DIFICULTAD DEL PORTERO
    // Cuanto más alto, más difícil es meter gol.
    // 1.0 = Igualdad total (50% gol). 3.5 = El portero tiene mucha ventaja.
    private static final double PESO_PORTERO = 3.5;

    public Eventos() {
    }

    // ===== EVENTOS DE GOL =====

    /**
     * Simula un tiro a puerta estándar.
     * El portero usa sus reflejos multiplicados por el PESO_PORTERO.
     */
    public boolean tiroPuerta(Jugador atacante, Portero portero) {

        double potenciaTiro = atacante.getTiro() + (atacante.getVelocidad() * 0.2);

        double resistenciaPortero = portero.getReflejos() * PESO_PORTERO;

        // Probabilidad (Ej: 80 vs 280 -> 22% de gol)
        double probabilidadGol = (potenciaTiro / (potenciaTiro + resistenciaPortero)) * 100;

        // +/- 5% aleatorio
        probabilidadGol += (random.nextInt(10) - 5);

        return random.nextDouble() * 100 < probabilidadGol;
    }

    /**
     * Simula un penal.
     * AQUÍ EL TIRADOR TIENE VENTAJA.
     * El factor del portero se reduce porque el tiro es a quemarropa.
     */
    public boolean penal(Jugador tirador, Portero portero) {
        // En penal, el tiro vale x4
        double potenciaTiro = tirador.getTiro() * 4.0;

        // El portero tiene un menor multiplicador
        double resistenciaPortero = portero.getReflejos() * 2.0;

        double probabilidadGol = (potenciaTiro / (potenciaTiro + resistenciaPortero)) * 100;

        // Capamos las probabilidades (max 90%, min 50%)
        if (probabilidadGol > 90) probabilidadGol = 90;
        if (probabilidadGol < 50) probabilidadGol = 50;

        return random.nextDouble() * 100 < probabilidadGol;
    }

    /**
     * Simula un tiro libre.
     * Si el tiro es muy bueno (>85), va directo a puerta.
     * Si no, es un centro que depende del PASE.
     */
    public boolean tiroLibre(Jugador ejecutor, Portero portero) {
        if (ejecutor.getTiro() > 85) {
            // bono de +10 al tiro para tiros potentes
            double potencia = ejecutor.getTiro() + 10;
            double resistencia = portero.getReflejos() * PESO_PORTERO;
            double prob = (potencia / (potencia + resistencia)) * 100;
            return random.nextDouble() * 100 < prob;
        }

        // Si el pase es malo, el portero lo atrapa fácil
        int calidadCentro = ejecutor.getPase();
        boolean centroBueno = random.nextInt(100) < calidadCentro;

        if (!centroBueno) return false; // El centro se fue fuera

        // Si el centro es bueno, se resuelve como un tiro normal pero con menos potencia
        return tiroPuerta(ejecutor, portero);
    }

    /**
     * Simula un tiro de esquina.
     * 1. El portero intenta cortar el pase (Salida vs Pase).
     * 2. Si falla, hay duelo físico (Atacante vs Defensa).
     * 3. Si gana atacante, remata de cabeza (Tiro vs Reflejos).
     */
    public boolean tiroEsquina(Jugador atacante, Jugador defensor, Portero portero) {

        // Asumimos que "Saque" del portero también implica su capacidad de salir jugando
        double capacidadSalida = (portero.getReflejos() + portero.getSaque()) / 2.0;
        double calidadCentro = atacante.getPase();

        // Si el portero es valiente, sale a cortar (50% de las veces intenta salir)
        if (random.nextBoolean()) {
            double probCortar = (capacidadSalida / (capacidadSalida + calidadCentro)) * 100;
            if (random.nextDouble() * 100 < probCortar) {
                return false; // El portero atrapó el centro en el aire
            }
        }

        // Si el portero no salió o falló
        // El atacante necesita ganar la posición
        int fuerzaAtacante = atacante.getFisico();
        int fuerzaDefensor = defensor.getFisico() + defensor.getDefensa();

        // El defensor tiene ventaja por posición (sumamos defensa)
        double probGanarPosicion = (double) fuerzaAtacante / (fuerzaAtacante + fuerzaDefensor) * 100;

        if (random.nextDouble() * 100 > probGanarPosicion) {
            return false; // Despejó el defensa
        }

        // Los cabezazos son menos precisos que los tiros con el pie
        double potenciaCabezazo = atacante.getTiro() * 0.7;
        double resistenciaPortero = portero.getReflejos() * PESO_PORTERO;

        double probGol = (potenciaCabezazo / (potenciaCabezazo + resistenciaPortero)) * 100;

        return random.nextDouble() * 100 < probGol;
    }

    // ===== EVENTOS DE JUEGO  =====

    public boolean saqueBanda(Jugador ejecutor, Jugador defensor) {
        // El pase vs la intercepción (Defensa + Velocidad)
        int ataque = ejecutor.getPase();
        // Reducimos un poco el peso de la defensa para que no roben siempre
        double defensa = (defensor.getDefensa() * 0.7) + (defensor.getVelocidad() * 0.3);

        double probabilidad = ataque / (ataque + defensa) * 100;
        return random.nextDouble() * 100 < probabilidad;
    }

    public boolean fueraDeJuego(Jugador atacante, Jugador defensor) {
        // Aquí la velocidad es CLAVE
        int velocidadAtaque = atacante.getVelocidad();
        int inteligenciaDefensa = defensor.getDefensa();

        // Si el atacante es muy rápido, tiende a caer más en fuera de juego por ansioso
        // O si el defensa es muy bueno, tira la línea del fuera de juego

        double probOffside = 10;

        if (velocidadAtaque > inteligenciaDefensa) {
            probOffside += 15; // Es rápido pero se adelanta
        }

        return random.nextInt(100) < probOffside;
    }

    // ===== TARJETAS Y LESIONES =====

    public String tarjetaAmarilla(Jugador jugador) {
        // Los jugadores con poca defensa suelen hacer faltas
        // Probabilidad base baja (20% de que la falta sea de tarjeta)
        double probabilidad = (100 - jugador.getDefensa()) / 20.0;

        if (random.nextDouble() * 100 < probabilidad) {
            return jugador.agregarTarjetaAmarilla() ? "EXPULSION" : "AMARILLA";
        }
        return null;
    }

    public boolean tarjetaRoja(Jugador jugador) {
        jugador.agregarTarjetaRoja();
        return true;
    }

    public boolean lesion(Jugador jugador) {
        // Jugadores con poco físico mas facil que se lesionen
        double probabilidad = (100 - jugador.getFisico()) / 25.0; // Dividido por 25 para que sea raro
        return random.nextDouble() * 100 < probabilidad;
    }
}