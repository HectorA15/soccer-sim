package org.example.entidades;

public class Formacion {
    String nombreFormacion;
    int defensas;
    int mediocampistas;
    int delanteros;

    public Formacion(int defensas, int mediocampistas, int delanteros){
        this.defensas = defensas;
        this.mediocampistas = mediocampistas;
        this.delanteros = delanteros;
        this.nombreFormacion = defensas + "-" + mediocampistas + "-" + delanteros;
    }

    public String getNombreFormacion(){return nombreFormacion;}
    public int getDefensas(){return defensas;}
    public int getMediocampistas(){return mediocampistas;}
    public int getDelanteros(){return delanteros;}


}
