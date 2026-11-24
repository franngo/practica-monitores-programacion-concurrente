package ar.edu.unq.simulacro_ejercicio_1_bis;

import java.util.ArrayList;
import java.util.List;

public class Encoder_a {
    private List<Object> rawFrames = new ArrayList<Object>();
    private final int maxRawFrames; //M
    private final int framesPerPack; //P

    //Se asume que P es menor o igual a M
    public Encoder_a(int maxRawFrames, int framesPerPack) {
        this.maxRawFrames = maxRawFrames;
        this.framesPerPack = framesPerPack;
    }

    public synchronized void putRawFrame(Object frame) throws InterruptedException {
        while (!canPut()) {
            wait();
        }
        rawFrames.add(frame);
        notifyAll();
        //o también podría hacer la optimización if(canGet()) { notifyAll(); }, para no hacer el notifyAll si aún no hay P frames.
    }

    public synchronized List<Object> getPack() throws InterruptedException {
        while(!canGet()) {
            wait();
        }
        List<Object> pack = new ArrayList<Object>();
        for(int i=0; i<framesPerPack; i++) {
            pack.add(rawFrames.removeFirst());
        }
        notifyAll();
        return pack;
    }

    private boolean canPut() {
        return rawFrames.size() < maxRawFrames; // < M
    }

    private boolean canGet() {
        return rawFrames.size() >= framesPerPack; // >= P
    }
}

/*
Mis razones para el notifyAll() en vez de notify() son las mismas que antes. Pueden haber threads dormidos por 2 guardas distintas, y yo, en cada momento, solo quiero
despertar a los de un tipo específico, por lo que no me queda otra que despertar a todos.
 */
