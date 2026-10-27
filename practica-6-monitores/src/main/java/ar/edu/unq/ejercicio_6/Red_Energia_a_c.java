package ar.edu.unq.ejercicio_6;

//SOLUCIÓN DEL INCISO A Y C (que fue como lo hicimos en clase con Pablo)
public class Red_Energia_a_c {
    private int productores = 0;
    private int consumidores = 0;
    private int productoresEsperandoSalida = 0;

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
        notifyAll(); //Ahora sí notifyAll, pues entre los productores y los consumidores dormidos, nosotros queremos despertar a los productores, ya que los consumidores no podrán entrar debido a la nueva condición. Si solo hicieramos notify y se le diera el lock a un cosnumidor, este se dormiría y se pararía el programa.
    }

    public synchronized void inicioProduccion() {
        productores++;
        notifyAll(); //Ahora sí notifyAll, pues entre los productores y los consumidores dormidos, nosotros queremos despertar a los productores, ya que los consumidores no podrán entrar debido a la nueva condición. Si solo hicieramos notify y se le diera el lock a un cosnumidor, este se dormiría y se pararía el programa.
    }

    public synchronized void finProduccion() {
        productoresEsperandoSalida++;
        while(!puedeFinalizarProduccion()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        productoresEsperandoSalida--;
        productores--;
    }

    private boolean puedeConsumir() {
        return consumidores + 1 <= productores && productoresEsperandoSalida==0;
    }

    private boolean puedeFinalizarProduccion() {
        return productores - 1 >= consumidores;
    }
}
/*
Según Pablo en clase: está bien asumir buena fe sobre los threads. Uno que hace inicioConsumo() no va a hacer después inicioProduccion() y viceversa. No nos preocupamos.
 */

