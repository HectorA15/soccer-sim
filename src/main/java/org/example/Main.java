package org.example;

import org.example.entidades.Equipos;
import org.example.entidades.Formacion;
import org.example.entidades.Jugador;
import org.example.entidades.Portero;
import org.example.nombres.JugadoresNombres;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Clase principal para crear y probar equipos de fútbol.
 *
 * Funcionalidad actual:
 * - Crea dos equipos (local y visitante)
 * - Distribuye 30 jugadores de forma balanceada y aleatoria
 * - Asigna formación 4-4-2 a ambos equipos
 * - Imprime la información completa de cada equipo
 */
public class Main {

    /**
     * Método principal que genera dos equipos balanceados.
     *
     * ALGORITMO DE DISTRIBUCIÓN:
     * 1. Obtiene 30 nombres de jugadores predefinidos
     * 2. Los mezcla aleatoriamente con Collections.shuffle()
     * 3. Divide el array en dos mitades iguales (15 y 15)
     * 4. Primera mitad → equipo local
     * 5. Segunda mitad → equipo visitante
     *
     * VENTAJAS de este enfoque:
     * - Garantiza balance perfecto (15 jugadores cada equipo)
     * - Cada ejecución genera equipos diferentes (aleatorio)
     * - Simple de entender y mantener
     *
     * ESTRUCTURA FINAL de cada equipo:
     * - 1 portero (primer jugador sin portero)
     * - 10 jugadores titulares
     * - 4 jugadores suplentes
     */
    public static void main(String[] args) {

        String[] nombresJugadores = JugadoresNombres.getJugadores();

        // Crear equipos vacíos
        Equipos equipoLocal = new Equipos();
        equipoLocal.setNombre("Chivas");
        equipoLocal.setLocal(true);

        Equipos equipoVisitante = new Equipos();
        equipoVisitante.setNombre("Las poderosisimas aguilas del america");
        equipoVisitante.setLocal(false);

        // ===== PASO 1: MEZCLAR NOMBRES ALEATORIAMENTE =====
        // Convertimos el array a List porque Collections.shuffle() solo funciona con listas
        List<String> nombresList = Arrays.asList(nombresJugadores);
        Collections.shuffle(nombresList);  // Orden aleatorio cada vez que se ejecuta

        // ===== PASO 2: DIVIDIR EN DOS MITADES =====
        int mitad = nombresList.size() / 2;  // Con 30 jugadores: mitad = 15

        // ===== PASO 3: ASIGNAR PRIMERA MITAD AL EQUIPO LOCAL =====
        // Índices 0-14 (15 jugadores)
        for (int i = 0; i < mitad; i++) {
            agregarJugador(equipoLocal, nombresList.get(i));
        }

        // ===== PASO 4: ASIGNAR SEGUNDA MITAD AL EQUIPO VISITANTE =====
        // Índices 15-29 (15 jugadores)
        for (int i = mitad; i < nombresList.size(); i++) {
            agregarJugador(equipoVisitante, nombresList.get(i));
        }

        // ===== PASO 5: CONFIGURAR FORMACIONES =====
        // Ambos equipos usan 4-4-2 (4 defensas, 4 mediocampistas, 2 delanteros)
        Formacion formacionLocal = new Formacion(4, 4, 2);
        Formacion formacionVisitante = new Formacion(4, 4, 2);
        equipoLocal.setFormacion(formacionLocal);
        equipoVisitante.setFormacion(formacionVisitante);

        // ===== PASO 6: ASIGNAR POSICIONES SEGÚN FORMACIÓN =====
        // Esto asigna DEFENSA/MEDIOCAMP/DELANTERO a cada jugador titular
        // basándose en la formación configurada
        equipoLocal.asignarPosiciones();
        equipoVisitante.asignarPosiciones();

        // ===== PASO 7: IMPRIMIR RESULTADOS =====
        System.out.println("=".repeat(100) + "\n");
        System.out.println(equipoLocal);
        System.out.println("=".repeat(100) + "\n");
        System.out.println(equipoVisitante);
    }

    /**
     * Agrega un jugador al equipo especificado.
     *
     * LÓGICA DE ASIGNACIÓN:
     * 1. Si el equipo NO tiene portero → crea un Portero
     * 2. Si ya tiene portero → crea un Jugador de campo
     * 3. Intenta agregarlo como titular (máximo 10)
     * 4. Si ya hay 10 titulares → lo manda a reserva
     *
     * RESULTADO con 15 jugadores:
     * - Jugador #1 → Portero
     * - Jugadores #2-11 → Titulares (10 jugadores de campo)
     * - Jugadores #12-15 → Reserva (4 suplentes)
     *
     * @param equipo el equipo al que se agregará el jugador
     * @param nombre el nombre del jugador a crear
     */
    private static void agregarJugador(Equipos equipo, String nombre) {
        if (!equipo.isPortero()) {
            // Este equipo aún no tiene portero, crear uno
            Portero porteroTemp = new Portero(nombre);
            porteroTemp.setRandomStats();  // Stats aleatorias (incluyendo saque y reflejos)
            equipo.setPortero(porteroTemp);

        } else {
            // Ya hay portero, crear jugador de campo
            Jugador jugadorTemp = new Jugador(nombre);
            jugadorTemp.setRandomStats();  // Stats aleatorias (5 básicas)

            // Intentar agregar como titular
            if (!equipo.setJugador(jugadorTemp)) {
                // No se pudo agregar (ya hay 10 titulares), va a reserva
                equipo.setReserva(jugadorTemp);
            }
        }
    }
}