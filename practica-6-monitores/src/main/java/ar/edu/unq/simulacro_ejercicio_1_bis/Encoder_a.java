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
    }

    public synchronized List<Object> getPack() throws InterruptedException {
        while(!canGet()) {
            wait();
        }
        List<Object> pack = new ArrayList<Object>();
        for(int i=0; i<framesPerPack; i++) {
            pack.add(rawFrames.removeFirst());
        }
        return pack;
    }

    private boolean canPut() {
        return rawFrames.size() < maxRawFrames;
    }

    private boolean canGet() {
        return rawFrames.size() >= framesPerPack;
    }
}
