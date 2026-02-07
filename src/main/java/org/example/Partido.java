package org.example;

import org.example.entidades.Equipos;

public class Partido {
    private Equipos local;
    private Equipos visitante;
    private int minutoActual;
    private int golesLocal;
    private int golesVisitante;
    private Eventos eventos;

    public Partido(Equipos local, Equipos visitante) {
        this.local = local;
        this.visitante = visitante;
        this.eventos = new Eventos();
        this.minutoActual = 0;
    }

    public void simular() {
        // Lógica principal: iterar minutos, decidir eventos, actualizar estado
    }

    private void procesarMinuto(int minuto) {
        // Aquí decides si ocurre tiro, pase, falta, etc.

    }
}