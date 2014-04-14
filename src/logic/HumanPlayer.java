package logic;

import java.rmi.RemoteException;

import checkersApi.User;

public class HumanPlayer extends Player {

	private User user;

	public HumanPlayer(Color color, User user) {
		super(color);
		this.user = user;
	}

	@Override
	public void notifyMeAboutStart(Chessboard startingChessboard) {
		try {
			user.onGameStateChanged("Rozpoczecie gry. Ruch białego gracza.\n" + startingChessboard.toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sendMeNewGameState(Chessboard newState) {
		try {
			user.onGameStateChanged(newState.toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
