package org.example.entidades;

import org.example.enums.Posicion;

public class Portero extends Jugador {
    private int saque;
    private int reflejos;

    public Portero(String nombre){
        super(nombre);
        setPosicion(Posicion.PORTERIA);
    }

    public int getSaque(){
        return saque;
    }
    public int getReflejos(){
        return reflejos;
    }
    @Override
    public void setRandomStats(){
        super.setRandomStats();
        saque           = 1 + (int)(Math.random() * 100);
        reflejos        = 1 + (int)(Math.random() * 100);
    }


    public String toString() {
        return String.format("%s %5d %5d", super.toString(), saque, reflejos);
    }
}
