package ar.edu.unq;

public class Consumidor extends Thread {
    Buffer_uno buffer;

    public Consumidor(Buffer_uno buffer) {
        this.buffer = buffer;
    }

    public void consumir() {
        Object x;
        while(true) {
            //x = buffer.consumir();
            //consumirObj(x);
        }
    }
}
