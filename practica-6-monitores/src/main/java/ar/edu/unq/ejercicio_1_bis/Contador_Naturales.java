package ar.edu.unq.ejercicio_1_bis;

public class Contador_Naturales {
    private int n = 0;

    public synchronized void incrementar() {
        n++;
        notify();
    }

    public synchronized void decrementar() throws InterruptedException {
        while(!puedeDecrementar()) {
            wait();
        }
        n--;
    }

    private boolean puedeDecrementar() {
        return n > 0;
    }
}
