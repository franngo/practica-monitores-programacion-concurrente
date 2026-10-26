package ar.edu.unq.ejercicio_8;

public class Buffer_uno {
    Object buffer = null;

    public synchronized Object consumir() throws InterruptedException {
        while(buffer == null) {
            wait();
        }
        Object aDevolver = buffer;
        buffer = null;
        notifyAll();
        return aDevolver;
    }

    public synchronized void producir(Object dato) throws InterruptedException {
        while(buffer != null) {
            wait();
        }
        buffer = dato;
        notifyAll();
    }
}
