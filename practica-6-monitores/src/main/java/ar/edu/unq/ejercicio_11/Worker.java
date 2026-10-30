package ar.edu.unq.ejercicio_11;

import ar.edu.unq.ejercicio_8.Buffer_N_sol;

public class Worker extends Thread {
    private Buffer_N_sol buffer;

    public Worker(Buffer_N_sol buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Runnable task;
        while(true) {
            task = (Runnable) buffer.consumir();
            task.run(); //ejecutar la task en cuestión
        }
    }
}
