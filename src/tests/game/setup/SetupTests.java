package tests.game.setup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;

import card.Deck;
import card.storecard.StoreCard;
import monster.Monster;
import monster.MonsterFactory;
import player.Player;

public class SetupTests {

	@Test
	// ID[1]: Each player is assigned a monster
	public void playerAssignedMonster() {
		MonsterFactory monFactory = new MonsterFactory();
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		// Get 5 monsters
		try {
			monsters = monFactory.getMonsters(4);
		} catch (Exception e) {
		}

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		// Assert that each player was assigned a monster
		for (int i = 0; i < players.size(); i++) {
			assertEquals(monsters.get(i), players.get(i).getMonster());
		}
	}

	@Test
	// ID[2]: Set victory points to 0
	public void checkVictoryPoints() {
		MonsterFactory monFactory = new MonsterFactory();
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		// Get 5 monsters
		try {
			monsters = monFactory.getMonsters(4);
		} catch (Exception e) {
		}

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		for (Player player : players) {
			assertEquals(0, player.getMonster().getStars());
		}
	}

	@Test
	// ID[3]: Set health to 10
	public void startingHealth() {
		MonsterFactory monFactory = new MonsterFactory();
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		
		// Get 4 monsters
		try {
			monsters = monFactory.getMonsters(4);
		} catch (Exception e) {
		}

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		for (Player player : players) {
			assertEquals(10, player.getMonster().getHealth());
		}
	}

	@Test
	// ID[4]: Start with 3 cards face up
	public void startingStoreCards() {
		MonsterFactory monFactory = new MonsterFactory();
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		
		// Get 4 monsters
		try {
			monsters = monFactory.getMonsters(4);
		} catch (Exception e) {
		}

		Deck deck = new Deck(monsters);
		
		ArrayList<StoreCard> store = deck.getStore();

		// Check that each card in the store is not null
		for (StoreCard card : store) {
			assertNotNull(card);
		}

		// Check that there are 3 cards in the store
		assertEquals(3, store.size());
	}	
}
