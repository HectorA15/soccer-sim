package org.example.entidades;

import org.example.enums.Posicion;

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
    private Formacion formacion;
    private final List<Jugador> jugadores = new ArrayList<>(10);
    private final List<Jugador> reserva = new ArrayList<>();
    private int cambiosRealizados = 0;
    private static final int MAX_CAMBIOS = 5;

    public Equipos() {
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
     * Realiza un cambio (sustitución).
     *
     * VALIDACIONES:
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
        // Validar que no se ha excedido el máximo de cambios
        if (cambiosRealizados >= MAX_CAMBIOS) {
            return false;
        }
        
        // Validar que el jugador que sale está en titulares
        if (!jugadores.contains(sale)) {
            return false;
        }
        
        // Validar que el jugador que entra está en reserva
        if (!reserva.contains(entra)) {
            return false;
        }
        
        // Guardar la posición del jugador que sale
        Posicion posicionAnterior = sale.getPosicion();
        
        // Realizar el cambio
        jugadores.remove(sale);
        reserva.remove(entra);
        
        jugadores.add(entra);
        reserva.add(sale);
        
        // Asignar la misma posición al jugador que entra
        entra.setPosicion(posicionAnterior);
        
        // Incrementar contador de cambios
        cambiosRealizados++;
        
        return true;
    }

    /**
     * Expulsa a un jugador por tarjeta roja.
     * El jugador es removido del campo y NO puede ser reemplazado.
     * El equipo queda con menos jugadores.
     *
     * @param jugador el jugador expulsado
     * @return true si el jugador fue expulsado, false si no estaba en titulares
     */
    public boolean expulsarJugador(Jugador jugador) {
        if (!jugadores.contains(jugador)) {
            return false;
        }
        
        // Remover del campo sin agregar a reserva
        jugadores.remove(jugador);
        return true;
    }

    /**
     * Realiza una sustitución por lesión automática.
     * Busca un suplente de la misma posición si es posible.
     * Si no hay suplente de la misma posición, busca cualquier suplente.
     *
     * @param lesionado el jugador lesionado
     * @return true si se realizó el cambio, false si no había suplentes disponibles
     */
    public boolean sustituirPorLesion(Jugador lesionado) {
        if (!jugadores.contains(lesionado)) {
            return false;
        }
        
        if (reserva.isEmpty()) {
            return false;
        }
        
        // Intentar encontrar un suplente de la misma posición
        Posicion posicionLesionado = lesionado.getPosicion();
        Jugador sustituto = null;
        
        if (posicionLesionado != null) {
            for (Jugador r : reserva) {
                if (r.getPosicion() == posicionLesionado) {
                    sustituto = r;
                    break;
                }
            }
        }
        
        // Si no hay suplente de la misma posición, tomar el primero disponible
        if (sustituto == null) {
            sustituto = reserva.get(0);
        }
        
        // Realizar el cambio
        return cambio(lesionado, sustituto);
    }

    public int getCambiosRealizados() {
        return cambiosRealizados;
    }

    public int getCambiosDisponibles() {
        return MAX_CAMBIOS - cambiosRealizados;
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
     * 2. Reasignar posiciones de los jugadores existentes en la lista titulares
     *
     * NOTA: Similar a cambio(), este metdo necesita acceso al Equipo.
     * Considera moverlo a Equipos.java o agregarlo en Partido.java
     *
     * INTENTA NO USAR CHATGPT HAZLO COMO PUEDAS, A TU FORMA, PERO AVISA SI VAS A MODIFICAR OTRAS CLASES Y EL QUE
     *
     * @return true si se realizó el cambio
     */
    public boolean cambioPosiciones() {
        // TODO:
        return false;
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

        // Header para PORTERO
        sb.append("PORTERO").append("\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %5s %5s %10s\n\n",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Saq", "Ref", "Posición"));
        sb.append(portero).append("\n\n");

        // Header para JUGADORES
        sb.append("JUGADORES").append("\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %10s\n\n",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Posición"));
        for (Jugador jugador : jugadores) {
            sb.append(jugador).append("\n");
        }

        sb.append("\n");
        sb.append("RESERVA").append("\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %10s\n\n",
                "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Posición"));
        for (Jugador jugador : reserva) {
            sb.append(jugador).append("\n");
        }
        return sb.toString();
    }
}