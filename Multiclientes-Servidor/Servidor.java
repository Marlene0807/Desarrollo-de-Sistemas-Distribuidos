// maneja a cada tortuga en un hilo independiente
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Servidor {
    private static final int PUERTO = 5500;
    private static final int META = 100;
    private static Map<Integer, Integer> posiciones = new ConcurrentHashMap<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        System.out.println("Servidor iniciado en el puerto " + PUERTO);
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket clienteSocket = serverSocket.accept();
                pool.execute(new ManejadorCliente(clienteSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ManejadorCliente implements Runnable {
        private Socket socket;
        private int id;
        private PrintWriter escritor;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
            this.id = socket.getPort(); // Identificador único por puerto
            posiciones.put(id, 0);
        }

        @Override
        public void run() {
            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

                this.escritor = salida;
                System.out.println("Cliente conectado: Tortuga #" + id);

                while (true) {
                    // Simular movimiento
                    int avance = (int) (Math.random() * 10);
                    posiciones.put(id, posiciones.get(id) + avance);

                    // Informar a la tortuga de su posición
                    salida.println("Tortuga #" + id + " avanzó a: " + posiciones.get(id));

                    if (posiciones.get(id) >= META) {
                        salida.println("¡Felicidades! Tortuga #" + id + " ha ganado.");
                        socket.close();
                        break;
                    }

                    Thread.sleep(500);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                posiciones.remove(id);
                System.out.println("Tortuga #" + id + " desconectada.");
            }
        }
    }
}