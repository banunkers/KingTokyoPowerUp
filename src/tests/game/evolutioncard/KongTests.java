package tests.game.evolutioncard;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import card.evolutioncard.implemented.kong.IronCurtain;
import game.GamePhase;
import player.Player;
import monster.Monster;
import monster.implemented.*;

public class KongTests {
	@Test
	public void ironCurtain() {
		Player kongPlayer = new Player(new Kong());
		Player otherPlayer = new Player(new Alienoid());
		Monster kong = kongPlayer.getMonster();
		Monster otherMon = otherPlayer.getMonster();
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(kongPlayer);
		players.add(otherPlayer);

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(kong);
		monsters.add(otherMon);

		// Give Kong Iron Curtain evolution card
		kong.addActiveEvolCard(new IronCurtain(monsters));
		System.out.println(kong.getActiveEvolCards());
		
		GamePhase gamePhase = new GamePhase(monsters);
		System.out.println(monsters);
		otherMon.setInTokyo(gamePhase, false);	// Other monster yields Tokyo
		assertEquals(9, otherMon.getHealth());
	}
}