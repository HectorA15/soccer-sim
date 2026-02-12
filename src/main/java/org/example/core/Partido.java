package org.example.core;

import org.example.entidades.Equipos;
import org.example.entidades.Formacion;
import org.example.entidades.Jugador;
import org.example.entidades.Portero;
import org.example.nombres.JugadoresNombres;

import java.util.*;

/**
 * Representa un partido de fútbol entre dos equipos.
 * Gestiona la simulación minuto a minuto del partido, procesando eventos aleatorios
 * basados en probabilidades: goles, tarjetas, lesiones, cambios y otros eventos.
 */
public class Partido {
    // ===== PROBABILIDADES DE EVENTOS =====
    private static final double SAQUE_DE_BANDA = 12;
    private static final double PROB_TIRO_LIBRE = 10;
    private static final double PROB_TIRO_EQUINA = 5;
    private static final double PROB_TIRO_A_PUERTA = 4;
    private static final double PROB_TARJETA_AMARILLA = 4;
    private static final double FUERA_DE_JUEGO = 3;
    private static final double LESION = 2;
    private static final double PROB_PENAL = 1;
    private static final double PROB_TARJETA_ROJA = 1;

    private final Equipos equipoLocal;
    private final Equipos equipoVisitante;
    private final Eventos evento;
    Random random = new Random();

    // ===== CONSTRUCTORES =====

    /**
     * Constructor del partido.
     * Inicializa el partido con dos equipos, crea el gestor de eventos y establece la duración.
     * @param local Equipo local
     * @param visitante Equipo visitante
     */
    public Partido(Equipos local, Equipos visitante) {
        this.equipoLocal = local;
        this.equipoVisitante = visitante;
        this.evento = new Eventos();
    }

    // ===== GETTERS =====

    /**
     * Obtiene el equipo local del partido.
     * @return Equipo local
     */
    public Equipos getEquipoLocal() {
        return equipoLocal;
    }

    /**
     * Obtiene el equipo visitante del partido.
     * @return Equipo visitante
     */
    public Equipos getEquipoVisitante() {
        return equipoVisitante;
    }

    // ===== MÉTODOS PÚBLICOS =====

    /**
     * Procesa un minuto del partido, generando eventos aleatorios.
     * Selecciona aleatoriamente el equipo afectado, jugadores involucrados y tipo de evento.
     * Gestiona: goles, penales, tiros libres, esquinas, saques, tarjetas, expulsiones,
     * lesiones con cambios automáticos y fueras de juego.
     * @param minutoActual Minuto actual del partido (0-89)
     * @param equipoLocal Equipo local
     * @param equipoVisitante Equipo visitante
     * @return String describiendo el evento ocurrido en este minuto
     */
    public String procesarMinuto(int minutoActual, Equipos equipoLocal, Equipos equipoVisitante) {
        int numGenerado = random.nextInt(100);
        double prob = 0;
        Jugador jugadorAfectado;
        Jugador jugadorDefensor;
        Equipos equipoAfectado;
        Portero porteroDefensor;

        // Asignar aleatoriamente el equipo que protagoniza el evento
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

        // Validar que haya jugadores disponibles
        if (jugadorAfectado == null || jugadorDefensor == null) {
            return "minuto " + minutoActual + "\t: No hay jugadores disponibles";
        }

        int goles = equipoAfectado.getGoles();
        int tarjetasAmarillas = jugadorAfectado.getTarjetasAmarillas();
        int tarjetasRojas = jugadorAfectado.getTarjetasRojas();
        int lesiones = jugadorAfectado.getLesiones();

        // ======================== PROCESAMIENTO DE EVENTOS ==========================

        // TIRO A PUERTA
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
                        " dispara, pero " + porteroDefensor.getNombre() + " ataja el balon";
            }

            // PENAL
        } else if (numGenerado < (prob += PROB_PENAL)) {
            if (evento.penal(jugadorAfectado, porteroDefensor)) {
                goles++;
                equipoAfectado.setGoles(goles);
                return "minuto " + minutoActual + "\t: " +
                        "GOOOOOOOOL!!!! DE " +
                        jugadorAfectado.getNombre() + " PARA " + equipoAfectado.getNombre();
            } else {
                return "minuto " + minutoActual + "\t: " +
                        "LO PARO LO PARO SENORES LO PARO!!!! " + porteroDefensor.getNombre() + " ATAJO INCREIBLEMENTE";
            }

            // TIRO LIBRE
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

            // TIRO DE ESQUINA
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

            // SAQUE DE BANDA
        } else if (numGenerado < (prob += SAQUE_DE_BANDA)) {
            if (evento.saqueBanda(jugadorAfectado, jugadorDefensor)) {
                return "minuto " + minutoActual + "\t: " + jugadorAfectado.getNombre() + " saque exitoso...";
            } else {
                return "minuto " + minutoActual + "\t: " + jugadorDefensor.getNombre() + " recibe el saque...";
            }

            // TARJETA AMARILLA
        } else if (numGenerado < (prob += PROB_TARJETA_AMARILLA)) {
            String resultado = evento.tarjetaAmarilla(jugadorAfectado);

            if (resultado != null) {
                if (resultado.equals("EXPULSION")) {
                    return "minuto " + minutoActual + "\t: " +
                            "EXPULSION! " + jugadorAfectado.getNombre() +
                            " recibe la segunda amarilla y es expulsado! " +
                            equipoAfectado.getNombre() + " se queda con " +
                            equipoAfectado.contarJugadoresDisponibles() + " jugadores";
                } else {
                    return "minuto " + minutoActual + "\t: " +
                            "Tarjeta Amarilla para " + jugadorAfectado.getNombre();
                }
            } else {
                return "minuto " + minutoActual + "\t: " + "";
            }

            // TARJETA ROJA
        } else if (numGenerado < (prob += PROB_TARJETA_ROJA)) {
            if (evento.tarjetaRoja(jugadorAfectado)) {
                return "minuto " + minutoActual + "\t: " +
                        "TARJETA ROJA DIRECTA! " + jugadorAfectado.getNombre() +
                        " es expulsado! " + equipoAfectado.getNombre() +
                        " se queda con " + equipoAfectado.contarJugadoresDisponibles() + " jugadores";
            } else {
                return "minuto " + minutoActual + "\t: " + "";
            }

            // FUERA DE JUEGO
        } else if (numGenerado < (prob += FUERA_DE_JUEGO)) {
            if (evento.fueraDeJuego(jugadorAfectado, jugadorDefensor)) {
                return "minuto " + minutoActual + "\t: " + jugadorAfectado.getNombre() +
                        " estaba en una posicion adelantada, Se anula la jugada!";
            } else {
                return "minuto " + minutoActual + "\t: " + "";
            }

            // LESION (con cambio automático)
        } else if (numGenerado < (prob += LESION)) {
            if (evento.lesion(jugadorAfectado)) {
                lesiones++;
                jugadorAfectado.setLesiones(lesiones);

                // Intentar cambio automático
                Jugador[] suplentes = equipoAfectado.getReserva();

                if (suplentes.length > 0 && equipoAfectado.getCambiosRealizados() < 5) {
                    // Buscar primer suplente disponible
                    Jugador suplente = null;
                    for (Jugador sup : suplentes) {
                        if (sup.getLesiones() == 0 && !sup.isExpulsado()) {
                            suplente = sup;
                            break;
                        }
                    }

                    if (suplente != null && equipoAfectado.cambio(jugadorAfectado, suplente)) {
                        return "minuto " + minutoActual + "\t: " +
                                jugadorAfectado.getNombre() + " cae lesionado! " +
                                "Sale del campo y entra " + suplente.getNombre() + " en su lugar. " +
                                "Cambio " + equipoAfectado.getCambiosRealizados() + "/5";
                    } else {
                        return "minuto " + minutoActual + "\t: " +
                                jugadorAfectado.getNombre() + " cae lesionado pero NO hay suplentes disponibles!";
                    }
                } else {
                    if (equipoAfectado.getCambiosRealizados() >= 5) {
                        return "minuto " + minutoActual + "\t: " +
                                jugadorAfectado.getNombre() + " cae lesionado pero ya se agotaron los cambios! Debe continuar";
                    } else {
                        return "minuto " + minutoActual + "\t: " +
                                jugadorAfectado.getNombre() + " cae lesionado pero NO hay suplentes disponibles!";
                    }
                }
            } else {
                return "minuto " + minutoActual + "\t: " + "";
            }
        } else {
            return "minuto " + minutoActual + "\t: " + "";
        }
    }

}