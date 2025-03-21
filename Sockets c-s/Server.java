import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    private static final int NUM_TORTUGAS = 5;  // Número de tortugas (clientes)
    private static final int PUERTO = 5500;  // Puerto del servidor

    public static void main(String[] args) {
        System.out.println("Iniciando servidor de carrera de tortugas...");
        
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            List<Socket> clientes = new ArrayList<>();
            List<PrintWriter> escritorClientes = new ArrayList<>();
            
            // Aceptar conexiones de los clientes
            for (int i = 0; i < NUM_TORTUGAS; i++) {
                System.out.println("Esperando a tortuga #" + (i + 1) + "...");
                Socket clienteSocket = serverSocket.accept();
                clientes.add(clienteSocket);
                escritorClientes.add(new PrintWriter(clienteSocket.getOutputStream(), true));
                System.out.println("Tortuga #" + (i + 1) + " conectada.");
            }

            // Empezamos la carrera
            System.out.println("¡Las tortugas están listas para la carrera!");
            int[] posiciones = new int[NUM_TORTUGAS];  // Posición de cada tortuga
            
            // La carrera continúa mientras ninguna tortuga haya llegado al final
            boolean carreraTerminada = false;
            while (!carreraTerminada) {
                // Hacer que todas las tortugas se muevan
                for (int i = 0; i < NUM_TORTUGAS; i++) {
                    posiciones[i] += (int) (Math.random() * 10);  // Cada tortuga avanza a distinta velocidad

                    // Enviar posición actual a cada cliente/tortuga
                    escritorClientes.get(i).println("Posición de la tortuga #" + (i + 1) + ": " + posiciones[i]);

                    // Comprobar si alguna tortuga ha llegado al final (100 pasos)
                    if (posiciones[i] >= 100) {
                        carreraTerminada = true;
                        escritorClientes.get(i).println("¡Has ganado la carrera!");
                    }
                }

                // Esperar un poco para el siguiente movimiento (simula el paso del tiempo)
                Thread.sleep(500);
            }

            // Cerrar las conexiones
            for (Socket cliente : clientes) {
                cliente.close();
            }
            System.out.println("Carrera terminada.");
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
