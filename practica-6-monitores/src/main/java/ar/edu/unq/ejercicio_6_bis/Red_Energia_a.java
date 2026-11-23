package ar.edu.unq.ejercicio_6_bis;

public class Red_Energia_a {
    private int productores_ociosos = 0;

    public synchronized void inicioProduccion() {
        productores_ociosos++;
        notify();
    }

    public synchronized void inicioConsumo() throws InterruptedException {
        while(!puedeInicioConsumo()) {
            wait();
        }
        productores_ociosos--;
    }

    public synchronized void finProduccion() throws InterruptedException {
        while(!puedeFinProduccion()) {
            wait();
        }
        productores_ociosos--;
    }

    public synchronized void finConsumo() {
        productores_ociosos++;
        notify();
    }

    private boolean puedeInicioConsumo() {
        return productores_ociosos >= 0;
    }

    private boolean puedeFinProduccion() {
        return productores_ociosos >= 0;
    }
}

//En vez de guardar consumidores y productores, y hacer la resta para conocer la cantidad de productores ociosos, directamente lo que guardo son los productores ociosos,
//y los incremento o decremento dependiendo de si alguien empieza a producir o a consumir.
