import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface CarreraTortugas extends Remote {
    String registrarTortuga() throws RemoteException;
    int avanzar(String idTortuga) throws RemoteException;
    boolean haFinalizado(String idTortuga) throws RemoteException;
    String obtenerGanador() throws RemoteException;
    Map<String, Integer> obtenerEstadoCarrera() throws RemoteException;  // Nuevo método
}
