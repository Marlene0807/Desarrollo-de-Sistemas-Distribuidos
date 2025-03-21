// Ejemplo de cliente con tcp
import java.net.*;
import java.io.*;

public class clientetcp {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Socket socket; //objeto socket

        byte[] mensaje_bytes = new byte[256];
        String mensaje = "";

        try {
            socket = new Socket("127.0.0.1", 5500);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            do {
                mensaje = in.readLine();
                out.writeUTF(mensaje);
            } while (!mensaje.startsWith("fin"));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }    
}
