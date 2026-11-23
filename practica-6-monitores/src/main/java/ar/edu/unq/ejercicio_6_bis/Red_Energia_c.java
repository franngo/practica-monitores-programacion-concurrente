package ar.edu.unq.ejercicio_6_bis;

public class Red_Energia_c {
    private int productores_ociosos = 0;
    private final int topeProductoresOciosos;
    private int productorQuierendoSalir = 0;

    //se asume que topeProductoresOciosos > 0
    public Red_Energia_c(int topeProductoresOciosos) {
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
        productorQuierendoSalir++;
        while(!puedeFinProduccion()) {
            wait();
        }
        productores_ociosos--;
        productorQuierendoSalir--;
        notifyAll();
    }

    public synchronized void finConsumo() {
        productores_ociosos++;
        notifyAll();
    }

    private boolean puedeInicioConsumo() {
        return productores_ociosos > 0 && productorQuierendoSalir == 0;
    }

    private boolean puedeFinProduccion() {
        return productores_ociosos > 0;
    }

    private boolean puedeInicioProduccion() {
        return productores_ociosos < topeProductoresOciosos;
    }
}
