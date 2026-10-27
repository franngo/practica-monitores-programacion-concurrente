package ar.edu.unq.ejercicio_3;

public class Secuenciador_Ternario {
    private int turno = 1;

    public synchronized void primero() {
        while(!puedeIrPrimero()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        cambioTurno();
        notifyAll();
    }

    public synchronized void segundo() {
        while(!puedeIrSegundo()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        cambioTurno();
        notifyAll();
    }

    public synchronized void tercero() {
        while(!puedeIrTercero()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        cambioTurno();
        notifyAll();
    }

    private void cambioTurno() {
        turno = (turno + 1) % 3;
    }

    private boolean puedeIrPrimero() {
        return turno == 1;
    }

    private boolean puedeIrSegundo() {
        return turno == 2;
    }

    private boolean puedeIrTercero() {
        return turno == 3;
    }
}

/*
Otra opción sería tener una función como:
    public void turnoDeSecuencia(Boolean condición) {
        while(condicion) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        cambioTurno();
        notifyAll();
    }

Y después simplemente hacer
    public synchronized void primero() {
        turnoDeSecuencia(!puedeIrPrimero());
    }
 */


