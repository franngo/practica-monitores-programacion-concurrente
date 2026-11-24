package ar.edu.unq.simulacro_ejercicio_1_bis;

import java.util.ArrayList;
import java.util.List;

public class Encoder_c {
    private List<Object> rawFrames = new ArrayList<Object>();
    private final int maxRawFrames; //M
    private List<Object> encodedFrames = new ArrayList<Object>();
    private int packsLlevadosSinEncoding = 0;
    private final int maxGetPacks; //K

    public Encoder_c(int maxRawFrames, int maxGetPacks) {
        this.maxRawFrames = maxRawFrames;
        this.maxGetPacks = maxGetPacks;
    }

    public synchronized void putRawFrame(Object frame) throws InterruptedException {
        while (!canPutFrame()) {
            wait();
        }
        rawFrames.add(frame);
        notifyAll();
        //o también podría hacer la optimización if(canGet()) { notifyAll(); }, para no hacer el notifyAll si aún no hay P frames.
    }

    public synchronized List<Object> getPack(int cantFrames) throws InterruptedException {
        while(!canGet(cantFrames)) {
            wait();
        }
        List<Object> pack = new ArrayList<Object>();
        for(int i=0; i<cantFrames; i++) {
            pack.add(rawFrames.removeFirst());
        }
        notifyAll();
        packsLlevadosSinEncoding++;
        return pack;
    }

    public synchronized void putEncodedPack(List<Object> encodedPack) throws InterruptedException {
        while(!canPutEncoded()) {
            wait();
        }
        encodedFrames.addAll(encodedPack);
        packsLlevadosSinEncoding--;
        notifyAll();
    }

    private boolean canPutFrame() {
        return rawFrames.size() < maxRawFrames; // < M
    }

    private boolean canGet(int cantFrames) {
        return rawFrames.size() >= cantFrames && packsLlevadosSinEncoding < maxGetPacks; // < K
    }

    private boolean canPutEncoded() {
        return packsLlevadosSinEncoding > 0;
    }
}

/*
Mis razones para el notifyAll() en vez de notify() son las mismas que antes. Pueden haber threads dormidos por 2 guardas distintas, y yo, en cada momento, solo quiero
despertar a los de un tipo específico, por lo que no me queda otra que despertar a todos.
 */

/*
Para este inciso c, interpreté que con cada getPack, se abre la posibilidad de un putEncodedPack, pues me hace sentido con la lógica de negocios, ya que
al llevarme un paquete de cuadros crudos, puedo poner un paquete encodeado más tarde.
También interpreté que un putEncodedPack reduce en uno la cantidad de putEncodedPack que se pueden hacer, pero no anula por completo la operación hasta que
se haga otro getPack.
 */
