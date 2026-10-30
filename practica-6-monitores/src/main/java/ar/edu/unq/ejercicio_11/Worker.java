package ar.edu.unq.ejercicio_11;

import ar.edu.unq.ejercicio_8.Buffer_N_sol;

public class Worker extends Thread {
    private Buffer_N_sol buffer;

    public Worker(Buffer_N_sol buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
//        Object x;
//        for(int i=0; i<5; i++) {
//            x = buffer.consumir();
//            System.out.println(x);
//        }
    }
}
