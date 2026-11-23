package ar.edu.unq.ejercicio_7;

public class Sala_Charla_a {
    private int asistentes = 0;
    private boolean estaElOrador = false;
    private final int capacidadMaxima;

    public Sala_Charla_a(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public synchronized void entrarSala() throws InterruptedException {
        while (!puedeentrarSala()) {
            wait();
        }
        asistentes++;
        //notify?
    }

    public synchronized void salirSala() throws InterruptedException {
        while (!puedeSalirSala()) {
            wait();
        }
        asistentes--;
        //notify?
    }

    public synchronized void inicioCharla() throws InterruptedException {
        while (!puedeInicioCharla()) {
            wait();
        }
        estaElOrador = true;
        //notify?
    }

    public synchronized void finCharla() {
        estaElOrador = false;
        //notify?
    }

    //La sala tiene una capacidad para N personas.
    //Si hay lugar, sı́ se puede entrar en la sala aunque la charla haya comenzado.
    private boolean puedeentrarSala() {
        return asistentes < capacidadMaxima;
    }

    //Un asistente solo puede dejar la sala si ya terminó la charla (o sea, si ya no está el orador).
    private boolean puedeSalirSala() {
        return !estaElOrador;
    }

    //Debe haber al menos una persona en la sala para comenzar.
    private boolean puedeInicioCharla() {
        return asistentes > 0;
    }
}
