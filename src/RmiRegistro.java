import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

public class RmiRegistro {

    private static final long serialVersionUID = 1L;
    
    public static void main(String[] args) {
        System.setProperty( "java.security.policy","RMISecurity.policy"); 
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
        try {
            Registry rmiRegistry = LocateRegistry.createRegistry(9999);
            ObjetoRemoto srv = new ObjetoRemoto();
            IObjetoRemoto rmiService = (IObjetoRemoto) UnicastRemoteObject.exportObject(srv, 9999);
            rmiRegistry.bind("RmiService", rmiService);
            Agenda.getObjeto();
            System.out.println("Servidor On-line");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Agenda.getObjeto().atualizar();
            }
        }, 100, 1000);
    }
    
}
