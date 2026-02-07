package org.example.entidades;

public class Jugador {

    private final String nombre;

    private int velocidad;
    private int tiro;
    private int pase;
    private int defensa;
    private int fisico;

    public Jugador(String nombre){
        this.nombre = nombre;
    }

    public Jugador(String nombre, int velocidad, int tiro, int pase, int defensa, int fisico){
        this.nombre = nombre;
        this.velocidad = velocidad;
        this.tiro = tiro;
        this.pase = pase;
        this.defensa = defensa;
        this.fisico = fisico;
    }



    // --- setters protegidos para que Portero pueda modificar stats sin tocar fields private ---
    protected void setVelocidad(int velocidad) { this.velocidad = velocidad; }
    protected void setTiro(int tiro) { this.tiro = tiro; }
    protected void setPase(int pase) { this.pase = pase; }
    protected void setDefensa(int defensa) { this.defensa = defensa; }
    protected void setFisico(int fisico) { this.fisico = fisico; }

    //getters
    public String getNombre() { return nombre; }
    public int getVelocidad() { return velocidad; }
    public int getTiro() { return tiro; }
    public int getPase() { return pase; }
    public int getDefensa() { return defensa; }
    public int getFisico() { return fisico; }


    //Establece las estadisticas del jugador aleatoriamente (1-100)
    public void setRandomStats(){
        setVelocidad(1 + (int)(Math.random() * 100));
        setTiro(1 + (int)(Math.random() * 100));
        setPase(1 + (int)(Math.random() * 100));
        setDefensa(1 + (int)(Math.random() * 100));
        setFisico(1 + (int)(Math.random() * 100));
    }

    public String getStats(){
        return String.format("%-25s %5d %5d %5d %5d %5d",
                nombre, velocidad, tiro, pase, defensa, fisico);
    }

    @Override
    public String toString() {
        return getStats();
    }
}
