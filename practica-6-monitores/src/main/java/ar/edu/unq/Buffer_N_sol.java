package ar.edu.unq;

//Solución de las diapos
public class Buffer_N_sol {
    private Object[] buffer;
    private int primeraParaConsumir = 0;
    private int proximaLibre = 0;

    //Con n+1 slots para que, si solo queda libre el slot extra, eso significa que toddo el buffer está ocupado
    public Buffer_N_sol(int n) {
        buffer = new Object[n+1];
    }

    public synchronized Object consumir() {
        while(puedeConsumir()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Object aDevolver = buffer[primeraParaConsumir];
        //buffer[primeraParaConsumir] = null;
        primeraParaConsumir = next(primeraParaConsumir);
        notifyAll();
        return aDevolver;
    }

    public synchronized void producir(Object dato) {
        while(puedeProducir()) { //si solo queda libre el slot extra, entonces significa que toddo el buffer está ocupado
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        buffer[proximaLibre] = dato;
        proximaLibre = next(proximaLibre);
        notifyAll();
    }

    private boolean puedeConsumir() {
        return primeraParaConsumir == proximaLibre;
    }

    private boolean puedeProducir() {
        return next(proximaLibre) == primeraParaConsumir;
    }

    private int next(int indice) {
        return (1 + indice) % buffer.length;
    }
}
