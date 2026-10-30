package ar.edu.unq.ejercicio_11;

public class PoisonPill implements Runnable {
    @Override
    public void run() {
        throw new PoisonException();
    }
}
