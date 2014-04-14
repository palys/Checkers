package checkersServer;

import java.rmi.RemoteException;
import java.security.InvalidParameterException;

import Utils.Logger;
import logic.Color;
import logic.GameLogic;
import logic.HumanPlayer;
import logic.Move;
import logic.Player;
import checkersApi.Game;
import checkersApi.User;
import checkersApi.UserToken;
import checkersClient.UserImpl;

public class GameImpl implements Game {
	
	private GameLogic gameLogic = null;
	
	private UserTokenImpl white = null;
	
	private UserTokenImpl black = null;

	@Override
	public UserToken playVsBot(User user) throws RemoteException {
		
		Player player = new HumanPlayer(Color.WHITE, user);
		
		if (gameLogic == null) {
			gameLogic = GameLogic.vsBot(8, player);
		} else {
			return null;
		}
		
		white = new UserTokenImpl(player);
		return white;
	}

	@Override
	public UserTokenImpl playVsPlayer(User user) throws RemoteException {
		
		if (gameLogic == null) {
			Player player = new HumanPlayer(Color.WHITE, user);
			gameLogic = GameLogic.vsPlayer(8, player);
			white = new UserTokenImpl(player);
			return white;
		} else {
			Player player = new HumanPlayer(Color.BLACK, user);
			boolean hasJoined = gameLogic.join(player);
			
			if (hasJoined) {
				black = new UserTokenImpl(player);
				return black;
			} else {
				return null;
			}
		}
	}

	@Override
	public boolean tryToMakeMove(UserTokenImpl userToken, int fromX, int fromY,
			int toX, int toY) throws RemoteException {
		
		if (userToken == null) {
			Logger.log("Invalid user token");
			return false;
		}
		
		if (userToken.equals(black) || userToken.equals(white)) {
			Player player = userToken.getPlayer();
			Move move = null;
			
			try {
				move = Move.newMove(fromX, fromY, toX, toY);
			} catch (InvalidParameterException e) {
				return false;
			}
			
			return gameLogic.doMove(move, player);
		}
		
		
		
		return false;
	}
	
	public static void main(String[] args) {
		User user1 = new UserImpl();
		UserTokenImpl token1 = null;
		
		User user2 = new UserImpl();
		UserTokenImpl token2 = null;
		
		Game game = new GameImpl();
		
		try {
			token1 = game.playVsPlayer(user1);
			token2 = game.playVsPlayer(user2);
			
			game.tryToMakeMove(token1, 2, 2, 3, 3);
			game.tryToMakeMove(token2, 3, 7, 2, 6);
			game.tryToMakeMove(token1, 3, 3, 2, 4);
			game.tryToMakeMove(token2, 2, 6, 3, 5);
			game.tryToMakeMove(token1, 2, 4, 4, 6);
			game.tryToMakeMove(token2, 5, 7, 3, 5);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
