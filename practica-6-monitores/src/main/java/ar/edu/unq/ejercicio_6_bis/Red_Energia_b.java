package ar.edu.unq.ejercicio_6_bis;

public class Red_Energia_b {
    private int productores_ociosos = 0;
    private final int topeProductoresOciosos;

    //se asume que topeProductoresOciosos > 0
    public Red_Energia_b(int topeProductoresOciosos) {
        this.topeProductoresOciosos = topeProductoresOciosos;
    }

    public synchronized void inicioProduccion() throws InterruptedException {
        while(!puedeInicioProduccion()) {
            wait();
        }
        productores_ociosos++;
        notifyAll();
    }

    public synchronized void inicioConsumo() throws InterruptedException {
        while(!puedeInicioConsumo()) {
            wait();
        }
        productores_ociosos--;
        notifyAll();
    }

    public synchronized void finProduccion() throws InterruptedException {
        while(!puedeFinProduccion()) {
            wait();
        }
        productores_ociosos--;
        notifyAll();
    }

    public synchronized void finConsumo() {
        productores_ociosos++;
        notifyAll();
    }

    private boolean puedeInicioConsumo() {
        return productores_ociosos > 0;
    }

    private boolean puedeFinProduccion() {
        return productores_ociosos > 0;
    }

    private boolean puedeInicioProduccion() {
        return productores_ociosos < topeProductoresOciosos;
    }
}

/*
Como ahora hay threads que quieren producir que pueden quedar en espera, inicioConsumo y finProduccion, que reducen los productores ociosos, tienen que llevar a cabo
un notify, pues el nuevo estado puede permitir que alguno de estos threads mencionados continue su ejecución.

Como pueden haber dormidos tanto threads que quieren empezar a producir (productores_ociosos < N) (1er tipo) como threads que quieren dejar de producir o empezar a
consumir (productores_ociosos > 0) (2do tipo), tras llevar a cabo un cambio de estado, en todos los casos hacemos notifyAll y no notify, pues podríamos despertar al
incorrecto, el cual se volvería a dormir, mientras que habría threads que pueden continuar su ejecución que continuarían dormidos.

En finConsumo() y en inicioProduccion(), donde incremento productores_ociosos, hago notifyAll porque a quien querría despertar es a un thread de los que esperan
productores_ociosos > 0 y no de los que esperan productores_ociosos < N, por lo que, para no despertar a uno del tipo incorrecto, hago notifyAll().

Y viceversa con inicioConsumo() y finProduccion().
 */
