package ar.edu.unq.ejercicio_1_bis;

public class Contador {
    private int n = 0;

    public synchronized void incrementar() {
        n++;
    }

    public synchronized void decrementar() {
        n--;
    }
}

//No aporta nada respecto al original. Solo lo hice para practicar. BORRAR.
