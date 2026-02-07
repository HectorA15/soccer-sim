package org.example.entidades;

import org.example.enums.Posicion;

import java.util.*;

public class Equipos {
    private Portero portero;
    private boolean isLocal;
    private String nombre;
    private Formacion formacion;

    private List<Jugador> jugadores = new ArrayList<>(10);
    private List<Jugador> reserva = new ArrayList<>();

    private final Map<Jugador, Posicion> posicionesAsignadas = new LinkedHashMap<>();

    public Equipos(){

    }


    public void setNombre(String nombre){this.nombre = nombre;}
    public void setPortero(Portero portero){this.portero = portero;}
    public void setJugador(Jugador jugador){this.jugadores.add(jugador);}
    public void setReserva(Jugador jugador){this.reserva.add(jugador);}
    public void setLocal(boolean isLocal){this.isLocal = isLocal;}
    public void setFormacion(Formacion formacion){this.formacion = formacion;}

    public String getNombre(){return nombre;}
    public Portero getPortero(){return portero;}
    public Jugador getJugador(int posicion){return jugadores.get(posicion);}
    public Jugador getReserva(int posicion){return reserva.get(posicion);}
    public Formacion getFormacion(){return formacion;}

    public Jugador[] getJugadores(){return jugadores.toArray(new Jugador[0]);}
    public Jugador[] getReserva(){return reserva.toArray(new Jugador[0]);}

    public boolean isPortero(){return portero != null;}
    public boolean isFull(){return jugadores.size() == 10;}
    public boolean isLocal(){return isLocal;}

    public void asignarPosiciones(Random random){

        int defensas = formacion.getDefensas();
        int mediocampistas = formacion.getMediocampistas();
        int delanteros = formacion.getDelanteros();

        if(defensas + mediocampistas + delanteros != 10){
            return;
        }

        for (int i = 0; i < defensas; i++) {
            jugadores.get(i).setPosicion(Posicion.DEFENSA);
        }
        for (int i = defensas; i < defensas + mediocampistas; i++) {
            jugadores.get(i).setPosicion(Posicion.MEDIOCAMP);
        }
        for (int i = defensas + mediocampistas; i < jugadores.size(); i++) {
            jugadores.get(i).setPosicion(Posicion.DELANTERO);
        }
    }

    public Posicion getPosicionAsignada(Jugador jugador){
        return posicionesAsignadas.get(jugador);
    }

    public Map<Jugador, Posicion> getPosicionesAsignadas() {
        return Map.copyOf(posicionesAsignadas);
    }

    public void setPosicion(Jugador jugador, Posicion posicion){
        posicionesAsignadas.put(jugador, posicion);
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append( "\""+this.nombre+"\"").append("\n\n");
        sb.append(" ".repeat(35) +"Equipo ").append(isLocal ? "Local" : "Visitante").append("\n\n");
        sb.append(String.format("%-25s %10s %5s %5s %5s %5s %5s %5s %5s %5s", "Nombre", "Posicion", "Vel", "Tiro", "Pase", "Def", "Fis", "Saq", "Ref", "Pos\n\n"));
        sb.append("PORTERO").append("\n");
        sb.append(portero).append("\n\n");

        sb.append("JUGADORES").append("\n");
        for (Jugador jugador : jugadores) {
            sb.append(jugador).append("\n");
        }
        sb.append("\n");
        sb.append("RESERVA").append("\n");
        for (Jugador jugador : reserva) {
            sb.append(jugador).append("\n");
        }
        return sb.toString();
    }

}
