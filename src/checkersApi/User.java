package checkersApi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface User extends Remote {

	public void onGameStateChanged(String newState) throws RemoteException;
	
}
