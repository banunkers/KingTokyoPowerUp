package tests.game.evolutioncard;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import card.evolutioncard.EvolCard;
import card.evolutioncard.implemented.kong.IronCurtain;
import card.evolutioncard.implemented.kong.RedDawn;
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

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(kong);
		monsters.add(otherMon);

		// Give Kong Iron Curtain evolution card
		kong.addActiveEvolCard(new IronCurtain(monsters));
		
		GamePhase gamePhase = new GamePhase(monsters);
		otherMon.setInTokyo(gamePhase, false);	// Other monster yields Tokyo
		assertEquals(otherMon.getMaxHealth() - 1, otherMon.getHealth());	// other monster should have lost 1 health
		kong.setInTokyo(gamePhase, true);
		assertEquals(kong.getMaxHealth(), kong.getHealth());
		kong.setInTokyo(gamePhase, false);	// Kong yields Tokyo
		assertEquals(kong.getMaxHealth(), kong.getHealth());	// Kong should still be at full health
	}

	@Test
	public void redDawn() {
		Player kongPlayer = new Player(new Kong());
		Player otherPlayer = new Player(new Alienoid());
		Monster kong = kongPlayer.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(kong);
		monsters.add(otherMon);

		// Give Kong Red Dawn evolution card
		ArrayList<EvolCard> evolCards = new ArrayList<EvolCard>();
		evolCards.add(new RedDawn(monsters));
		kong.setEvolCards(evolCards);
		
		// Activate and assert
		kong.activateEvolCard();
		assertEquals(kong.getMaxHealth(), kong.getHealth());	// Kong still full health
		assertEquals(otherMon.getMaxHealth() - 2, otherMon.getHealth());	// Other monster damaged for 2
	}
}