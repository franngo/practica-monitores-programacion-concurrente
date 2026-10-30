package ar.edu.unq.ejercicio_8;

public class Main {
    public static void main(String[] args) {
        Buffer_N_sol buffer = new Buffer_N_sol(2);
        Productor productor = new Productor(buffer);
        Consumidor consumidor = new Consumidor(buffer);
        consumidor.start();
        productor.start();
    }
}

/*
Con start, hacemos que se cree un hilo de ejecución independiente, el cual se va a encargar de ejecutar el código de consumidor.
Tras esto, comienza la ejecución de dicho hilo. Se comienza a ejecutar su method run, que es como el main del thread.

Por más que consumidor se duerma antes de que se llegue a ejecutar productor.start(), eso no importa, porque el main y consumidor ya son procesos/hilos separados.
Que consumidor se duerma no va a hacer que el main se duerma. Por tanto, el main sí o sí va a llegar a iniciar el thread para productor, el cual va a producir
y va a despertar a consumidor para que pueda consumir.
 */
