package ar.edu.unq.ejercicio_2;

public class Semaforo {
    private int permisos;

    public Semaforo(int permisos) {
        this.permisos = permisos;
    }

    public synchronized void acquire() throws InterruptedException {
        while(!puedeAcquire()) {
            wait();
        }
        permisos--;
    }

    public synchronized void release() {
        permisos++;
        notify();
    }

    private boolean puedeAcquire() {
        return permisos > 0;
    }
}

/*Esta solución tiene starvation debido a que, por como funcionan los monitores en Java, el pasaje de un thread del wait set al entry set no es fair,
  y tampoco se da el lock a un thread del entry set de forma fair. Debido a esto, es un semáforo débil, el cual sufre starvation.
 */
