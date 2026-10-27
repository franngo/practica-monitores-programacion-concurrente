package ar.edu.unq.simulacro_ejercicio_1;

import java.util.ArrayList;
import java.util.List;

//Encoder inciso c
public class Encoder_c {
    private ArrayList<Object> frames = new ArrayList<Object>();
    //private int p; //YA NO HAY cantidad constante de cuadros que se devuelven en getPack (ahora es parametrizable)
    private int m; //el máximo de cuadros almacenable en frames
    private int getPacks = 0;
    private ArrayList<Object> encoded = new ArrayList<Object>();
    private int k; //el máximo de getPacks que se pueden hacer sin que se haga un putEncodedPack. Cada putEncodedPack va a permitir un nuevo getPack.

    public Encoder_c(int m, int k) {
        //this.p = p;
        this.m = m;
        this.k = k;
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
        getPacks++;
        notifyAll(); //pues ahora se podría despertar el thread productor o algún thread que quiere hacer putEncodedPack.
        return aDevolver;
    }

    public synchronized void putEncodedPack(List<Object> encodedPack) { //trae los frames tomados antes pero ya encodeados
        while(!canPutEncoded()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        getPacks--;
        encoded.add(encodedPack);
        notifyAll(); //si se hicieron k getPacks, al haber hecho un putEncodedPack, libero que se pueda hacer un nuevo getPack, PERO, también podrían haber productores de frames esperando, pues se podrían haber acumulado frames mientras los getPacks estaban bloqueados. Por tanto, hago notifyAll, pues solo me sirve despertar consumidores con este nuevo estado, pero tengo consumidores y productores dormidos, así que no puedo arriesgar con simplemente notify.
    }

    private boolean canPut() {
        return frames.size() + 1 <= m;
    }

    private boolean canGet(int p) {
        return frames.size() >= p && getPacks + 1 <= k;
    }

    private boolean canPutEncoded() {
        return getPacks > 0;
    }
}

/*
El profe Nicolás se hizo 2 variables, una getPacks y otra encodeds. En putEncodedPack, en vez de getPacks--, hacía encodeds++, y dps
en canGet hacía
return frames.size() >= p && getPacks - encoded < k;
Me parece más fea que mi solución, aunque ambas hacen exactamente lo mismo.
 */
