package ar.edu.unq.ejercicio_6;

//SOLUCIÓN DEL INCISO A
public class Red_Energia_a {
    private int productores = 0;
    private int consumidores = 0;

    public synchronized void inicioConsumo() {
        while(!puedeConsumir()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        consumidores++;
    }

    public synchronized void FinConsumo() {
        consumidores--;
        notify(); //no notifyAll, porque puede haber bloqueados tanto consumidores (esperando a entrar) como productores (esperando a salir), pero ambos esperan lo mismo (un consumidor menos o un productor más)
    }

    public synchronized void inicioProduccion() {
        productores++;
        notify(); //no notifyAll, porque puede haber bloqueados tanto consumidores (esperando a entrar) como productores (esperando a salir), pero ambos esperan lo mismo (un consumidor menos o un productor más)
    }

    public synchronized void finProduccion() {
        while(!puedeFinalizarProduccion()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        productores--;
    }

    private boolean puedeConsumir() {
        return consumidores + 1 <= productores;
    }

    private boolean puedeFinalizarProduccion() {
        return productores - 1 >= consumidores;
    }
}
/*
Según Pablo en clase: está bien asumir buena fe sobre los threads. Uno que hace inicioConsumo() no va a hacer después inicioProduccion() y viceversa. No nos preocupamos.
 */
