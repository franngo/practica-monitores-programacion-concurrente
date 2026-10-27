package ar.edu.unq.ejercicio_4;

public class Barrera {
    private int n;
    private int esperando = 0;

    public Barrera(int n) {
        this.n = n;
    }

    public synchronized void esperar() {
        esperando++;
        while(debeEsperar()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        notifyAll();
    }

    private boolean debeEsperar() {
        return esperando < n;
    }
}

/*
Tecnicamente, el único notifyAll() que se necesita es el primero, el del thread que se salta el while por 1ra vez. El notifyAll del resto de threads despertados
es innecesario, aunque no afecta en nada realmente, pues un notify sobre un wait set vacío no afecta en nada.

Otra opción sería hacer el notifyAll antes de entrar al while si se da que sos el thread N.
    public synchronized void esperar() {
        esperando++;

        if(esperando==n) {
          notifyAll();
        }

        while(debeEsperar()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
 */
