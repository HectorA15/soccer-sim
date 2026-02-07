package org.example;

import org.example.entidades.Equipos;
import org.example.entidades.Formacion;
import org.example.entidades.Jugador;
import org.example.entidades.Portero;
import org.example.nombres.JugadoresNombres;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        String[] nombresJugadores = JugadoresNombres.getJugadores();
        Random random = new Random();

        Equipos equipoLocal = new Equipos();
        equipoLocal.setNombre("Chivas");
        equipoLocal.setLocal(true);


        Equipos equipoVisitante = new Equipos();
        equipoVisitante.setNombre("Las poderosisimas aguilas del america");
        equipoVisitante.setLocal(false);

        //
        for (int i = 0; i < nombresJugadores.length; i++) {

            Equipos equipo = random.nextBoolean() ? equipoLocal : equipoVisitante;

            if (!equipo.isPortero()) {
                Portero porteroTemp = new Portero(nombresJugadores[i]);
                porteroTemp.setRandomStats();
                equipo.setPortero(porteroTemp);

            } else {
                Jugador jugadorTemp = new Jugador(nombresJugadores[i]);
                jugadorTemp.setRandomStats();

                if (!equipo.isFull()) {
                    equipo.setJugador(jugadorTemp);
                } else {
                    equipo.setReserva(jugadorTemp);
                }
            }
        }

        Formacion formacionLocal = new Formacion(4,4,2);
        Formacion formacionVisitante = new Formacion(4,4,2);
        equipoLocal.setFormacion(formacionLocal);
        equipoVisitante.setFormacion(formacionVisitante);
        equipoLocal.asignarPosiciones(random);
        equipoVisitante.asignarPosiciones(random);

        System.out.println("=".repeat(100)+ "\n");
        System.out.println(equipoLocal);
        System.out.println("=".repeat(100)+ "\n");
        System.out.println(equipoVisitante);
    }
}
