package ar.edu.unq.ejercicio_1;

public class Contador {
    private int numero = 0;

    public synchronized void incrementar () {
        numero++;
    }

    public synchronized void decrementar() {
        numero--;
    }
}
//Es así de simple porque no hay ninguna condición de espera, por lo que no hay que dormir a nadie, y, entonces, tampoco hay que despertar nunca a nadie.
