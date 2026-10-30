package ar.edu.unq.ejercicio_11;

import ar.edu.unq.ejercicio_8.Buffer_N_sol;

import java.util.ArrayList;

public class ThreadPool {
    Buffer_N_sol buffer;
    ArrayList<Worker> workers = new ArrayList<Worker>();

    public ThreadPool(int bufferSize, int cantWorkers) {
        buffer = new Buffer_N_sol(bufferSize);
        for(int i=0; i<cantWorkers; i++) {
            workers.add(new Worker(buffer));
        }
    }
}
