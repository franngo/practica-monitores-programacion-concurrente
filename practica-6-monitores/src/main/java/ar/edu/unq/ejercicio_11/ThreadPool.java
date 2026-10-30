package ar.edu.unq.ejercicio_11;

import ar.edu.unq.ejercicio_8.Buffer_N_sol;

public class ThreadPool {
    Buffer_N_sol buffer; //buffer donde se van a cargar las tareas/tasks
    int cantWorkers; //la cantidad de threads Workers que se inicializan para atender las tareas que se envian.

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

    /*
    Acá llevamos a cabo un Poison Pill Pattern.
    Producimos cantWorkers veces la tarea PoisonPill, de modo que los cantWorkers workers la tomen, la ejecuten y finalicen su ejecución.
    No se va a poder dar que uno la tome dos veces, porque, al tomar una de estas, el Worker finaliza su ejecución y ya no toma más tareas.
    Por tanto, las cantWorkers PoisonPills van a alcanzar para matar a todos los Workers que fueron iniciados.
    De esta forma, terminamos el ciclo de vida de los Workers de forma controlada y no de forma abrupta.
     */
    public synchronized void stop() {
        for(int i=0; i<this.cantWorkers; i++) {
            buffer.producir(new PoisonPill());
        }
    }
}
