package tests.game.playing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import card.Deck;
import card.evolutioncard.EvolCard;
import card.evolutioncard.implemented.kong.RedDawn;
import card.storecard.StoreCard;
import dice.Dice;
import dice.Util;
import game.GamePhase;
import game.RuleBook;
import monster.Monster;
import monster.MonsterFactory;
import monster.implemented.Alienoid;
import monster.implemented.Gigazaur;
import monster.implemented.Kong;
import monster.implemented.Kraken;
import player.Player;

public class GameLogicTests {

	@Test
	// ID[7]: If your monster is inside Tokyo - Gain 1 Star
	public void insideTokyo() {
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

		RuleBook ruleBook = new RuleBook();
		GamePhase gamePhase = new GamePhase();
		
		// Place one of the monsters inside tokyo
		Monster inTokyoMon = monsters.get(0);
		int preStars = inTokyoMon.getStars();
		inTokyoMon.setInTokyo(gamePhase, true, monsters);
		ruleBook.startingPhase(inTokyoMon, gamePhase, monsters);
		assertEquals(preStars + 1, inTokyoMon.getStars());
		ruleBook.startingPhase(inTokyoMon, gamePhase, monsters);
		assertEquals(preStars + 2, inTokyoMon.getStars());
		
		// Monster not in Tokyo no star at start of turn
		ruleBook.startingPhase(monsters.get(1), gamePhase, monsters);
		assertEquals(0, monsters.get(1).getStars());
	}

	@Test
	// ID[8]: Roll your 6 dice
	public void rollSixDice() {
		Util diceUtil = new Util();
		ArrayList<Dice> rolledDice = diceUtil.roll(6);

		for (Dice dice : rolledDice) {
			assertNotNull(dice);
		}

		// check that it's 6 dice
		assertEquals(6, rolledDice.size());
	}

	@Test
	// ID[9]: Select which of your dice to reroll
	public void selectReroll() {
		Util diceUtil = new Util();
		ArrayList<Dice> rolledDice = diceUtil.roll(6);

		// Select to reroll the 6th dice
		String[] reroll = "6".split(",");
		diceUtil.selectReroll(rolledDice, reroll);
		assertEquals(5, rolledDice.size());

		rolledDice = diceUtil.roll(6);
		reroll = "6,5,4,3,2,1".split(",");
		diceUtil.selectReroll(rolledDice, reroll);
		assertEquals(0, rolledDice.size());
	}

	@Test
	// ID[10]: Reroll selected dice
	public void reroll() {
		Util diceUtil = new Util();
		ArrayList<Dice> rolledDice = diceUtil.roll(6);

		// Select to reroll the 6th dice and check that it gets replaced with a new dice
		String[] reroll = "6".split(",");
		diceUtil.selectReroll(rolledDice, reroll);
		diceUtil.replaceRerolled(rolledDice);
		assertEquals(6, rolledDice.size());

		rolledDice = diceUtil.roll(6);
		reroll = "6,5,4,3,2,1".split(",");
		diceUtil.selectReroll(rolledDice, reroll);
		diceUtil.replaceRerolled(rolledDice);
		assertEquals(6, rolledDice.size());
	}

	@Test
	// ID[12]: Resolve rolled dice
	public void resolveDice() throws Exception {
		try {
			checkHeart();
		} catch (Exception e) {
			throw e;
		}

		try {
			checkNums();
		} catch (Exception e) {
			throw e;
		}

		try {
			checkEnergy();
		} catch (Exception e) {
			throw e;
		}
		
		try {
			checkClaws();
		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	// ID[13]: Buying cards
	public void buyingCards() throws Exception {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(new Kong());

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		Monster mon = monsters.get(0);
		RuleBook ruleBook = new RuleBook();
		Deck deck = new Deck(monsters);
		GamePhase gamePhase = new GamePhase();
		@SuppressWarnings("unchecked") ArrayList<StoreCard> storePreCards = (ArrayList<StoreCard>) deck.getStore().clone();

		try {
			Method buy = RuleBook.class.getDeclaredMethod("buy", Monster.class, int.class, Deck.class, GamePhase.class, ArrayList.class);
			buy.setAccessible(true);

			// Buyng card with no energy
			int buyIndex = 1;	// Some random card in store
			boolean bought = (boolean) buy.invoke(ruleBook, mon, buyIndex, deck, gamePhase, monsters);
			assertEquals(false, bought);

			// Reset store
			buyIndex = 3; // user input of 3 will reset the store for 2 energy
			mon.incEnergy(2);
			bought = (boolean) buy.invoke(ruleBook, mon, buyIndex, deck, gamePhase, monsters);
			assertEquals("Didnt buy anything when reset", false, bought); // Since only reset store nothing should be bought
			assertFalse("Same cards in store after reset", deck.getStore().equals(storePreCards));

			// Buy a card with sufficient energy
			mon.incEnergy(10);
			buyIndex = 2; // Random card
			int cardCost = deck.getStore().get(2).getCost();
			bought = (boolean) buy.invoke(ruleBook, mon, buyIndex, deck, gamePhase, monsters);
			assertEquals("Buying a card with enough energy", true, bought);
			assertEquals("Player payed the card cost", 10 - cardCost, mon.getEnergy());
			assertEquals("Store still contains three cards", 3, deck.getStore().size());
		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	// ID[14]: Store card Keep or Discard
	public void storeCard() throws Exception {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(new Kong());

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		Monster mon = monsters.get(0);
		RuleBook ruleBook = new RuleBook();
		Deck deck = new Deck(monsters);
		GamePhase gamePhase = new GamePhase();

		try {
			Method buy = RuleBook.class.getDeclaredMethod("buy", Monster.class, int.class, Deck.class, GamePhase.class, ArrayList.class);
			buy.setAccessible(true);

			// Buy a card with sufficient energy
			mon.incEnergy(100);
			boolean boughtDiscard = false;
			
			// Loop until DISCARD card is bought
			while(!boughtDiscard) {
				for (int i = 0; i < deck.getStore().size(); i++) {
					if (deck.getStore().get(i).isDiscard()) {
						boughtDiscard = true;
						boolean bought = (boolean) buy.invoke(ruleBook, mon, i, deck, gamePhase, monsters);
						assertEquals("Player bought the discard card", true, bought);
						break;
					}
				}
				deck.resetStore();
			}
			// Check that the discard card doesn't stay on hand
			assertEquals("Bought DISCARD card was discarded", 0, mon.getStoreCards().size());

			mon.incEnergy(100);
			// Loop until KEEP card is bought
			boolean boughtKeep = false;
			while(!boughtKeep) {
				for (int i = 0; i < deck.getStore().size(); i++) {
					if (!deck.getStore().get(i).isDiscard()) {
						boughtKeep = true;
						boolean bought = (boolean) buy.invoke(ruleBook, mon, i, deck, gamePhase, monsters);
						assertEquals("Player bought the keep card", true, bought);
						break;
					}
				}
				deck.resetStore();
			}
			
			// Check that KEEP card is still in hand
			assertEquals("Bout KEEP card still on hand", 1, mon.getStoreCards().size());
		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	// ID[16]: First monster to get 20 stars wins the game
	public void starVictory() throws Exception {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(new Kong());
		monsters.add(new Alienoid());
		monsters.add(new Gigazaur());
		monsters.add(new Kraken());

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		Monster mon = monsters.get(0);
		RuleBook ruleBook = new RuleBook();

		try {
			Method victoryByStars = RuleBook.class.getDeclaredMethod("victoryByStars", ArrayList.class);
			victoryByStars.setAccessible(true);
			
			// All monsters 0 stars and full health => no winner
			boolean hasWinner  = victoryByStars.invoke(ruleBook, players) == null ? false : true;
			assertEquals("Game not over", false, hasWinner);

			// Monster with 20 stars
			mon.incStars(20);
			Player winner = (Player) victoryByStars.invoke(ruleBook, players);
			assertEquals("Victory by stars", mon, winner.getMonster());	
		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	// ID[17]: The sole surviving monster wins the game
	public void survivingVictory() throws Exception {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(new Kong());
		monsters.add(new Alienoid());
		monsters.add(new Gigazaur());
		monsters.add(new Kraken());

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		Monster mon = monsters.get(0);
		RuleBook ruleBook = new RuleBook();

		try {
			Method victoryBySurv = RuleBook.class.getDeclaredMethod("victoryBySurviving", ArrayList.class);
			victoryBySurv.setAccessible(true);
			
			// All monsters 0 stars and full health => no winner
			boolean hasWinner  = victoryBySurv.invoke(ruleBook, players) == null ? false : true;
			assertEquals("Game not over when all full health", false, hasWinner);

			// All monsters 0 health except one
			for (Monster monster : monsters) {
				monster.setHealth(0);
			}
			mon.setHealth(10);
			Player winner = (Player) victoryBySurv.invoke(ruleBook, players);
			assertEquals("Victory by surviving", mon, winner.getMonster());	
		} catch (Exception e) {
			throw e;
		}
	}

	@Test
	// ID[18]: A monster that reaches 0 health is out of the game
	public void outOfGame() {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(new Kong());

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		Monster mon = monsters.get(0);
		RuleBook ruleBook = new RuleBook();
		GamePhase gamePhase = new GamePhase();

		// Monster with non-zero health is still considered alive
		assertEquals("Monster with non-zero health still alive", true, ruleBook.startingPhase(mon, gamePhase, monsters));

		// Monster with zero health is considered dead and thus will be skipped during game loop
		mon.setHealth(0);
		assertEquals("Monster with zero health considered dead", false, ruleBook.startingPhase(mon, gamePhase, monsters));
	}

	public void checkHeart() throws Exception {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(new Kong());

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		Monster mon = monsters.get(0);
		ArrayList<EvolCard> evolCard = new ArrayList<EvolCard>();
		evolCard.add(new RedDawn());
		mon.setEvolCards(evolCard);
		RuleBook ruleBook = new RuleBook();
		GamePhase gamePhase = new GamePhase();
		HashMap<Dice, Integer> diceResult = new HashMap<Dice, Integer>();
		
		// Check rolling HEART
		try {
			Method resolveHearts = RuleBook.class.getDeclaredMethod("resolveHearts", Monster.class, HashMap.class, ArrayList.class);
			resolveHearts.setAccessible(true);

			// Rolled HEART not in Tokyo
			mon.decHealth(5);
			int preHealth = mon.getHealth();
			diceResult.put(new Dice(Dice.HEART), 2);
			resolveHearts.invoke(ruleBook, mon, diceResult, monsters);
			assertEquals(preHealth + 2, mon.getHealth());
			mon.setHealth(10);

			// Rolled 3 HEART => evol card + 3 health
			mon.decHealth(5);
			preHealth = mon.getHealth();
			diceResult.clear();
			diceResult.put(new Dice(Dice.HEART), 3);
			EvolCard output = (EvolCard) resolveHearts.invoke(ruleBook, mon, diceResult, monsters);
			assertEquals(preHealth + 3, mon.getHealth());	// Healed for 3
			assertNotNull(output);	// Activated an evolution

			// Health not more than max (mon starting with 7 HP)
			mon.setHealth(7);
			mon.getMaxHealth();
			diceResult.clear();
			diceResult.put(new Dice(Dice.HEART), 6);
			resolveHearts.invoke(ruleBook, mon, diceResult, monsters);
			assertEquals(mon.getMaxHealth(), mon.getHealth());	// At max Health

			// Rolled HEART in Tokyo
			mon.setHealth(5);
			preHealth = mon.getHealth();
			mon.setInTokyo(gamePhase, true, monsters);
			diceResult.clear();
			diceResult.put(new Dice(Dice.HEART), 1);
			resolveHearts.invoke(ruleBook, mon, diceResult, monsters);
			assertEquals(preHealth, mon.getHealth());	// Didnt heal
		} catch (Exception e) {
			throw e;
		}
	}

	public void checkNums() throws Exception {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(new Kong());

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		Monster mon = monsters.get(0);
		RuleBook ruleBook = new RuleBook();
		HashMap<Dice, Integer> diceResult = new HashMap<Dice, Integer>();

		try {
			Method resolveNums = RuleBook.class.getDeclaredMethod("resolveNums", Monster.class, HashMap.class);
			resolveNums.setAccessible(true);
			
			// Rolled 1 ONE
			diceResult.put(new Dice(1), 1);
			int preStars = mon.getStars();
			resolveNums.invoke(ruleBook, mon, diceResult);
			assertEquals(preStars, mon.getStars());

			// Rolled 3 ONE
			diceResult.clear();
			diceResult.put(new Dice(1), 3);
			resolveNums.invoke(ruleBook, mon, diceResult);
			assertEquals(preStars + 1, mon.getStars());

			// Rolled 6 ONE
			diceResult.clear();
			diceResult.put(new Dice(1), 6);
			preStars = mon.getStars();
			resolveNums.invoke(ruleBook, mon, diceResult);
			assertEquals(preStars + 4, mon.getStars());

			mon.setStars(0);
			preStars = mon.getStars();

			// Rolled 3 TWO
			diceResult.clear();
			diceResult.put(new Dice(2), 3);
			resolveNums.invoke(ruleBook, mon, diceResult);
			assertEquals(preStars + 2, mon.getStars());

			// Rolled 6 TWO
			diceResult.clear();
			diceResult.put(new Dice(2), 6);
			preStars = mon.getStars();
			resolveNums.invoke(ruleBook, mon, diceResult);
			assertEquals(preStars + 5, mon.getStars());

			mon.setStars(0);
			preStars = mon.getStars();

			// Rolled 3 THREE
			diceResult.clear();
			diceResult.put(new Dice(3), 3);
			resolveNums.invoke(ruleBook, mon, diceResult);
			assertEquals(preStars + 3, mon.getStars());

			// Rolled 6 TWO
			diceResult.clear();
			diceResult.put(new Dice(3), 6);
			preStars = mon.getStars();
			resolveNums.invoke(ruleBook, mon, diceResult);
			assertEquals(preStars + 6, mon.getStars());

		} catch (Exception e) {
			throw e;
		}
	}

	public void checkEnergy() throws Exception {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(new Kong());

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		Monster mon = monsters.get(0);
		RuleBook ruleBook = new RuleBook();
		HashMap<Dice, Integer> diceResult = new HashMap<Dice, Integer>();

		try {
			Method resolveNums = RuleBook.class.getDeclaredMethod("resolveEnergy", Monster.class, HashMap.class);
			resolveNums.setAccessible(true);

			for (int i = 0; i < 7; i++) {
				int preEnergy = mon.getEnergy();
				diceResult.clear();
				diceResult.put(new Dice(Dice.ENERGY), i);
				resolveNums.invoke(ruleBook, mon, diceResult);
				assertEquals(preEnergy + i, mon.getEnergy());
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public void checkClaws() throws Exception {
		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(new Kong());
		monsters.add(new Alienoid());
		monsters.add(new Gigazaur());
		monsters.add(new Kraken());

		ArrayList<Player> players = new ArrayList<Player>();
		for (Monster monster : monsters) {
			Player player = new Player();
			player.setMonster(monster);
			players.add(player);
		}

		Monster mon = monsters.get(0);
		RuleBook ruleBook = new RuleBook();
		HashMap<Dice, Integer> diceResult = new HashMap<Dice, Integer>();
		GamePhase gamePhase = new GamePhase();

		try {
			Method resolveClaw = RuleBook.class.getDeclaredMethod("resolveClaw", Monster.class, HashMap.class, GamePhase.class, ArrayList.class, ArrayList.class);
			resolveClaw.setAccessible(true);

			// Monster attacking in Tokyo
			mon.setInTokyo(gamePhase, true, monsters);
			for (int i = 0; i < 7; i++) {
				diceResult.clear();
				diceResult.put(new Dice(Dice.CLAWS), i);
				for (Monster monster : monsters) {
					monster.setHealth(10);
				}
				resolveClaw.invoke(ruleBook, mon, diceResult, gamePhase, monsters, players);
				for (Monster monster : monsters) {
					if (monster.equals(mon)) {
						assertEquals(10, monster.getHealth());
					} else {
						assertEquals(10 - i, monster.getHealth());
					}
				}
			}

			// Monster attacking not in Tokyo but Tokyo occupied
			mon.setInTokyo(gamePhase, false, monsters);
			Monster otherMonInTokyo = monsters.get(1);
			otherMonInTokyo.setInTokyo(gamePhase, true, monsters);
			for (int i = 0; i < 7; i++) {
				diceResult.clear();
				diceResult.put(new Dice(Dice.CLAWS), i);
				for (Monster monster : monsters) {
					monster.setHealth(10);
				}
				resolveClaw.invoke(ruleBook, mon, diceResult, gamePhase, monsters, players);
				for (Monster monster : monsters) {
					if (!monster.isInTokyo()) {
						assertEquals(10, monster.getHealth());
					} else {
						assertEquals(10 - i, monster.getHealth());
					}
				}
			}

			// Monster attacking not in Tokyo and Tokyo unoccupied
			otherMonInTokyo.setInTokyo(gamePhase, false, monsters);
			for (int i = 0; i < 7; i++) {
				int preStars = mon.getStars();
				mon.setInTokyo(gamePhase, false, monsters);
				diceResult.clear();
				diceResult.put(new Dice(Dice.CLAWS), i);
				for (Monster monster : monsters) {
					monster.setHealth(10);
				}
				resolveClaw.invoke(ruleBook, mon, diceResult, gamePhase, monsters, players);
				for (Monster monster : monsters) {
					assertEquals(10, monster.getHealth());
				}

				assertEquals(preStars + 1, mon.getStars());
			}
		} catch (Exception e) {
			throw e;
		}
	}
}