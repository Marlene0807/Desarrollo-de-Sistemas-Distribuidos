// Ejemplo de socket con udp-servidor

import java.net.*;
import java.io.*;

public class servidorudp {
    public static void main(String[] args) {
        DatagramSocket socket;
        boolean fin = false;

        try {
            socket = new DatagramSocket(5500);
            
            byte[] mensaje_bytes = new byte[256];
            String mensaje = "";
            mensaje = new String(mensaje_bytes);
            String mensajeComp = "";

            DatagramPacket paquete = new DatagramPacket(mensaje_bytes, 256);
            DatagramPacket envpaquete = new DatagramPacket(mensaje_bytes, 256);

            int puerto;
            InetAddress address;
            byte[] mensaje2_bytes =new byte[256];

            do { //iniciamos ciclo
                socket.receive(paquete); //recibimos paquete
                mensaje = new String(mensaje_bytes).trim(); //lo formateamos
                System.out.println(mensaje); //lo mostramos en pantalla
                puerto = paquete.getPort();
                address = paquete.getAddress();

                if (mensaje.startsWith("fin")) {
                    mensajeComp = "bye cliente";
                }

                if (mensaje.startsWith("hola")) {
                    mensajeComp = "hola cliente";
                }

                mensaje2_bytes = mensajeComp.getBytes(); //formateamos el mensaje de salida
                envpaquete = new DatagramPacket(mensaje2_bytes, mensajeComp.length(), address, puerto);
                socket.send(envpaquete);
            } while (1>0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }    
}
