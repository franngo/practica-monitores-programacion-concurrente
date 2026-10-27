package ar.edu.unq.ejercicio_1;

public class Contador_Naturales {
    private int numero = 0;

    public synchronized void incrementar () {
        numero++;
        notify();
    }

    public synchronized void decrementar() {
        while(!puedeDecrementar()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        numero--;
    }

    private boolean puedeDecrementar() {
        return numero > 0;
    }
}
