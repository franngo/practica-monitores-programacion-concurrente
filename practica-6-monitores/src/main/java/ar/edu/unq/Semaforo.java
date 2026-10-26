package ar.edu.unq;

public class Semaforo {
    private int permisos;

    public Semaforo(int permisos) {
        this.permisos = permisos;
    }

    public synchronized void acquire() {
        while(permisos < 1) {
            wait();
        }
        permisos--;
    }

    public synchronized void release() {
        permisos++;
        notify();
    }
}
