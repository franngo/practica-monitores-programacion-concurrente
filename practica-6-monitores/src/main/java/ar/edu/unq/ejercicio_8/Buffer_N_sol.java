package ar.edu.unq.ejercicio_8;

//Solución de las diapos
/*
Solución de la diapos.
Lo único que cambié fueron los 2 notifyAll() por notify(), pues o va a haber consumidores dormidos si el buffer esta vacío, o va a haber productores dormidos si
el buffer está lleno, pero NUNCA va a haber de ambos, pues no pueden suceder ambas cosas a la vez, por lo que al producir un dato podemos estar seguros de que, si hay
alguien dormido, son consumidores porque el buffer estaba vacío, pero NO productores, pues el buffer no estaba lleno (ya que pudimos producir), y viceversa con el
consumir.
Además, como solo produjimos un dato, solo va a haber un consumidor que lo pueda aprovechar, por lo que levantar a todos los posibles consumidores dormidos no tendría
sentido, pues solo el primero aprovecharía el dato y el resto se dormiría de nuevo, lo cual es poco eficiente.
 */
public class Buffer_N_sol {
    private Object[] buffer;
    private int primeraParaConsumir = 0;
    private int proximaLibre = 0;

    //Con n+1 slots para que, si solo queda libre el slot extra, eso significa que toddo el buffer está ocupado
    public Buffer_N_sol(int n) {
        buffer = new Object[n+1];
    }

    public synchronized Object consumir() {
        while(!puedeConsumir()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Object aDevolver = buffer[primeraParaConsumir];
        //buffer[primeraParaConsumir] = null;
        primeraParaConsumir = next(primeraParaConsumir);
        notify();
        return aDevolver;
    }

    public synchronized void producir(Object dato) {
        while(!puedeProducir()) { //si solo queda libre el slot extra, entonces significa que toddo el buffer está ocupado
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        buffer[proximaLibre] = dato;
        proximaLibre = next(proximaLibre);
        notify();
    }

    private boolean puedeConsumir() {
        return primeraParaConsumir != proximaLibre;
    }

    private boolean puedeProducir() {
        return next(proximaLibre) != primeraParaConsumir;
    }

    private int next(int indice) {
        return (1 + indice) % buffer.length;
    }
}
