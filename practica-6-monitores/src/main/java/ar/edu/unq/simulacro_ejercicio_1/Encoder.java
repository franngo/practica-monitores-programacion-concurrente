package ar.edu.unq.simulacro_ejercicio_1;

import java.util.ArrayList;
import java.util.List;

//Encoder inciso a
public class Encoder {
    private ArrayList<Object> frames = new ArrayList<Object>();
    private int p; //la cantidad constante de cuadros que se devuelven en getPack
    private int m; //el máximo de cuadros almacenable en frames

    public Encoder(int p, int m) {
        this.p = p;
        this.m = m;
    }

    public void putRawFrame(Object frame) {
        while(!canPut()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        frames.add(frame);
        notifyAll();
    }

    public List<Object> getPack() {
        while(!canGet()) {
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
        notifyAll();
        return aDevolver;
    }

    private boolean canPut() {
        return frames.size() + 1 <= m;
    }

    private boolean canGet() {
        return frames.size() >= p;
    }
}
