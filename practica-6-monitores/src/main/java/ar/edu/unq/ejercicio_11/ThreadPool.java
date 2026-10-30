package ar.edu.unq.ejercicio_11;

import ar.edu.unq.ejercicio_8.Buffer_N_sol;

import java.util.ArrayList;

public class ThreadPool {
    Buffer_N_sol buffer; //buffer donde se van a cargar las tareas/tasks
    //ArrayList<Worker> workers = new ArrayList<Worker>(); Creo que no hace falta guardarlos acá, ¿no? Simplemente los creo y ellos ya tienen el buffer
    int cantWorkers;

    public ThreadPool(int bufferSize, int cantWorkers) {
        this.buffer = new Buffer_N_sol(bufferSize);
        this.cantWorkers = cantWorkers;
        for(int i=0; i<cantWorkers; i++) {
            Worker worker = new Worker(buffer);
            worker.start();
        }
    }

    public synchronized void launch(Runnable tarea) {
        buffer.producir(tarea);
        //el notify() para avisar a un posible worker consumidor de tareas bloqueado ya se da en el producir del buffer
    }

    public synchronized void stop() {
        for(int i=0; i<this.cantWorkers; i++) {
            buffer.producir(new PoisonPill());
        }
    }
}
