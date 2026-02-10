package org.example.core;

import org.example.entidades.Equipos;
import org.example.entidades.Jugador;
import org.example.entidades.Portero;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Partido {
    // ============== % PROBABILIDADES FIJAS ===============
    private static final double SAQUE_DE_BANDA = 30;
    private static final double PROB_TIRO_LIBRE = 17;
    private static final double PROB_TIRO_EQUINA = 10;
    private static final double PROB_TIRO_A_PUERTA = 9;
    private static final double PROB_TARJETA_AMARILLA = 7;
    private static final double FUERA_DE_JUEGO = 6;
    private static final double LESION = 5;
    private static final double PROB_PENAL = 3;
    private static final double PROB_TARJETA_ROJA = 1;
    Timer timer;
    Random random = new Random();
    private final Equipos local;
    private final Equipos visitante;
    private int minuto;
    private int duracionPartido;
    private final Eventos evento;


    public Partido(Equipos local, Equipos visitante) {
        this.local = local;
        this.visitante = visitante;
        this.evento = new Eventos();
        this.minuto = 0;

        this.duracionPartido = 90;
        this.timer = new Timer();
    }

    public void simular() {
        minuto = 0;

        timer = new Timer();

        TimerTask tareaCadaSegundo = new TimerTask() {
            @Override
            public void run() {
                minuto++;

                String textoMinuto = procesarMinuto(minuto, local, visitante);
                System.out.println(textoMinuto);

                if (minuto >= duracionPartido) {
                    timer.cancel();
                    timer.purge();

                    System.out.println("Final del partido: " +
                            local.getNombre() + " " + local.getGoles() +
                            " - " + visitante.getGoles() + " " + visitante.getNombre());
                }
            }
        };

        timer.scheduleAtFixedRate(tareaCadaSegundo, 0, 1000);
    }
    /**
     * Procesa un minuto del partido.
     * <p>
     * Cada minuto tiene una probabilidad de que
     * ocurra un evento diferente.
     * <p>
     * Los jugadores afectados y los defensores deben recibirse
     * de forma aleatoria.
     *
     * @param minutoActual
     * @param equipoLocal
     * @param equipoVisitante
     */
    private String procesarMinuto(int minutoActual, Equipos equipoLocal, Equipos equipoVisitante) {
        int numGenerado = random.nextInt(100);
        double prob = 0;


        // ===== CREACION DE EQUIPOS Y JUGADORES INVOLUCRADOS =====
        Jugador jugadorAfectado;
        Jugador jugadorDefensor;
        Equipos equipoAfectado;
        Portero porteroDefensor;

        // ==== SE ASIGNA ALEATORIAMENTE A QUIEN SE LE DA EL EVENTO ====
        if (random.nextBoolean()) {
            equipoAfectado = equipoLocal;

            jugadorAfectado = equipoLocal.getJugadorRandom();
            jugadorDefensor = equipoVisitante.getJugadorRandom();

            porteroDefensor = equipoVisitante.getPortero();
        } else {
            equipoAfectado = equipoVisitante;

            jugadorAfectado = equipoVisitante.getJugadorRandom();
            jugadorDefensor = equipoLocal.getJugadorRandom();

            porteroDefensor = equipoLocal.getPortero();
        }

        // ==== SE RECIBEN LOS DATOS DEL JUGADOR QUE SE PUEDAN LLEGAR A MODIFICAR ====
        int goles = equipoAfectado.getGoles();
        int tarjetasAmarillas = jugadorAfectado.getTarjetasAmarillas();
        int tarjetasRojas = jugadorAfectado.getTarjetasRojas();
        int lesiones = jugadorAfectado.getLesiones();


        // ======================== SE PROCESAN LOS EVENTOS ==========================
        if (numGenerado < (prob += PROB_TIRO_A_PUERTA)) {
            if (evento.tiroPuerta(jugadorAfectado, porteroDefensor)) {
                goles++;
                equipoAfectado.setGoles(goles);
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            } else {
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            }

        } else if (numGenerado < (prob += PROB_PENAL)) {
            if (evento.penal(jugadorAfectado, porteroDefensor)) {
                goles++;
                equipoAfectado.setGoles(goles);
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            } else {
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            }

        } else if (numGenerado < (prob += PROB_TIRO_LIBRE)) {
            if (evento.tiroLibre(jugadorAfectado, porteroDefensor)) {
                goles++;
                equipoAfectado.setGoles(goles);
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            } else {
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            }

        } else if (numGenerado < (prob += PROB_TIRO_EQUINA)) {
            if (evento.tiroEsquina(jugadorAfectado, jugadorDefensor, porteroDefensor)) {
                goles++;
                equipoAfectado.setGoles(goles);
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            } else {
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            }

        } else if (numGenerado < (prob += SAQUE_DE_BANDA)) {
            if (evento.saqueBanda(jugadorAfectado, jugadorDefensor)) {
                //TODO: no afecta al partido solo va generar un texto si sale bien o mal el saque de banda
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            } else {
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            }

        } else if (numGenerado < (prob += PROB_TARJETA_AMARILLA)) {
            if (evento.tarjetaAmarilla(jugadorAfectado)) {
                tarjetasAmarillas++;
                jugadorAfectado.setTarjetasAmarillas(tarjetasAmarillas);
                return minutoActual + ": " + "Tarjeta Amarilla! " + jugadorAfectado.getNombre() + " No parece muy contento...";
            } else {
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            }
        } else if (numGenerado < (prob += PROB_TARJETA_ROJA)) {
            if (evento.tarjetaRoja(jugadorAfectado)) {
                tarjetasRojas++;
                jugadorAfectado.setTarjetasRojas(tarjetasRojas);
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            } else {
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            }

        } else if (numGenerado < (prob += FUERA_DE_JUEGO)) {
            if (evento.fueraDeJuego(jugadorAfectado, jugadorDefensor)) {
                return minutoActual + ": " + jugadorAfectado.getNombre() + " estaba en una posicion adelantada, Se anula la jugada!";
            } else {
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            }

        } else if (numGenerado < (prob += LESION)) {
            if (evento.lesion(jugadorAfectado)) {
                lesiones++;
                jugadorAfectado.setLesiones(lesiones);
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            } else {
                return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
            }
        } else {
            return "minuto " + minutoActual + "\t: " + "llenar de texto aqui";
        }

    }
}