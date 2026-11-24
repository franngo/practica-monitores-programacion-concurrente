package ar.edu.unq.ejercicio_parcial_ClimaticoRegional;

public class ClimaticoRegional_a {
    Object[] datos; //Object en vez de DatosMeteorologicos
    Boolean[] medicionActiva;
    Boolean[] estadisticaActiva;

    public ClimaticoRegional_a(int n) {
        datos = new Object[n];
        medicionActiva = new Boolean[n];
        estadisticaActiva = new Boolean[n];
        for(int i=0; i<n; i++) {
            medicionActiva[i] = false;
            estadisticaActiva[i] = false;
        }
    }

    public synchronized void iniciarMedicion(int zona) throws InterruptedException {
        while (!puedeIniciarMedicion(zona)) {
            wait();
        }
        medicionActiva[zona] = true;
    }

    public synchronized void finalizarMedicion(int zona, Object dm) {
        datos[zona] = dm;
        medicionActiva[zona] = false;
        notifyAll();
    }

    public synchronized void iniciarEstadistica(int zona) throws InterruptedException {
        while (!puedeIniciarEstadistica(zona)) {
            wait();
        }
        estadisticaActiva[zona] = true;
    }

    public synchronized void finalizarEstadistica(int zona) {
        estadisticaActiva[zona] = false;
        notifyAll();
    }

    private boolean puedeIniciarMedicion(int zona) {
        return !medicionActiva[zona] && !estadisticaActiva[zona];
    }

    private boolean puedeIniciarEstadistica(int zona) {
        return !medicionActiva[zona] && !estadisticaActiva[zona];
    }
}

//la estadística no hace nada (hace algo que no sabemos)

/*En ambos casos hacemos notify porque podemos tener threads esperando para iniciar una medición o para iniciar una estadística PARA MÚLTIPLES ZONAS DIFERENTES,
por lo que si uso notify y despierto a un thread que esperaba para entrar a otra zona diferente, este se va a volver a dormir, y puede haber un thread que quería
iniciar una medición o estadística para mi zona que no es despertado.
Es por esto que usamos notifyAll y los despertamos a todos.
 */
