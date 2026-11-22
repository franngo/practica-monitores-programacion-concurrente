package ar.edu.unq.ejercicio_2_bis;

public class Semaforo {
    private int releases;

    public Semaforo(int releases) {
        this.releases = releases;
    }

    public synchronized void acquire() throws InterruptedException {
        while(!puedeAcquire()) {
            wait();
        }
        releases--;
    }

    public synchronized void release() {
        releases++;
        notify();
    }

    private boolean puedeAcquire() {
        return releases > 0;
    }
}
