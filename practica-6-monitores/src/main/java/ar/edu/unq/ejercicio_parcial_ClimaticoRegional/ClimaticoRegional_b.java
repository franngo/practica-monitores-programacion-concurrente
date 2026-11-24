package ar.edu.unq.ejercicio_parcial_ClimaticoRegional;

public class ClimaticoRegional_b {
    Object[] datos; //Object en vez de DatosMeteorologicos
    Boolean[] medicionActiva;
    Boolean[] estadisticaActiva;
    int[] medicionesHastaEstadistica;
    int[] medicionesEsperando;

    public ClimaticoRegional_b(int n) {
        datos = new Object[n];
        medicionActiva = new Boolean[n];
        estadisticaActiva = new Boolean[n];
        medicionesHastaEstadistica = new int[n];
        medicionesEsperando = new int[n];
        for(int i=0; i<n; i++) {
            medicionActiva[i] = false;
            estadisticaActiva[i] = false;
            medicionesHastaEstadistica[i] = 0;
            medicionesEsperando[i] = 0;
        }
    }

    public synchronized void iniciarMedicion(int zona) throws InterruptedException {
        medicionesEsperando[zona]++;
        while (!puedeIniciarMedicion(zona)) {
            wait();
        }
        medicionActiva[zona] = true;
        medicionesEsperando[zona]--;
    }

    public synchronized void finalizarMedicion(int zona, Object dm) {
        datos[zona] = dm;
        medicionActiva[zona] = false;
        medicionesHastaEstadistica[zona]++;
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
        medicionesHastaEstadistica[zona] = 0; //reseteamos a 0, pues en la zona hubo nueva estadística
        notifyAll();
    }

    private boolean puedeIniciarMedicion(int zona) {
        return !medicionActiva[zona] && !estadisticaActiva[zona];
    }

    private boolean puedeIniciarEstadistica(int zona) {
        return (!medicionActiva[zona] && !estadisticaActiva[zona]) && medicionesHastaEstadistica[zona] >= 5 && medicionesEsperando[zona] == 0;
    }
}

//la estadística no hace nada (hace algo que no sabemos)

/*En ambos casos hacemos notify porque podemos tener threads esperando para iniciar una medición o para iniciar una estadística PARA MÚLTIPLES ZONAS DIFERENTES,
por lo que si uso notify y despierto a un thread que esperaba para entrar a otra zona diferente, este se va a volver a dormir, y puede haber un thread que quería
iniciar una medición o estadística para mi zona que no es despertado.
Es por esto que usamos notifyAll y los despertamos a todos.
 */

