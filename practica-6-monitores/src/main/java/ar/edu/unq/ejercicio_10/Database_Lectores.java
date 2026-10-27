package ar.edu.unq.ejercicio_10;

/*
Vamos a tener el recurso global compartido fuera del monitor, y en el monitor vamos a tener métodos para permitir el acceso a dicho recurso.
Va a ser así para que podamos permitir las acciones concurrentes sobre dicho recurso, ya que tenemos que permitir múltiples lectores concurrentes, por ejemplo.
 */
//AHORA SÍ IMPONIENDO PRIORIDAD LECTORES
public class Database_Lectores {
    private int lectores = 0;
    private boolean seEstaEscribiendo = false;
    private int lectoresEsperando = 0;

    public synchronized void inicioEscritura() {
        while(seEstaLeyendo() || seEstaEscribiendo || hayLectoresEsperando()) {
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
        lectoresEsperando++; //Se hace una vez ANTES del while, porque, si se hace antes del wait(), podría darse que obtenga el lock, nuevamente se cumpla seEstaEscribiendo e incremente lectoresEsperando otra vez más, inflando así la variable de forma incorrecta.
        while(seEstaEscribiendo) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        lectoresEsperando--;
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

    private boolean hayLectoresEsperando() {
        return lectoresEsperando > 0;
    }
}

