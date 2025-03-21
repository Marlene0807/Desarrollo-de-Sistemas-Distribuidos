// Ejemplo de socket con udp-cliente
import java.net.*;
import java.io.*;

public class clienteudp {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        DatagramSocket socket;
        InetAddress address;
        byte[] mensaje_bytes = new byte[256];
        String mensaje ="";
        mensaje_bytes = mensaje.getBytes();

        DatagramPacket paquete; //paquete
        String cadenaMensaje = "";
        DatagramPacket servPacket;
        byte[] RecogerServidor_bytes = new byte[256];

        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");

            do {
                mensaje = in.readLine();
                mensaje_bytes = mensaje.getBytes();
                paquete = new DatagramPacket(mensaje_bytes, mensaje.length(), address, 5500);
                socket.send(paquete);

                RecogerServidor_bytes = new byte[256];

                servPacket = new DatagramPacket(RecogerServidor_bytes, 256);
                socket.receive(servPacket);

                cadenaMensaje = new String(RecogerServidor_bytes).trim();

                System.out.println(cadenaMensaje);
            } while (!mensaje.startsWith("fin"));
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }    
}
