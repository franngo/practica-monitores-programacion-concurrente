package ar.edu.unq.ejercicio_3_bis;

public class Secuenciador_Ternario {
    private int numeroSecuencia = 3;

    public synchronized void primero() throws InterruptedException {
        while(!puedePrimero()) {
            wait();
        }
        numeroSecuencia = 1;
        notifyAll();
    }

    public synchronized void segundo() throws InterruptedException {
        while(!puedeSegundo()) {
            wait();
        }
        numeroSecuencia = 2;
        notifyAll();
    }

    public synchronized void tercero() throws InterruptedException {
        while(!puedeTercero()) {
            wait();
        }
        numeroSecuencia = 3;
        notifyAll();
    }

    private boolean puedePrimero() {
        return numeroSecuencia == 3;
    }

    private boolean puedeSegundo() {
        return numeroSecuencia == 1;
    }

    private boolean puedeTercero() {
        return numeroSecuencia == 2;
    }
}
