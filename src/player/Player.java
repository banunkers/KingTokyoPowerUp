package player;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

import monster.Monster;

/**
 * The player who plays the game
 */
public class Player {
	public Socket connection = null;
	public BufferedReader inFromClient = null;
	public DataOutputStream outToClient = null;
	private Monster monster;

	public Player(Monster monster) {
		this.monster = monster;
	}

	public Monster getMonster() {
		return monster;
	}
}