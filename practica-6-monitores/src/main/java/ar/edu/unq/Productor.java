package ar.edu.unq;

public class Productor extends Thread {
    Buffer_uno buffer;

    public Productor(Buffer_uno buffer) {
        this.buffer = buffer;
    }

    public void producir() {
        Object x;
        while(true) {
            //x = producirObj();
            //buffer.producir(x);
        }
    }
}
