import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    private static final int META = 100;
    private int puerto;
    private int numTortugas;
    
    public Servidor(int puerto, int numTortugas) {
        this.puerto = puerto;
        this.numTortugas = numTortugas;
    }
    
    public void iniciar() {
        System.out.println("Iniciando servidor en el puerto " + puerto + "...");
        
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            List<Socket> clientes = new ArrayList<>();
            List<PrintWriter> escritorClientes = new ArrayList<>();
            
            for (int i = 0; i < numTortugas; i++) {
                Socket clienteSocket = serverSocket.accept();
                clientes.add(clienteSocket);
                escritorClientes.add(new PrintWriter(clienteSocket.getOutputStream(), true));
                System.out.println("Tortuga conectada en el puerto " + puerto);
            }
            
            int[] posiciones = new int[numTortugas];
            boolean[] llegoMeta = new boolean[numTortugas];
            int tortugasLlegaron = 0;
            
            while (tortugasLlegaron < numTortugas) {
                for (int i = 0; i < numTortugas; i++) {
                    if (!llegoMeta[i]) {
                        posiciones[i] += (int) (Math.random() * 10);
                        if (posiciones[i] >= META) {
                            posiciones[i] = META;
                            llegoMeta[i] = true;
                            tortugasLlegaron++;
                            escritorClientes.get(i).println("¡Felicidades! Has llegado a la meta.");
                        } else {
                            escritorClientes.get(i).println("Tortuga avanzó a: " + posiciones[i]);
                        }
                    }
                }
                Thread.sleep(500);
            }
            
            System.out.println("Carrera finalizada en el puerto " + puerto);
            for (Socket cliente : clientes) {
                cliente.close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Thread(() -> new Servidor(5500, 3).iniciar()).start();
        new Thread(() -> new Servidor(5501, 3).iniciar()).start();
    }
}

class Tortuga {
    private String host;
    private int puerto;

    public Tortuga(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void iniciar() {
        try (Socket socket = new Socket(host, puerto);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {
            
            System.out.println("Conectado al servidor en puerto " + puerto);
            String mensaje;
            while ((mensaje = input.readLine()) != null) {
                System.out.println(mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java Tortuga <puerto>");
            return;
        }
        int puerto = Integer.parseInt(args[0]);
        new Tortuga("localhost", puerto).iniciar();
    }
}
