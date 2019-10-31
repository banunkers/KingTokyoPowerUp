package tests.game.evolutioncard;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import card.evolutioncard.EvolCard;
import card.evolutioncard.implemented.kraken.EaterOfSouls;
import card.evolutioncard.implemented.kraken.HealingRain;
import game.GamePhase;
import monster.Monster;
import monster.implemented.Kong;
import monster.implemented.Kraken;
import player.Player;

public class KrakenTests {

	@Test
	public void healingRain() {
		Player krakenPlayer = new Player();
		krakenPlayer.setMonster(new Kraken());
		Player otherPlayer = new Player();
		otherPlayer.setMonster(new Kong());
		Monster kraken = krakenPlayer.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(kraken);
		monsters.add(otherMon);

		ArrayList<EvolCard> evolCards = new ArrayList<EvolCard>();
		evolCards.add(new HealingRain());
		kraken.setEvolCards(evolCards);

		kraken.decHealth(5);
		otherMon.decHealth(5);
		int krakenPreHealth = kraken.getHealth();
		int otherPreHealth = kraken.getHealth();

		kraken.activateEvolCard(monsters);
		assertEquals(krakenPreHealth + 2, kraken.getHealth());
		assertEquals(otherPreHealth, otherMon.getHealth());
	}

	@Test
	public void eaterOfSouls() {
		Player krakenPlayer = new Player();
		krakenPlayer.setMonster(new Kraken());
		Player otherPlayer = new Player();
		otherPlayer.setMonster(new Kong());
		Monster kraken = krakenPlayer.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(kraken);
		monsters.add(otherMon);

		ArrayList<EvolCard> evolCards = new ArrayList<EvolCard>();
		evolCards.add(new EaterOfSouls());
		kraken.setEvolCards(evolCards);
		
		GamePhase gamePhase = new GamePhase();
		
		// Check max health now +2
		int krakenPreMaxHealth = kraken.getMaxHealth();
		kraken.activateEvolCard(monsters);
		assertEquals(krakenPreMaxHealth + 2, kraken.getMaxHealth());

		int krakenPreHealth = kraken.getHealth();
		kraken.setInTokyo(gamePhase, true, monsters);	// take control of Tokyo
		assertEquals(krakenPreHealth + 1, kraken.getHealth());

		kraken.setInTokyo(gamePhase, false, monsters); // yield Tokyo
		assertEquals(krakenPreHealth + 1, kraken.getHealth());

		kraken.setInTokyo(gamePhase, true, monsters);	// take control of Tokyo
		assertEquals(krakenPreHealth + 2, kraken.getHealth());
	}
}