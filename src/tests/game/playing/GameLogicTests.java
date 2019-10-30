package tests.game.playing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;

import dice.Dice;
import dice.Util;
import game.GamePhase;
import game.RuleBook;
import monster.Monster;
import monster.MonsterFactory;
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
			players.add(new Player(monster));
		}

		RuleBook ruleBook = new RuleBook(players, monsters);
		GamePhase gamePhase = new GamePhase(monsters);
		
		// Place one of the monsters inside tokyo
		Monster inTokyoMon = monsters.get(0);
		int preStars = inTokyoMon.getStars();
		inTokyoMon.setInTokyo(gamePhase, true);
		ruleBook.startingPhase(inTokyoMon, gamePhase);
		assertEquals(preStars + 1, inTokyoMon.getStars());
		ruleBook.startingPhase(inTokyoMon, gamePhase);
		assertEquals(preStars + 2, inTokyoMon.getStars());
		
		// Monster not in Tokyo no star at start of turn
		ruleBook.startingPhase(monsters.get(1), gamePhase);
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
	public void resolveDice() {

	}
}