import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.HashMap;

public class ClienteGUI extends JFrame {
    private CarreraTortugas carrera;
    private String idTortuga;
    private Map<String, JProgressBar> barras;
    private JLabel labelGanador;

    public ClienteGUI() {
        super("Carrera de Tortugas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        barras = new HashMap<>();
        labelGanador = new JLabel("Esperando al ganador...", SwingConstants.CENTER);
        add(labelGanador);

        conectarServidor();
        registrarTortuga();
        iniciarActualizacion();
    }

    private void conectarServidor() {
        try {
            Registry registro = LocateRegistry.getRegistry("localhost", 1099);
            carrera = (CarreraTortugas) registro.lookup("CarreraTortugas");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registrarTortuga() {
        try {
            idTortuga = carrera.registrarTortuga();
            System.out.println("Tortuga registrada con ID: " + idTortuga);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void iniciarActualizacion() {
        new Thread(() -> {
            try {
                while (true) {
                    carrera.avanzar(idTortuga);
                    Map<String, Integer> estado = carrera.obtenerEstadoCarrera();
                    String ganador = carrera.obtenerGanador();

                    SwingUtilities.invokeLater(() -> {
                        actualizarCarrera(estado, ganador);
                    });

                    if (ganador != null) break;
                    Thread.sleep(500);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void actualizarCarrera(Map<String, Integer> estado, String ganador) {
        for (String id : estado.keySet()) {
            if (!barras.containsKey(id)) {
                JProgressBar barra = new JProgressBar(0, 100);
                barra.setStringPainted(true);
                barras.put(id, barra);
                add(barra);
                revalidate();
            }
            barras.get(id).setValue(estado.get(id));
        }

        if (ganador != null) {
            labelGanador.setText("¡Tortuga ganadora: " + ganador + "!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClienteGUI().setVisible(true);
        });
    }
}
