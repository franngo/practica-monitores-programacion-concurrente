package ar.edu.unq.ejercicio_10;

/*
Vamos a tener el recurso global compartido fuera del monitor, y en el monitor vamos a tener métodos para permitir el acceso a dicho recurso.
Va a ser así para que podamos permitir las acciones concurrentes sobre dicho recurso, ya que tenemos que permitir múltiples lectores concurrentes, por ejemplo.
 */
//PRIORIDAD ESCRITORES
public class Database_Escritores {
    private int lectores = 0;
    private boolean seEstaEscribiendo = false;
    private int escritoresEsperando = 0;

    public synchronized void inicioEscritura() {
        escritoresEsperando++;
        while(seEstaLeyendo() || seEstaEscribiendo) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        escritoresEsperando--;
        seEstaEscribiendo = true;
    }
    public synchronized void finEscritura () {
        seEstaEscribiendo = false;
        notifyAll(); //a los que quiero despertar son a los escritores, pero también puede haber lectores dormidos.
    }

    public synchronized void inicioLectura () {
        while(seEstaEscribiendo || hayEscritoresEsperando()) {
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
            notifyAll(); //porque si hay algún escritor esperando, esto puede causar que también haya lectores esperando debido a la waiting condition. Acá queremos despertar escritores, pero ante la posibilidad de que hayan tanto escritores como lectores, despertamos a todos.
        }
    }

    private boolean seEstaLeyendo() {
        return lectores > 0;
    }

    private boolean hayEscritoresEsperando() {
        return escritoresEsperando > 0;
    }
}
