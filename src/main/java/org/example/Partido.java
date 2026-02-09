package org.example;

import org.example.entidades.Equipos;
import org.example.entidades.Jugador;
import org.example.entidades.Portero;

import java.util.Random;
import java.util.Timer;

public class Partido {
    private Equipos local;
    private Equipos visitante;
    private int minutoActual;
    private int golesLocal;
    private int golesVisitante;
    private int duracionPartido;
    private Eventos eventos;
    Timer timer;
    Random random = new Random();
    Eventos evento = new Eventos();


    // ============== PROBABILIDES FIJAS ===============
    private static final int PROB_TIRO_A_PUERTA = 5;
    private static final int PROB_PENAL = 5;
    private static final int  PROB_TIRO_LIBRE = 2;
    private static final int PROB_TIRO_EQUINA = 1;
    private static final int PROB_TARJETA_AMARILLA = 1;
    private static final int PROB_TARJETA_ROJA = 1;
    private static final int SAQUE_DE_BANDA = 1;
    private static final int FUERA_DE_JUEGO = 1;
    private static final int LESION = 1;



    public Partido(Equipos local, Equipos visitante) {
        this.local = local;
        this.visitante = visitante;
        this.eventos = new Eventos();
        this.minutoActual = 0;
    }

    public void simular() {
        //TODO
    }

    /**
     * Procesa un minuto del partido.
     *
     * Cada minuto tiene una probabilidad de que
     * ocurra un evento diferente.
     *
     * Los jugadores afectados y los defensores deben recibirse
     * de forma aleatoria.
     *
     * @param minuto
     * @param jugadorAfectado
     * @param jugadorDefensor
     * @param portero
     */

    private void procesarMinuto(int minuto, Equipos equipoLocal, Equipos equipoVisitante) {
        int numGenerado = random.nextInt(100);
        int prob = 0;

        Jugador jugadorAfectado;
        Jugador jugadorDefensor;
        Equipos equipoAfectado;
        Equipos equipoDefensor;
        Portero porteroAfectado;
        Portero porteroDefensor;

        if (random.nextBoolean()){
            equipoAfectado = equipoLocal;
            equipoDefensor = equipoVisitante;

            jugadorAfectado = equipoLocal.getJugadorRandom();
            jugadorDefensor = equipoVisitante.getJugadorRandom();

            porteroDefensor = equipoVisitante.getPortero();
        }else{
            equipoAfectado = equipoVisitante;
            equipoDefensor = equipoLocal;

            jugadorAfectado = equipoLocal.getJugadorRandom();
            jugadorDefensor = equipoVisitante.getJugadorRandom();

            porteroDefensor = equipoLocal.getPortero();
        }

        int goles = equipoAfectado.getGoles();
        int tarjetasAmarillas = jugadorAfectado.getTarjetasAmarillas();
        int tarjetasRojas = jugadorAfectado.getTarjetasRojas();
        int lesiones = jugadorAfectado.getLesiones();

        if (numGenerado < (prob += PROB_TIRO_A_PUERTA) ){ //5% probabilidad tiro a puerta

        } else if (numGenerado < (prob += PROB_PENAL)){ // 2% de probabilidad de penal
            if (evento.penal(jugadorAfectado, porteroDefensor) ){
                goles++;
                equipoAfectado.setGoles(goles);
            }

        } else if (numGenerado < (prob += PROB_TIRO_LIBRE)){ // 2% de probabilidad de tiro libre
            if (evento.tiroLibre(jugadorAfectado, porteroDefensor) ){
                goles++;
                equipoAfectado.setGoles(goles);
            }

        } else if (numGenerado < (prob += PROB_TIRO_EQUINA)){ // 1% de probabilidad de tiro equina
            if (evento.tiroEsquina(jugadorAfectado, jugadorDefensor, porteroDefensor) ){
                goles++;
                equipoAfectado.setGoles(goles);
            }

        } else if (numGenerado < (prob += SAQUE_DE_BANDA)){ // 1% de probabilidad de saque de banda
            if (evento.saqueBanda(jugadorAfectado, jugadorDefensor) ){
                //TODO
            }

        } else if (numGenerado < (prob += PROB_TARJETA_AMARILLA)){ // 1% de probabilidad de tarjeta amarilla
            if ( evento.tarjetaAmarilla(jugadorAfectado) ){
                tarjetasAmarillas++;
                jugadorAfectado.setTarjetasAmarillas(tarjetasAmarillas);
            }
        } else if (numGenerado < (prob += PROB_TARJETA_ROJA)){ // 1% de probabilidad de tarjeta roja
            if ( evento.tarjetaRoja(jugadorAfectado) ){
                tarjetasRojas++;
                jugadorAfectado.setTarjetasRojas(tarjetasRojas);
            }

        } else if (numGenerado < (prob += FUERA_DE_JUEGO)){ // 1% de probabilidad de fuerda de juego
            if ( evento.fueraDeJuego(jugadorAfectado, jugadorDefensor) ){
                //TODO
            }

        } else if (numGenerado < (prob += LESION)){ // 1% de probabilidad de lesion
            if ( evento.lesion(jugadorAfectado) ){
                lesiones++;
                jugadorAfectado.setLesiones(lesiones);
            }
        }

    }
}