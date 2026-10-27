package ar.edu.unq.ejercicio_10;

/*
Vamos a tener el recurso global compartido fuera del monitor, y en el monitor vamos a tener métodos para permitir el acceso a dicho recurso.
Va a ser así para que podamos permitir las acciones concurrentes sobre dicho recurso, ya que tenemos que permitir múltiples lectores concurrentes, por ejemplo.
 */
//PRIORIDAD "LECTORES" (hasta ahí nomás) (en realidad, es medio como "prioridad ninguno". Si termina un escritor y desbloquea escritores y lectores, el que tenga
// la suerte de que pueda entrar va a a ser el que pueda ejecutar, no como en el esquema con prioridad lectores original, donde sí o sí entraban los lectores.
public class Database {
    private int lectores = 0;
    private boolean seEstaEscribiendo = false;

    public synchronized void inicioEscritura() {
        while(seEstaLeyendo() || seEstaEscribiendo) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        seEstaEscribiendo = true;
    }
    public synchronized void finEscritura () {
        seEstaEscribiendo = false;
        notifyAll(); //me sirve despertar tanto a lectores como a escritores, pues ambos son aptos para ejecutar
    }

    public synchronized void inicioLectura () {
        while(seEstaEscribiendo) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        lectores++;
    }

    public synchronized void finLectura () {
        lectores--;
        if(!seEstaLeyendo()) {
            notify(); //no notifyAll() porque solo va a haber escritores encolados en este momento, pues si un lector entra al monitor mientras otros leen, NO se lo encola
        }
    }

    private boolean seEstaLeyendo() {
        return lectores > 0;
    }
}
