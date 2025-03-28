// recibe actualizaciones del servidor para la posicion en la carrera
import java.io.*;
import java.net.*;  

public class Tortuga {
    private static final String HOST = "localhost";
    private static final int PUERTO = 5500;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Conectado al servidor como Tortuga #" + socket.getPort());

            String mensaje;
            while ((mensaje = input.readLine()) != null) {
                System.out.println(mensaje);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
