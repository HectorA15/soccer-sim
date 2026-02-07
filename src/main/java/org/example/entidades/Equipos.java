package org.example.entidades;

import java.util.ArrayList;
import java.util.List;

public class Equipos {
    private Portero portero;
    private boolean isLocal;
    private String nombre;

    private List<Jugador> jugadores = new ArrayList<>(10);
    private List<Jugador> reserva = new ArrayList<>();

    public Equipos(){

    }


    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setPortero(Portero portero){
        this.portero = portero;
    }
    public Portero getPortero(){
        return portero;
    }

    public void setJugador(Jugador jugador){
        this.jugadores.add(jugador);
    }
    public void setReserva(Jugador jugador){
        this.reserva.add(jugador);
    }
    public void setLocal(boolean isLocal){
        this.isLocal = isLocal;
    }
    public Jugador getJugador(int posicion){
        return jugadores.get(posicion);
    }
    public Jugador getReserva(int posicion){
        return reserva.get(posicion);
    }
    public String getNombre(){
        return nombre;
    }

    public boolean isLocal(){
        return isLocal;
    }

    public Jugador[] getJugadores(){
        return jugadores.toArray(new Jugador[0]);
    }
    public Jugador[] getReserva(){
        return reserva.toArray(new Jugador[0]);
    }

    public boolean isPortero(){
        return portero != null;
    }

    public boolean isFull(){
        return jugadores.size() == 10;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append( "\""+this.nombre+"\"").append("\n\n");
        sb.append(" ".repeat(35) +"Equipo ").append(isLocal ? "Local" : "Visitante").append("\n\n");
        sb.append(String.format("%-25s %5s %5s %5s %5s %5s %5s %5s %5s", "Nombre", "Vel", "Tiro", "Pase", "Def", "Fis", "Saq", "Ref", "Pos\n\n"));
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
