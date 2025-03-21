import java.net.*;
import java.io.*;

public class Tortuga {
    private static final String HOST = "localhost";
    private static final int PUERTO = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Tortuga conectada al servidor.");

            // Recibir las posiciones de la tortuga desde el servidor
            String mensaje;
            while ((mensaje = input.readLine()) != null) {
                System.out.println(mensaje);
                // Aquí puedes añadir lógica para simular el comportamiento de la tortuga, por ejemplo, moverla
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
