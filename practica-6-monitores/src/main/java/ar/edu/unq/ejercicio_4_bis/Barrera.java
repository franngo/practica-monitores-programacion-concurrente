package ar.edu.unq.ejercicio_4_bis;

public class Barrera {
    private int faltantes;

    public Barrera(int cantThreads) {
        faltantes = cantThreads;
    }

    public synchronized void esperar() throws InterruptedException {
        faltantes--;
        while(faltantes > 0) {
            wait();
        }
        notifyAll();
    }
}

//También se podía encarar inicializando faltantes con el int pasado por parámetro y restando hasta que sea 0, como hice acá.
