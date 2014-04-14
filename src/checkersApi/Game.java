package checkersApi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import checkersServer.UserTokenImpl;

public interface Game extends Remote {
	
	public UserToken playVsBot(User user) throws RemoteException;
	
	public UserTokenImpl playVsPlayer(User user) throws RemoteException;
	
	public boolean tryToMakeMove(UserTokenImpl userToken, int fromX, int fromY, int toX, int toY) throws RemoteException;
	
}
