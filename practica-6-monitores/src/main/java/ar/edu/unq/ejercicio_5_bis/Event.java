package ar.edu.unq.ejercicio_5_bis;

public class Event {
    private int nroUltimoPublish = 0;

    public synchronized void publish() throws InterruptedException {
        nroUltimoPublish++;
        notifyAll();
    }

    public synchronized void subscribe() throws InterruptedException {
        int nroPublishRequerido = nroUltimoPublish + 1;
        while(nroUltimoPublish != nroPublishRequerido) {
            wait();
        }
    }
}

//Solo cambia respecto al original que no uso una subtarea, pero es que esta versión, para este caso particular, me parece más clara.
