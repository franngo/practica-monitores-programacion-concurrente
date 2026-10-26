package ar.edu.unq;

public class Buffer_uno {
    Object buffer = null;

    public synchronized Object consumir() {
        while(buffer == null) {
            wait();
        }
        Object aDevolver = buffer;
        buffer = null;
        notifyAll();
        return aDevolver;
    }

    public synchronized void producir(Object dato) {
        while(buffer != null) {
            wait();
        }
        buffer = dato;
        notifyAll();
    }
}
