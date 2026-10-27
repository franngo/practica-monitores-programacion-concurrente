package ar.edu.unq.simulacro_ejercicio_1;

import java.util.ArrayList;
import java.util.List;

//Encoder inciso b
public class Encoder_b {
    private ArrayList<Object> frames = new ArrayList<Object>();
    //private int p; //YA NO HAY cantidad constante de cuadros que se devuelven en getPack (ahora es parametrizable)
    private int m; //el máximo de cuadros almacenable en frames

    public Encoder_b(int m) {
        //this.p = p;
        this.m = m;
    }

    public synchronized void putRawFrame(Object frame) {
        while(!canPut()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        frames.add(frame);
        notifyAll(); //como no sabés cuántos frames necesita cada consumidor, tenés que despertar en toddo caso (y despertás a TODOS, porque podrías despertar justo a uno que no puede obtener tantos frames como necesita y que se vuelve a dormir)
    }

    public synchronized List<Object> getPack(int p) {
        while(!canGet(p)) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        List<Object> aDevolver = new ArrayList<Object>();
        for(int i=0; i<p; i++) {
            aDevolver.add(frames.remove(0)); //con remove(0) saco el primero de la lista y lo retorno
        }
        notify(); //porque se nos dice que UN thread actua como productor, y sabemos que en este momento solo va a estar en el wait set el único productor.
        return aDevolver;
    }

    private boolean canPut() {
        return frames.size() + 1 <= m;
    }

    private boolean canGet(int p) {
        return frames.size() >= p;
    }
}