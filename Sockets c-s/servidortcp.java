// Ejemplo de socket con tcp

import java.net.*; //librería java.net
import java.io.*; //librería java.io

public class servidortcp {

    public static void main(String[] args) {
        ServerSocket socket; //declaramos el objeto
        boolean fin = false;

        try {
            socket = new ServerSocket(5500);
            Socket socket_cli = socket.accept();

            DataInputStream in = new DataInputStream(socket_cli.getInputStream());

            do {
                String mensaje = "";
                mensaje = in.readUTF();
                System.out.println(mensaje);
            } while (1>0);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
