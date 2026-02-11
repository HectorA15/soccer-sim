package org.example.core;

import org.example.entidades.Equipos;
import org.example.entidades.Jugador;
import org.example.entidades.Portero;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Partido {
    // ============== % PROBABILIDADES FIJAS ===============
    private static final double SAQUE_DE_BANDA = 12;
    private static final double PROB_TIRO_LIBRE = 10;
    private static final double PROB_TIRO_EQUINA = 5;
    private static final double PROB_TIRO_A_PUERTA = 4;
    private static final double PROB_TARJETA_AMARILLA = 4;
    private static final double FUERA_DE_JUEGO = 3;
    private static final double LESION = 2;
    private static final double PROB_PENAL = 1;
    private static final double PROB_TARJETA_ROJA = 1;

    private final Equipos local;
    private final Equipos visitante;
    private final Eventos evento;
    Timer timer;
    Random random = new Random();
    private int minuto;
    private final int duracionPartido;

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
                return "minuto " + minutoActual + "\t: " +
                        jugadorAfectado.getNombre() +
                        " dispara y marca gol para " + equipoAfectado.getNombre();
            } else {
                return "minuto " + minutoActual + "\t: " +
                        jugadorAfectado.getNombre() +
                        " dispara, pero " + porteroDefensor.getNombre() + " ataja el balón";
            }

        } else if (numGenerado < (prob += PROB_PENAL)) {
            if (evento.penal(jugadorAfectado, porteroDefensor)) {
                goles++;
                equipoAfectado.setGoles(goles);
                return "minuto " + minutoActual + "\t: " +
                        "GOOOOOOOOL!!!! DE " +
                        jugadorAfectado.getNombre() + "PARA LAS " + equipoAfectado.getNombre();
            } else {
                return "minuto " + minutoActual + "\t: " +
                        "LO PARO LO PARO SEÑORES LO PARO!!!! " + porteroDefensor.getNombre() + " ATAJO INCREIBLEMENTE";
            }

        } else if (numGenerado < (prob += PROB_TIRO_LIBRE)) {
            if (evento.tiroLibre(jugadorAfectado, porteroDefensor)) {
                goles++;
                equipoAfectado.setGoles(goles);
                return "minuto " + minutoActual + "\t: " +
                        "GOLAZOOOOOOO DE " + jugadorAfectado.getNombre() + " PARA " + equipoAfectado.getNombre();
            } else {
                return "minuto " + minutoActual + "\t: "
                        + "NOOOOO QUE LERDOOOO " + jugadorAfectado.getNombre() + " FALLO TERRIBLEMENTE";
            }

        } else if (numGenerado < (prob += PROB_TIRO_EQUINA)) {
            if (evento.tiroEsquina(jugadorAfectado, jugadorDefensor, porteroDefensor)) {
                goles++;
                equipoAfectado.setGoles(goles);
                return "minuto " + minutoActual + "\t: " +
                        "QUE BUEN TIRAZOOOOO DE " + jugadorAfectado.getNombre();
            } else {
                return "minuto " + minutoActual + "\t: " + "VAYAAA JUGADOR " + jugadorDefensor.getNombre() +
                        " QUE DEFENSAAA DIOS MIO";
            }

        } else if (numGenerado < (prob += SAQUE_DE_BANDA)) {
            if (evento.saqueBanda(jugadorAfectado, jugadorDefensor)) {
                return "minuto " + minutoActual + "\t: " + jugadorAfectado.getNombre() + " saque exitoso...";
            } else {
                return "minuto " + minutoActual + "\t: " + jugadorDefensor.getNombre() + " recibe el saque...";
            }

        } else if (numGenerado < (prob += PROB_TARJETA_AMARILLA)) {
            if (evento.tarjetaAmarilla(jugadorAfectado)) {
                tarjetasAmarillas++;
                jugadorAfectado.setTarjetasAmarillas(tarjetasAmarillas);
                return minutoActual + ": " + "Tarjeta Amarilla! " + jugadorAfectado.getNombre() + " No parece muy contento...";
            } else {
                return "minuto " + minutoActual;
            }
        } else if (numGenerado < (prob += PROB_TARJETA_ROJA)) {
            if (evento.tarjetaRoja(jugadorAfectado)) {
                tarjetasRojas++;
                jugadorAfectado.setTarjetasRojas(tarjetasRojas);
                return "minuto " + minutoActual + "\t: " + "Tarjeta roja!!!, para " + jugadorAfectado.getNombre();
            } else {
                return "minuto " + minutoActual;
            }

        } else if (numGenerado < (prob += FUERA_DE_JUEGO)) {
            if (evento.fueraDeJuego(jugadorAfectado, jugadorDefensor)) {
                return minutoActual + ": " + jugadorAfectado.getNombre() + " estaba en una posicion adelantada, Se anula la jugada!";
            } else {
                return "minuto " + minutoActual;
            }

        } else if (numGenerado < (prob += LESION)) {
            if (evento.lesion(jugadorAfectado)) {
                lesiones++;
                jugadorAfectado.setLesiones(lesiones);
                return "minuto " + minutoActual + "\t: " + "uuuh el jugador " + jugadorAfectado.getNombre() + " cae lesionado ";
            } else {
                return "minuto " + minutoActual;
            }
        } else {
            return "minuto " + minutoActual;
        }
    }
}