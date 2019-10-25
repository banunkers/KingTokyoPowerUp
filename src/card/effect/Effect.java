package card.effect;

import java.util.ArrayList;

// import game.Game;
import monster.Monster;

public interface Effect {

	public void execute(Monster monster, ArrayList<Monster> monsters, GamePase phase);

	// public boolean conditions(Monster monster, GamePhase phase);
}