package org.example;

import org.example.entidades.Portero;
import org.example.entidades.Jugador;

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

    public Eventos(){
    }

    // ========== EVENTOS DE GOL ==========

    /**
     * Resuelve un tiro a puerta: ¿es gol o atajado?
     *
     * STATS RELEVANTES:
     * - Atacante: tiro
     * - Portero: reflejos
     *
     * FÓRMULA SUGERIDA:
     * probabilidad = tiro / (tiro + reflejos) * 100
     *
     * @param atacante quien dispara
     * @param portero quien ataja
     * @return true si es gol, false si es atajado
     */
    public boolean tiroPuerta(Jugador atacante, Portero portero) {
        // TODO: Implementar
        return false;
    }

    /**
     * Resuelve un penal: ¿es gol o atajado/fallado?
     *
     * IMPORTANTE: Los penales son MÁS FÁCILES que tiros normales (~75-80% de éxito).
     *
     * OPCIONES:
     * - Multiplicar tiro del jugador por 1.5
     * - Reducir reflejos del portero a la mitad
     * - Probabilidad base de 75% ajustada por diferencia de stats
     *
     * @param tirador quien ejecuta
     * @param portero quien intenta atajar
     * @return true si es gol, false si falla o es atajado
     */
    public boolean penal(Jugador tirador, Portero portero) {
        // TODO: Implementar (más fácil que tiroPuerta)
        return false;
    }

    /**
     * Resuelve un tiro libre.
     *
     * OPCIONES DE DISEÑO:
     *
     * OPCIÓN 1 (Simple): Tratarlo como tiroPuerta()
     *
     * OPCIÓN 2 (Realista): Decidir entre disparo directo o centro
     * - Si tiro > 75 → disparar (usar tiroPuerta)
     * - Si no → centro al área (evaluar pase, luego duelo físico, luego tiroPuerta)
     *
     * @param ejecutor quien cobra el tiro libre
     * @param portero el portero rival
     * @return true si termina en gol, false si no
     */
    public boolean tiroLibre(Jugador ejecutor, Portero portero) {
        // TODO: Implementar (usa tiroPuerta o haz versión con centro)
        return false;
    }

    /**
     * Resuelve un tiro de esquina (córner).
     *
     * PROCESO SUGERIDO (2 pasos):
     *
     * PASO 1: ¿Quién gana el balón en el área?
     * - Atacante: físico
     * - Defensor: físico + defensa
     * - Si gana defensor → return false (despeja)
     *
     * PASO 2: Si gana atacante, ¿es gol?
     * - Usar tiroPuerta(atacante, portero)
     *
     * NOTA: Solo ~3-5% de corners terminan en gol en fútbol real.
     *
     * @param atacante quien intenta rematar
     * @param defensor quien defiende
     * @param portero quien ataja
     * @return true si es gol, false en cualquier otro caso
     */
    public boolean tiroEsquina(Jugador atacante, Jugador defensor, Portero portero) {
        // TODO: Implementar proceso de 2 pasos
        return false;
    }

    // ========== EVENTOS DE JUEGO ==========

    /**
     * Resuelve un saque de banda: ¿mantiene posesión?
     *
     * STATS RELEVANTES:
     * - Ejecutor: pase
     * - Defensor: defensa + velocidad (para interceptar)
     *
     * @param ejecutor quien saca
     * @param defensor quien presiona
     * @return true si mantiene posesión, false si la pierde
     */
    public boolean saqueBanda(Jugador ejecutor, Jugador defensor) {
        // TODO: Implementar
        return false;
    }

    /**
     * Determina si hay fuera de juego en una jugada.
     *
     * QUÉ ES: El atacante está más adelantado que el último defensor cuando recibe el pase.
     *
     * OPCIONES DE IMPLEMENTACIÓN:
     *
     * OPCIÓN 1 (Simple - Aleatoria):
     * - Probabilidad fija: 10-15% de que haya fuera de juego en jugadas de ataque
     *
     * OPCIÓN 2 (Basada en stats):
     * - Comparar velocidad del atacante vs defensa del defensor
     * - Atacante más rápido → más probable que esté adelantado
     *
     * SUGERENCIA: Intenta usar la opcion 2, usa la opcion 1 solo si no te crees capaz.
     *
     * @param atacante el jugador que recibe el pase
     * @param defensor el último defensor
     * @return true si hay fuera de juego (se anula la jugada), false si es válida
     */
    public boolean fueraDeJuego(Jugador atacante, Jugador defensor) {
        // TODO: Implementar (opción 1 es la más simple)
        return false;
    }

    // ========== TARJETAS ==========

    /**
     * Determina si se muestra tarjeta amarilla en una falta.
     *
     * CUÁNDO OCURRE:
     * - Falta fuerte
     * - Jugador con baja defensa hace falta (no sabe defender bien)
     * - Acumulación de faltas
     *
     * FÓRMULA SUGERIDA:
     * probabilidad = (100 - defensa) / 8
     *
     * Ejemplos:
     * - Defensa 80 → (100-80)/8 = 2.5% de amarilla
     * - Defensa 40 → (100-40)/8 = 7.5% de amarilla
     *
     * NOTA: Si un jugador ya tiene amarilla y saca otra → expulsión (roja directa)
     *
     * @param jugador quien comete la falta
     * @return true si recibe tarjeta amarilla, false si no
     */
    public boolean tarjetaAmarilla(Jugador jugador) {
        // TODO: Implementar basándose en stat de defensa
        return false;
    }

    /**
     * Determina si se muestra tarjeta roja directa (expulsión).
     *
     * CUÁNDO OCURRE:
     * - Falta muy grave
     * - Jugada de último defensor (impide gol claro)
     * - Agresión
     *
     * PROBABILIDAD: Muy baja (1-2% en faltas graves)
     *
     * OPCIÓN SIMPLE:
     * - Probabilidad fija de 2%
     *
     * OPCIÓN REALISTA:
     * - Si es último defensor → 20%
     * - Si es falta normal → 1%
     *
     * IMPORTANTE: Si un jugador es expulsado, debe salir del partido y
     * el equipo juega con 10 jugadores (no se puede reemplazar).
     *
     * @param jugador quien comete la falta
     * @param esUltimoDefensor si es falta de último defensor
     * @return true si recibe tarjeta roja (expulsión), false si no
     */
    public boolean tarjetaRoja(Jugador jugador, boolean esUltimoDefensor) {
        // TODO: Implementar con probabilidad baja
        // Si esUltimoDefensor=true, aumentar probabilidad
        return false;
    }

    // ========== LESIONES Y CAMBIOS ==========

    /**
     * Determina si un jugador se lesiona.
     *
     * STATS RELEVANTES:
     * - Físico del jugador (menor físico = más riesgo)
     *
     * FÓRMULA SUGERIDA:
     * probabilidad = (100 - físico) / 10
     *
     * Ejemplos:
     * - Físico 80 → 2% de lesión
     * - Físico 50 → 5% de lesión
     *
     * @param jugador quien puede lesionarse
     * @return true si se lesiona (debe salir), false si no
     */
    public boolean lesion(Jugador jugador) {
        // TODO: Implementar fórmula basada en físico
        return false;
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