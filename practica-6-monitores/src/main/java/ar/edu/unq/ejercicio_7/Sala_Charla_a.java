package ar.edu.unq.ejercicio_7;

public class Sala_Charla_a {
    private int asistentes = 0;
    private boolean estaElOrador = false;
    private final int capacidadMaxima;

    //Se asume que capacidadMaxima > 0
    public Sala_Charla_a(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public synchronized void entrarSala() throws InterruptedException {
        while (!puedeEntrarSala()) {
            wait();
        }
        asistentes++;
        notifyAll(); //pues podría continuar su ejecución el orador que espera asistentes > 0;
    }

    public synchronized void salirSala() throws InterruptedException {
        while (!puedeSalirSala()) {
            wait();
        }
        asistentes--;
        notifyAll(); //pues podría continuar su ejecución alguien que quiere entrar que espera asistentes < capacidadMaxima
    }

    public synchronized void inicioCharla() throws InterruptedException {
        while (!puedeInicioCharla()) {
            wait();
        }
        estaElOrador = true;
    }

    public synchronized void finCharla() {
        estaElOrador = false;
        notifyAll(); //pues podría continuar su ejecución alguien que quiere salir que espera !estaElOrador
    }

    //La sala tiene una capacidad para N personas.
    //Si hay lugar, sı́ se puede entrar en la sala aunque la charla haya comenzado.
    private boolean puedeEntrarSala() {
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

    //imagino que se asume que los threads actuan con buena fe y ninguno va a hacer cosas sin sentido dentro de la lógica de negocios, como 2 inicioCharla() seguidos, x ej.
}

//En los tres casos donde hago notifyAll, hago notifyAll y no notify, pues pueden convivir threads bloqueados por 3 guardas distintas en el wait set, por lo que
//despertar al equivocado con un notify podría causar que uno que si podía continuar su ejecución no lo haga.
