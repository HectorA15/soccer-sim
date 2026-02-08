package org.example.enums;

/**
 * Define las posiciones de un jugador en el campo de fútbol.
 *
 * ═══════════════════════════════════════════════════════════════════════
 * ¿QUÉ ES UN ENUM?
 * ═══════════════════════════════════════════════════════════════════════
 *
 * Un enum (enumeración) es una clase especial que representa UN CONJUNTO
 * FIJO de valores constantes.
 *
 * Piensa en un enum como un "menú de opciones" donde SOLO puedes elegir
 * las opciones que están en el menú, nada más.
 *
 * ═══════════════════════════════════════════════════════════════════════
 * ¿POR QUÉ USAR ENUM EN VEZ DE STRING?
 * ═══════════════════════════════════════════════════════════════════════
 *
 * ❌ OPCIÓN MALA (usando String):
 * <pre>
 * String posicion = "Delantero";  // Puede tener typos
 * String posicion = "delantero";  // Diferente por mayúsculas
 * String posicion = "DELATERO";   // Typo, compilador no detecta error
 * String posicion = "Arquero";    // Nombre diferente, inconsistente
 * </pre>
 *
 * ✅ OPCIÓN BUENA (usando enum):
 * <pre>
 * Posicion posicion = Posicion.DELANTERO;  // Solo estas 4 opciones existen
 * Posicion posicion = Posicion.DEFENSA;    // IntelliJ te ayuda con autocompletado
 * Posicion posicion = Posicion.MEDIOCAMP;  // No hay typos posibles
 * Posicion posicion = Posicion.PORTERIA;   // Nombres consistentes siempre
 * </pre>
 *
 * VENTAJAS:
 * 1. ✅ No hay typos (errores de escritura)
 * 2. ✅ IntelliJ te da autocompletado
 * 3. ✅ El compilador detecta errores si escribes mal
 * 4. ✅ Nombres consistentes en todo el proyecto
 * 5. ✅ Más eficiente en memoria que String
 *
 * ═══════════════════════════════════════════════════════════════════════
 * CÓMO USAR ESTE ENUM
 * ═══════════════════════════════════════════════════════════════════════
 *
 * EJEMPLO 1: Asignar una posición a un jugador
 * <pre>
 * Jugador messi = new Jugador("Lionel Messi");
 * messi.setPosicion(Posicion.DELANTERO);  // Así se usa
 * </pre>
 *
 * EJEMPLO 2: Comparar posiciones
 * <pre>
 * if (jugador.getPosicion() == Posicion.PORTERIA) {
 *     System.out.println("Es portero");
 * }
 * </pre>
 * NOTA: Con enum usas == (no .equals() como con String)
 *
 * EJEMPLO 3: Switch con enum
 * <pre>
 * switch (jugador.getPosicion()) {
 *     case PORTERIA:
 *         System.out.println("Guardameta");
 *         break;
 *     case DEFENSA:
 *         System.out.println("Defensor");
 *         break;
 *     case MEDIOCAMP:
 *         System.out.println("Mediocampista");
 *         break;
 *     case DELANTERO:
 *         System.out.println("Atacante");
 *         break;
 * }
 * </pre>
 *
 * EJEMPLO 4: Obtener todos los valores del enum
 * <pre>
 * for (Posicion pos : Posicion.values()) {
 *     System.out.println(pos);  // Imprime: PORTERIA, DEFENSA, MEDIOCAMP, DELANTERO
 * }
 * </pre>
 *
 * EJEMPLO 5: Obtener el nombre como String
 * <pre>
 * Posicion pos = Posicion.DELANTERO;
 * String nombre = pos.name();        // "DELANTERO"
 * String texto = pos.toString();     // "DELANTERO"
 * </pre>
 *
 * EJEMPLO 6: Convertir String a enum
 * <pre>
 * String texto = "DEFENSA";
 * Posicion pos = Posicion.valueOf(texto);  // Convierte String → enum
 *
 * // CUIDADO: Si el texto no existe, lanza excepción
 * Posicion pos = Posicion.valueOf("ARQUERO");  // ❌ Error! No existe
 * </pre>
 *
 * ═══════════════════════════════════════════════════════════════════════
 * MÉTODOS ÚTILES DE ENUM (los tiene automáticamente)
 * ═══════════════════════════════════════════════════════════════════════
 *
 * - Posicion.values()           → Array con todos los valores [PORTERIA, DEFENSA, ...]
 * - Posicion.valueOf("DEFENSA") → Convierte String a enum
 * - posicion.name()             → Obtiene el nombre como String
 * - posicion.ordinal()          → Obtiene la posición (índice): PORTERIA=0, DEFENSA=1, etc.
 *
 * ═══════════════════════════════════════════════════════════════════════
 * ERRORES COMUNES AL USAR ENUM
 * ═══════════════���═══════════════════════════════════════════════════════
 *
 * ❌ ERROR 1: Intentar crear nuevos valores
 * <pre>
 * Posicion lateral = new Posicion("LATERAL");  // ❌ NO se puede!
 * </pre>
 * Los valores del enum son FIJOS. Si necesitas "LATERAL", debes agregarlo aquí arriba.
 *
 * ❌ ERROR 2: Usar .equals() en vez de ==
 * <pre>
 * if (posicion.equals(Posicion.DEFENSA)) { }  // ✅ Funciona, pero innecesario
 * if (posicion == Posicion.DEFENSA) { }       // ✅ Mejor, más simple
 * </pre>
 * Con enum se recomienda usar == porque solo hay UNA instancia de cada valor.
 *
 * ❌ ERROR 3: Comparar con String directamente
 * <pre>
 * if (jugador.getPosicion() == "DEFENSA") { }  // ❌ NO funciona!
 * </pre>
 * Correcto:
 * <pre>
 * if (jugador.getPosicion() == Posicion.DEFENSA) { }  // ✅
 * </pre>
 *
 * ❌ ERROR 4: Null pointer
 * <pre>
 * Jugador jugador = new Jugador("Messi");
 * if (jugador.getPosicion() == Posicion.DEFENSA) { }  // ❌ NullPointerException!
 * </pre>
 * Si no asignaste posición, es null. Debes validar primero:
 * <pre>
 * if (jugador.getPosicion() != null && jugador.getPosicion() == Posicion.DEFENSA) { }
 * </pre>
 *
 * ═══════════════════════════════════════════════════════════════════════
 * ¿CUÁNDO CREAR TUS PROPIOS ENUMS?
 * ═══════════════════════════════════════════════════════════════════════
 *
 * Crea un enum cuando tengas un conjunto FIJO y PEQUEÑO de valores.
 *
 * BUENOS EJEMPLOS:
 * - enum Posicion { PORTERIA, DEFENSA, MEDIOCAMP, DELANTERO }
 * - enum TipoTarjeta { AMARILLA, ROJA }
 * - enum EstadoPartido { PRIMERA_MITAD, DESCANSO, SEGUNDA_MITAD, FINALIZADO }
 * - enum Resultado { VICTORIA, EMPATE, DERROTA }
 *
 * MALOS EJEMPLOS (NO usar enum):
 * - Nombres de jugadores (hay miles, no es un conjunto fijo)
 * - Dorsal del jugador (puede ser cualquier número)
 * - Minuto del partido (valores numéricos, no categorías)
 *
 * ═══════════════════════════════════════════════════════════════════════
 * PARA TU PROYECTO: OTROS ENUMS QUE PODRÍAS CREAR
 * ═══════════════════════════════════════════════════════════════════════
 *
 * enum TipoTarjeta {
 *     AMARILLA,
 *     ROJA
 * }
 *
 * enum TipoEvento {
 *     GOL,
 *     PENAL,
 *     CORNER,
 *     TIRO_LIBRE,
 *     FALTA,
 *     FUERA_DE_JUEGO,
 *     LESION,
 *     CAMBIO
 * }
 *
 * enum EstadoPartido {
 *     NO_INICIADO,
 *     PRIMERA_MITAD,
 *     DESCANSO,
 *     SEGUNDA_MITAD,
 *     TIEMPO_EXTRA,
 *     PENALES,
 *     FINALIZADO
 * }
 *
 * ═══════════════════════════════════════════════════════════════════════
 * RECURSOS PARA APRENDER MÁS:
 * ═══════════════════════════════════════════════════════════════════════
 * - Oracle Java Enum Tutorial: https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
 * - Baeldung Java Enum Guide: https://www.baeldung.com/a-guide-to-java-enums
 *
 * @author HectorA15
 * @see org.example.entidades.Jugador
 * @see org.example.entidades.Formacion
 */
public enum Posicion {

    /**
     * Posición de portero/arquero/guardameta.
     *
     * Es el único jugador que puede usar las manos dentro del área.
     * Solo puede haber 1 portero por equipo.
     *
     * CARACTERÍSTICAS:
     * - Stats importantes: reflejos, saque
     * - Ubicación: debajo de los postes (la meta)
     * - Dorsal típico: 1, 13, 25
     *
     * USO:
     * <pre>
     * Portero neuer = new Portero("Manuel Neuer");
     * // La posición ya se asigna automáticamente como PORTERIA en el constructor
     * </pre>
     */
    PORTERIA,

    /**
     * Posición de defensa/zaguero/defensor.
     *
     * Jugadores que forman la línea defensiva. Su función principal
     * es evitar que el rival anote.
     *
     * TIPOS:
     * - Defensa central (por el centro)
     * - Lateral derecho/izquierdo (por los costados)
     *
     * CARACTERÍSTICAS:
     * - Stats importantes: defensa, físico
     * - Cantidad típica: 3-5 defensas según formación
     * - Dorsal típico: 2-6, 13-15
     *
     * USO:
     * <pre>
     * Jugador vandijk = new Jugador("Virgil van Dijk");
     * vandijk.setPosicion(Posicion.DEFENSA);
     * </pre>
     */
    DEFENSA,

    /**
     * Posición de mediocampista/volante.
     *
     * Jugadores que conectan defensa y ataque. Controlan el mediocampo
     * y distribuyen el balón.
     *
     * TIPOS:
     * - Mediocentro defensivo (ayuda a la defensa)
     * - Mediocentro (equilibrado)
     * - Mediocentro ofensivo (se proyecta al ataque)
     *
     * CARACTERÍSTICAS:
     * - Stats importantes: pase, defensa (si es defensivo), tiro (si es ofensivo)
     * - Cantidad típica: 3-5 mediocampistas según formación
     * - Dorsal típico: 5-8, 14, 16, 18, 20
     *
     * USO:
     * <pre>
     * Jugador debruyne = new Jugador("Kevin De Bruyne");
     * debruyne.setPosicion(Posicion.MEDIOCAMP);
     * </pre>
     */
    MEDIOCAMP,

    /**
     * Posición de delantero/atacante/ariete.
     *
     * Jugadores cuya función principal es marcar goles.
     *
     * TIPOS:
     * - Delantero centro/9 (por el medio, recibe centros)
     * - Extremo (por las bandas, da centros)
     * - Segundo delantero/mediapunta (entre medio y ataque)
     *
     * CARACTERÍSTICAS:
     * - Stats importantes: tiro, velocidad
     * - Cantidad típica: 1-3 delanteros según formación
     * - Dorsal típico: 7, 9-11, 17, 19, 21
     *
     * USO:
     * <pre>
     * Jugador haaland = new Jugador("Erling Haaland");
     * haaland.setPosicion(Posicion.DELANTERO);
     * </pre>
     */
    DELANTERO
}