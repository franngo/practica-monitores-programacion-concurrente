package ar.edu.unq.ejercicio_8;

public class Consumidor extends Thread {
    Buffer_N_sol buffer;

    public Consumidor(Buffer_N_sol buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Object x;
        for(int i=0; i<5; i++) {
            x = buffer.consumir();
            System.out.println(x);
        }
    }
}

/*
El esquema del consumir sería:
    public void consumir() {
        Object x;
        while(true) {
            //x = buffer.consumir();
            //consumirObj(x);
        }
    }
 */
