package org.example.util;

import org.example.entidades.Equipos;

import java.util.ArrayList;

public class GestorMercado {

    // muy largo de calcular, lo hice una constante
    private static final double GOLES_BASE_LIGA = 1.3;

    // atributos
    private double cuotaLocal;
    private double cuotaVisitante;
    private double cuotaEmpate;

    // Constructor
    public GestorMercado() {
        this.cuotaLocal = 1.0;
        this.cuotaEmpate = 1.0;
        this.cuotaVisitante = 1.0;
    }

    public void actualizarCuotas(int minuto, int golesLocalActuales, int golesVisitaActuales, Equipos equipoLocal, Equipos equipoVisitante) {
        int minutosRestantes = 90 - minuto;
        double factorTiempo = (double) minutosRestantes / 90.0;

        // Si ya se acabó el partido, evitamos negativos
        if (factorTiempo < 0) factorTiempo = 0;


        // calculamos lambdas para ambos equipos, que es el numero de goles que esperamos que meta cada equipo en el tiempo restante
        double lambdaLocal = calcularLambda(equipoLocal, equipoVisitante, factorTiempo);
        double lambdaVisitante = calcularLambda(equipoVisitante, equipoLocal, factorTiempo);

        // calculamos las probabilidades de que cada equipo meta exactamente k goles en el tiempo restante usando la formula de poisson
        ArrayList<Double> probsLocal = calcularProbabilidadPoisson(lambdaLocal);
        ArrayList<Double> probsVisitante = calcularProbabilidadPoisson(lambdaVisitante);

        // Suma de Probabilidades
        double sumaVictoriaLocal = 0.0;
        double sumaEmpate = 0.0;
        double sumaVictoriaVisita = 0.0;



        for (int i = 0; i < probsLocal.size(); i++) {
            for (int j = 0; j < probsVisitante.size(); j++) {

                double probEscenario = probsLocal.get(i) * probsVisitante.get(j);

                // El resultado final es lo que ya llevan + lo que van a meter
                int marcadorFinalLocal = golesLocalActuales + i;
                int marcadorFinalVisita = golesVisitaActuales + j;

                if (marcadorFinalLocal > marcadorFinalVisita) {
                    sumaVictoriaLocal += probEscenario;
                } else if (marcadorFinalLocal == marcadorFinalVisita) {
                    sumaEmpate += probEscenario;
                } else {
                    sumaVictoriaVisita += probEscenario;
                }
            }
        }

        double margen = 0.90; // El casino se queda con el 10%
        this.cuotaLocal = margen / Math.max(sumaVictoriaLocal, 0.0001);
        this.cuotaEmpate = margen / Math.max(sumaEmpate, 0.0001);
        this.cuotaVisitante = margen / Math.max(sumaVictoriaVisita, 0.0001);
    }

    /**
     * Lambda es el numero que se le entrega a la formula de poisson.
     * Lambda es el número de goles que esperamos que meta un equipo en el tiempo que queda
     */

    private double calcularLambda(Equipos local, Equipos visita, double  factorTiempo) {

        double ataque = local.getPoderOfensivo();
        double defensa = visita.getPoderDefensivo();

        // para evitar division por cero
        if (defensa == 0) defensa = 1;

        /* Cuanto mayor sea el ataque del equipo local en comparación con la defensa del visitante, mayor será la ventaja
         y, por lo tanto, el número esperado de goles (lambda) aumentará. La potencia de 1.3 se utiliza para amplificar
         esta ventaja, haciendo que las diferencias en ataque y defensa tengan un impacto más significativo en el resultado final.
        */
        double ratioVentaja = Math.pow(ataque / defensa, 1.3);

        // Lambda final ajustado al tiempo
        return GOLES_BASE_LIGA * ratioVentaja * factorTiempo;

    }


    //aplicamos la formula de poisson para calcular la probabilidad de que el equipo meta exactamente k goles en el tiempo restante
    private ArrayList<Double> calcularProbabilidadPoisson(double lambda) {
        ArrayList probabilidades = new ArrayList<>();

        for (int k = 0; k <= 10; k++){
            double probabilidad = (Math.pow(lambda, k)) * (Math.exp(-lambda)) / factorial(k);
            probabilidades.add(probabilidad);
        }

        return probabilidades;
    }


    // Funcon para calcular el factorial de un numero, necesario para la formula de poisson
    private double factorial(int n) {
        if (n == 0) return 1;
        double resultado = 1;
        for (int i = 1; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }



    // ======== GETTERS ========

    public String getCuotaLocal() {
        return String.format("%.2f", cuotaLocal);
    }

    public String getCuotaEmpate() {
        return String.format("%.2f", cuotaEmpate);
    }

    public String getCuotaVisitante() {
        return String.format("%.2f", cuotaVisitante);
    }
}
