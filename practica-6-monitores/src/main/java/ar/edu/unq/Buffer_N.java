package ar.edu.unq;

public class Buffer_N {
    private Object[] buffer;
    private int primeraParaConsumir = 0;
    private int proximaLibre = 0;

    //Con n+1 slots para que, si solo queda libre el slot extra, eso significa que toddo el buffer está ocupado
    public Buffer_N(int n) {
        buffer = new Object[n+1];
    }

    public synchronized Object consumir() {
        while(primeraParaConsumir != proximaLibre) {
            wait();
        }
        Object aDevolver = buffer[primeraParaConsumir];
        buffer[primeraParaConsumir] = null;
        primeraParaConsumir = (1 + primeraParaConsumir) % buffer.length;
        notifyAll();
        return aDevolver;
    }

    public synchronized void producir(Object dato) {
        while(buffer[next()] != null) { //si solo queda libre el slot extra, entonces significa que toddo el buffer está ocupado
            wait();
        }
        buffer[proximaLibre] = dato;
        proximaLibre = (1 + proximaLibre) % buffer.length;
        notifyAll();
    }

    private int next() {
        return (1 + proximaLibre) % buffer.length;
    }
}
