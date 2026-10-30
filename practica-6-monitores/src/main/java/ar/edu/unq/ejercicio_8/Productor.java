package ar.edu.unq.ejercicio_8;

public class Productor extends Thread {
    Buffer_N_sol buffer;

    public Productor(Buffer_N_sol buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int n = 1;
        for(int i=0; i<5; i++) {
            buffer.producir(n);
            n++;
        }
    }
}

/*
El esquema del producir sería:
    public void producir() {
        Object x;
        while(true) {
            //x = producirObj();
            //buffer.producir(x);
        }
    }
 */
