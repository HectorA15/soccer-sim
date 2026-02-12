package org.example.entidades;

import org.example.enums.Posicion;
import org.example.nombres.JugadoresNombres;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa un equipo de fútbol completo.
 * Un equipo tiene: 1 portero, 10 jugadores titulares, suplentes y una formación.
 */
public class Equipos {
    private int goles;
    private Portero portero;
    private boolean isLocal;
    private String nombre;
    private int tarjetasAmarillas;
    private int tarjetasRojas;
    private Formacion formacion;
    private final List<Jugador> jugadores = new ArrayList<>(10);
    private final List<Jugador> reserva = new ArrayList<>();

    /**
     * Contador de cambios realizados durante el partido.
     * Se incrementa cada vez que se hace una sustitución exitosa.
     * Se usa para validar el límite de MAX_CAMBIOS.
     */
    private int cambiosRealizados = 0;

    /**
     * Límite máximo de cambios permitidos por partido según reglas FIFA.
     * Nota: En algunas competiciones puede variar (ej: 3 cambios en vez de 5).
     */
    private static final int MAX_CAMBIOS = 5;

    public Equipos() {
    }

    public Equipos(String nombre) {
        this.nombre = nombre;
        inicializarEquipo();
    }

    /**
     * Inicializa el equipo con portero y 10 jugadores titulares + 5 suplentes.
     * Se llama automáticamente en el constructor.
     */
    private void inicializarEquipo() {
        // Crear portero
        Portero nuevoPortero = new Portero("Portero del " + nombre);
        nuevoPortero.setRandomStats();
        this.portero = nuevoPortero;

        // Crear 10 jugadores titulares
        for (int i = 0; i < 10; i++) {
            String nombreJugador = JugadoresNombres.getNombreAleatorio();
            Jugador jugador = new Jugador(nombreJugador);
            jugador.setRandomStats();
            this.jugadores.add(jugador);
        }

        // Crear 5 jugadores en la banca (suplentes)
        for (int i = 0; i < 5; i++) {
            String nombreJugador = JugadoresNombres.getNombreAleatorio();
            Jugador jugador = new Jugador(nombreJugador);
            jugador.setRandomStats();
            this.reserva.add(jugador);
        }

        // Establecer formación por defecto
        this.formacion = new Formacion(4, 4, 2);
        asignarPosiciones();
    }

    public Portero getPortero() {
        return portero;
    }

    public Formacion getFormacion() {
        return formacion;
    }

    /**
     * Asigna la formación y valida que sea correcta.
     *
     * @throws IllegalArgumentException si la suma de defensas+medios+delanteros != 10
     */
    public void setFormacion(Formacion formacion) {
        if (formacion != null && !formacion.isValida()) {
            throw new IllegalArgumentException("Formación inválida: debe sumar 10 jugadores");
        }
        this.formacion = formacion;
    }

    /**
     * Agrega un jugador a la lista de titulares.
     * <p>
     * IMPORTANTE: Solo permite 10 jugadores titulares (sin contar portero).
     * Si el equipo ya está lleno, debes usar setReserva() en su lugar.
     * <p>
     * Ejemplo de uso:
     * <pre>
     * if (!equipo.setJugador(nuevoJugador)) {
     *     equipo.setReserva(nuevoJugador);  // Va a la banca
     * }
     * </pre>
     *
     * @return true si se agregó, false si ya hay 10 titulares
     */
    public boolean setJugador(Jugador jugador) {
        if (jugadores.size() >= 10) {
            return false;
        }
        this.jugadores.add(jugador);
        return true;
    }

    public int setTarjetasAmarillas() {
        return tarjetasAmarillas++;
    }
    public int setTarjetasRojas() {
        return tarjetasRojas++;
    }


    public Jugador getJugadorRandom() {
        return jugadores.get((int) (Math.random() * jugadores.size()));
    }

    public Jugador getJugador(int posicion) {
        return jugadores.get(posicion);
    }

    public Jugador getReserva(int posicion) {
        return reserva.get(posicion);
    }

    public String getNombre() {
        return nombre;
    }

    public int getTarjetasAmarillas() {
        return tarjetasAmarillas;
    }
    public int getTarjetasRojas() {
        return tarjetasRojas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    /**
     * Devuelve una copia de los jugadores titulares como array.
     * <p>
     * NOTA: Es una copia, no la lista original. Si modificas el array
     * devuelto, NO afecta al equipo. Esto previene bugs accidentales.
     */
    public Jugador[] getJugadores() {
        return jugadores.toArray(new Jugador[0]);
    }

    public Jugador[] getReserva() {
        return reserva.toArray(new Jugador[0]);
    }

    public void setReserva(Jugador jugador) {
        this.reserva.add(jugador);
    }

    public boolean isPortero() {
        return portero != null;
    }

    /**
     * Asigna el portero.
     * ADVERTENCIA: Si ya existe un portero, lo sobrescribe sin avisar.
     * Usa isPortero() primero si quieres validar.
     */
    public void setPortero(Portero portero) {
        this.portero = portero;
    }

    /**
     * Verifica si el equipo está completo (portero + 10 jugadores).
     */
    public boolean isFull() {
        return portero != null && jugadores.size() == 10;
    }

    /**
     * Asigna posiciones según la formación EN ORDEN SECUENCIAL.
     * <p>
     * Cómo funciona:
     * 1. Los primeros N jugadores de la lista → DEFENSA
     * 2. Los siguientes M jugadores → MEDIOCAMP
     * 3. Los últimos K jugadores → DELANTERO
     * <p>
     * Donde N, M, K vienen de la formación (ej: 4-4-2 → N=4, M=4, K=2)
     * <p>
     * IMPORTANTE: Siempre asigna en el mismo orden. Si quieres variedad,
     * usa asignarPosicionesAleatorias() en su lugar.
     */
    public void asignarPosiciones() {
        if (formacion == null) {
            for (Jugador jugador : jugadores) {
                jugador.setPosicion(null);
            }
            return;
        }

        int index = 0;

        // Asignar defensas
        for (int i = 0; i < formacion.getDefensas() && index < jugadores.size(); i++, index++) {
            jugadores.get(index).setPosicion(Posicion.DEFENSA);
        }

        // Asignar mediocampistas
        for (int i = 0; i < formacion.getMediocampistas() && index < jugadores.size(); i++, index++) {
            jugadores.get(index).setPosicion(Posicion.MEDIOCAMP);
        }

        // Asignar delanteros
        for (int i = 0; i < formacion.getDelanteros() && index < jugadores.size(); i++, index++) {
            jugadores.get(index).setPosicion(Posicion.DELANTERO);
        }
    }

    /**
     * Versión ALEATORIA de asignarPosiciones().
     * <p>
     * Diferencia clave:
     * - asignarPosiciones() → Siempre el mismo orden (predecible)
     * - asignarPosicionesAleatorias() → Mezcla jugadores primero (más realista)
     * <p>
     * Útil para que en cada simulación, jugadores con diferentes stats
     * ocupen diferentes posiciones.
     */
    public void asignarPosicionesAleatorias() {
        if (formacion == null) {
            for (Jugador jugador : jugadores) {
                jugador.setPosicion(null);
            }
            return;
        }

        Collections.shuffle(jugadores);  // ESTA es la diferencia
        asignarPosiciones();
    }

    /**
     * Realiza una sustitución de jugadores durante el partido.
     * <p>
     * VALIDACIONES que aplica:
     * - Máximo 5 cambios por partido (regla FIFA)
     * - El jugador que sale debe estar en titulares
     * - El jugador que entra debe estar en reserva
     * <p>
     * Cómo funciona:
     * 1. Verifica que no se hayan alcanzado los MAX_CAMBIOS (5)
     * 2. Confirma que 'sale' está en la lista de titulares
     * 3. Confirma que 'entra' está en la lista de reserva
     * 4. Intercambia sus posiciones: 'sale' va a reserva, 'entra' a titulares
     * 5. Incrementa el contador de cambios
     * <p>
     * IMPORTANTE: Después de un cambio exitoso, es recomendable llamar
     * asignarPosiciones() si quieres que el jugador entrante tome
     * la posición correcta según la formación actual.
     * <p>
     * NOTA: No valida si un jugador está expulsado (tarjeta roja).
     * Esa lógica debe implementarse en otra parte (ej: clase Jugador
     * con un estado isExpulsado).
     * <p>
     * Ejemplo de uso:
     * <pre>
     * Jugador cansado = equipo.getJugador(5);
     * Jugador fresco = equipo.getReserva(0);
     *
     * if (equipo.cambio(cansado, fresco)) {
     *     System.out.println("Cambio exitoso");
     *     equipo.asignarPosiciones();  // Actualizar posiciones
     * } else {
     *     System.out.println("Cambio inválido o sin cambios disponibles");
     * }
     * </pre>
     *
     * @param sale  jugador que sale de la cancha (debe estar en titulares)
     * @param entra jugador que entra a la cancha (debe estar en reserva)
     * @return true si el cambio se realizó exitosamente, false si no cumple validaciones
     */
    public boolean cambio(Jugador sale, Jugador entra) {

        if (cambiosRealizados >= MAX_CAMBIOS) {
            return false;
        }

        if (!jugadores.contains(sale)) {
            return false;
        }

        if (!reserva.contains(entra)) {
            return false;
        }

        jugadores.remove(sale);
        reserva.remove(entra);

        jugadores.add(entra);
        reserva.add(sale);

        cambiosRealizados++;

        return true;
    }

    /**
     * Reasigna las posiciones de los jugadores según la formación actual.
     * <p>
     * CUÁNDO ES ÚTIL:
     * - Después de un cambio de jugador (para que el sustituto tome posición)
     * - Cuando el entrenador cambia de formación táctica (ver setFormacion())
     * - Para reorganizar posiciones sin modificar la lista de titulares
     * <p>
     * Cómo funciona:
     * 1. Verifica que exista una formación asignada
     * 2. Llama a asignarPosiciones() para redistribuir roles
     * <p>
     * IMPORTANTE: Este metdo NO cambia la formación, solo reasigna posiciones.
     * Si quieres cambiar de formación (ej: de 4-4-2 a 5-3-2), debes llamar
     * primero a setFormacion() y LUEGO a cambioPosiciones().
     * <p>
     * Ejemplo de uso estratégico:
     * <pre>
     * // Escenario: Vas perdiendo 2-0 al minuto 70
     * equipo.setFormacion(new Formacion(3, 3, 4));  // Formación ofensiva
     * equipo.cambioPosiciones();                     // Aplica cambios
     *
     * // Escenario: Vas ganando 1-0 al minuto 85
     * equipo.setFormacion(new Formacion(5, 4, 1));  // Formación defensiva
     * equipo.cambioPosiciones();                     // Aplica cambios
     * </pre>
     *
     * @return true si se reasignaron las posiciones, false si no hay formación definida
     */
    public boolean cambioPosiciones() {

        if (formacion == null) {
            return false;
        }

        asignarPosiciones();
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(this.nombre).append("\"").append("\n");

        if (formacion != null) {
            sb.append("Formación: ").append(formacion).append("\n");
        }

        sb.append("\n");
        sb.append(" ".repeat(35)).append("Equipo ")
                .append(isLocal ? "Local" : "Visitante").append("\n\n");

        sb.append("PORTERO\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %5s %5s %10s\n\n",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Saq", "Ref", "Posición"));
        sb.append(portero).append("\n\n");

        sb.append("JUGADORES\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %10s\n\n",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Posición"));

        for (Jugador jugador : jugadores) {
            sb.append(jugador).append("\n");
        }

        sb.append("\nRESERVA\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %10s\n\n",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Posición"));

        for (Jugador jugador : reserva) {
            sb.append(jugador).append("\n");
        }

        return sb.toString();
    }
}