import java.util.Random;


class Coche implements Runnable {
    private String nombre;
    private int distanciaRecorrida;
    private static final int DISTANCIA_META = 100;
    private static boolean ganadorAnunciado = false;
    private static final Object lock = new Object(); // Para sincronizar el anuncio del ganador

    public Coche(String nombre) {
        this.nombre = nombre;
        this.distanciaRecorrida = 0;
    }

    @Override
    public void run() {
        Random random = new Random();

        // El coche avanza en incrementos aleatorios
        while (distanciaRecorrida < DISTANCIA_META && !ganadorAnunciado) {
            int avance = random.nextInt(10) + 1; // Avance aleatorio entre 1 y 10

            // Simular un obstáculo (20% de probabilidad de ralentizar al coche)
            if (random.nextInt(100) < 20) {
                System.out.println(nombre + " ha encontrado un obstáculo y se ralentiza!");
                avance = Math.max(avance / 2, 1); // La velocidad se reduce a la mitad (mínimo 1)
            }

            distanciaRecorrida += avance;

            // Mostrar el progreso del coche en una barra de 20 caracteres
            mostrarProgreso();

            // Verificar si el coche ha ganado la carrera
            if (distanciaRecorrida >= DISTANCIA_META && !ganadorAnunciado) {
                synchronized (lock) {
                    if (!ganadorAnunciado) { // Doble chequeo para asegurarse
                        ganadorAnunciado = true;
                        System.out.println("\n" + nombre + " ha ganado la carrera!");
                    }
                }
            }

            try {
                // Simula el tiempo que toma para avanzar
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para mostrar una barra de progreso en la consola
    private void mostrarProgreso() {
        int progreso = (distanciaRecorrida * 20) / DISTANCIA_META; // Progreso en una escala de 0 a 20
        StringBuilder barraProgreso = new StringBuilder("[");

        for (int i = 0; i < 20; i++) {
            if (i < progreso) {
                barraProgreso.append("=");
            } else {
                barraProgreso.append(" ");
            }
        }
        barraProgreso.append("]");

        System.out.println(nombre + " " + barraProgreso + " " + distanciaRecorrida + " / " + DISTANCIA_META);
    }
}
