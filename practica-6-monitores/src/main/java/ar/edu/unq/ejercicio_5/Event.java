package ar.edu.unq.ejercicio_5;

public class Event {
    private int publicaciones = 0;

    public synchronized void publish() {
        publicaciones++;
        notifyAll();
    }

    public synchronized void subscribe() {
        int nroPublicacion = publicaciones + 1;
        while(!esSuPublicacion(nroPublicacion)) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean esSuPublicacion(int nroPublicacion) {
        return nroPublicacion == publicaciones;
    }
}
