package checkersClient;

import java.rmi.RemoteException;

import checkersApi.User;

public class UserImpl implements User {

	@Override
	public void onGameStateChanged(String newState) throws RemoteException {
		System.out.println(newState);
	}

}
