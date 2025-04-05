import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServidorCarrera extends UnicastRemoteObject implements CarreraTortugas {
    private static final int META = 100;
    private Map<String, Integer> posiciones;
    private String ganador = null;

    protected ServidorCarrera() throws RemoteException {
        super();
        posiciones = new HashMap<>();
    }

    @Override
    public String registrarTortuga() throws RemoteException {
        String id = UUID.randomUUID().toString();
        posiciones.put(id, 0);
        System.out.println("Tortuga registrada con ID: " + id);
        return id;
    }

    @Override
    public int avanzar(String idTortuga) throws RemoteException {
        if (!posiciones.containsKey(idTortuga) || ganador != null) {
            return posiciones.getOrDefault(idTortuga, 0);
        }

        int avance = (int) (Math.random() * 10);
        posiciones.put(idTortuga, posiciones.get(idTortuga) + avance);

        System.out.println("Tortuga " + idTortuga + " avanzó a: " + posiciones.get(idTortuga));

        if (posiciones.get(idTortuga) >= META && ganador == null) {
            ganador = idTortuga;
            System.out.println("¡Tortuga " + idTortuga + " ha ganado la carrera!");
        }

        return posiciones.get(idTortuga);
    }

    @Override
    public boolean haFinalizado(String idTortuga) throws RemoteException {
        return posiciones.get(idTortuga) >= META;
    }

    @Override
    public String obtenerGanador() throws RemoteException {
        return ganador;
    }

    @Override
    public Map<String, Integer> obtenerEstadoCarrera() throws RemoteException {
        return new HashMap<>(posiciones); // Devolvemos una copia para evitar cambios no controlados
    }

    public static void main(String[] args) {
        try {
            ServidorCarrera servidor = new ServidorCarrera();
            Registry registro = LocateRegistry.createRegistry(1099);
            registro.rebind("CarreraTortugas", servidor);
            System.out.println("Servidor RMI listo.");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
