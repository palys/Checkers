package checkersServer;

import checkersApi.UserToken;
import logic.Player;

public class UserTokenImpl implements UserToken {
	
	private Player player;
	
	public UserTokenImpl(Player player) {
		this.player = player;
	}
	
	Player getPlayer() {
		return player;
	}

}
