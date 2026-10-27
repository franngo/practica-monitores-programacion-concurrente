package ar.edu.unq.simulacro_ejercicio_1;

import java.util.ArrayList;
import java.util.List;

//Encoder inciso a
public class Encoder {
    private ArrayList<Object> frames = new ArrayList<Object>();
    private int p; //la cantidad constante de cuadros que se devuelven en getPack
    private int m; //el máximo de cuadros almacenable en frames

    //P debe ser <= M
    public Encoder(int p, int m) {
        this.p = p;
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
        if(canGet()) {
            notify(); //pues no tiene sentido despertar a un consumidor si todavía no hay p frames.
        }
    }

    public synchronized List<Object> getPack() {
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
        notify(); //porque se nos dice que UN thread actua como productor, y sabemos que en este momento solo va a estar en el wait set el único productor.
        return aDevolver;
    }

    private boolean canPut() {
        return frames.size() + 1 <= m;
    }

    private boolean canGet() {
        return frames.size() >= p;
    }
}
