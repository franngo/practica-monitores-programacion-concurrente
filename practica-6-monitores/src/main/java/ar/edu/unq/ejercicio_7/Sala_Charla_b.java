package ar.edu.unq.ejercicio_7;

public class Sala_Charla_b {
    private int asistentes = 0;
    private boolean estaElOrador = false;
    private final int capacidadMaxima;
    private int numCharlaActual = 0;

    //Se asume que capacidadMaxima > 0
    public Sala_Charla_b(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public synchronized void entrarSala(int numCharla) throws InterruptedException {
        while (!puedeEntrarSala(numCharla)) {
            wait();
        }
        asistentes++;
        notifyAll(); //pues podría continuar su ejecución el orador que espera asistentes > 0 (además de que numCharla == numCharlaActual)
    }

    public synchronized void salirSala() throws InterruptedException {
        while (!puedeSalirSala()) {
            wait();
        }
        asistentes--;
        notifyAll(); //pues podría continuar su ejecución alguien que quiere entrar que espera asistentes < capacidadMaxima (además de que numCharla == numCharlaActual)
    }

    public synchronized void inicioCharla(int numCharla) throws InterruptedException {
        while (!puedeInicioCharla(numCharla)) {
            wait();
        }
        estaElOrador = true;
    }

    public synchronized void finCharla() {
        estaElOrador = false;
        cambioDeCharla();
        notifyAll(); //pues podría continuar su ejecución o bien alguien que quiere salir que espera !estaElOrador, o bien alguien que espera numCharla == numCharlaActual (además de otra condición más)
    }

    //La sala tiene una capacidad para N personas.
    //Si hay lugar, sı́ se puede entrar en la sala aunque la charla haya comenzado.
    private boolean puedeEntrarSala(int numCharla) {
        return numCharlaActual == numCharla && asistentes < capacidadMaxima;
    }

    //Un asistente solo puede dejar la sala si ya terminó la charla (o sea, si ya no está el orador).
    private boolean puedeSalirSala() {
        return !estaElOrador;
    }

    //Debe haber al menos una persona en la sala para comenzar.
    private boolean puedeInicioCharla(int numCharla) {
        return numCharlaActual == numCharla && asistentes > 0;
    }

    //imagino que se asume que los threads actuan con buena fe y ninguno va a hacer cosas sin sentido dentro de la lógica de negocios, como 2 inicioCharla() seguidos, x ej.

    private void cambioDeCharla() {
        numCharlaActual = (numCharlaActual + 1) % 3;
    }
}

//En los tres casos donde hago notifyAll, hago notifyAll y no notify, pues pueden convivir threads bloqueados por 3 guardas distintas en el wait set, por lo que
//despertar al equivocado con un notify podría causar que uno que si podía continuar su ejecución no lo haga.

/*OJO: El enunciado NO me pide esperar a que se vayan todos los que vinieron a ver la primera charla para empezar la segunda. Yo asumo que se pueden quedar a varias
si quieren.
 */
