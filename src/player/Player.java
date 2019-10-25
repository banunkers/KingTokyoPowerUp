package player;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import card.Card;
import card.Effect;
import monster.Monster;

/**
 * The player who plays the game
 */
public class Player {
	public Socket connection = null;
	public BufferedReader inFromClient = null;
	public DataOutputStream outToClient = null;
	public ArrayList<Card> cards = new ArrayList<Card>();
	private Monster monster;

	public Player(Monster monster) {
		this.monster = monster;
	}

	public Monster getMonster() {
		return monster;
	}

	public String cardsToString() {
		String returnString = "";
		if (cards.size() == 0)
			return "[NO CARDS]:";
		for (int i = 0; i < cards.size(); i++) {
			returnString += "\t[" + i + "] " + cards.get(i) + ":";
		}
		return returnString;
	}

	// search all available cards and return the effect value of an effect
	public int cardEffect(String effectName) {
		for (int i = 0; i < cards.size(); i++){
			try {
				// Find variable by "name"
				if (Effect.class.getField(effectName).getInt(cards.get(i).effect) > 0) {
					return Effect.class.getField(effectName).getInt(cards.get(i).effect);
				}
			} catch (Exception e) {
			}
		}
		return 0;
	}
}