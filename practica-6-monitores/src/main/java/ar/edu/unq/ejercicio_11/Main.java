package ar.edu.unq.ejercicio_11;

public class Main {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(10, 8);
        for(int i=0; i<100; i++) {
            threadPool.launch(new DummyTask());
        }
        threadPool.stop();
    }
}
