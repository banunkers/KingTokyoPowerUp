package tests.game.storecard;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import card.storecard.implemented.AcidAttack;
import card.storecard.implemented.AlienMetabolism;
import card.storecard.implemented.AlphaMonster;
import card.storecard.implemented.ApartmentBuilding;
import card.storecard.implemented.ArmorPlating;
import card.storecard.implemented.ComuterTrain;
import card.storecard.implemented.CornerStore;
import game.GamePhase;
import game.Phase;
import monster.Monster;
import monster.implemented.Kong;
import monster.implemented.Kraken;
import player.Player;

public class StoreCardTests {
	
	@Test
	public void acidAttack() {
		Player player = new Player();
		player.setMonster(new Kraken());
		Player otherPlayer = new Player();
		otherPlayer.setMonster(new Kong());
		Monster mon = player.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(mon);
		monsters.add(otherMon);

		mon.addStoreCard(new AcidAttack());
		
		GamePhase gamePhase = new GamePhase();

		int otherPreHealth = otherMon.getHealth();
		int monPreHealth = mon.getHealth();
		int monPreMoreDamage = mon.getMoreDamage();
		int otherPreMoreDamage = otherMon.getMoreDamage();

		// Monster did not roll any CLAWS and in Tokyo
		mon.setInTokyo(gamePhase, true, monsters);
		gamePhase.setPhase(Phase.ATTACKING_NO_CLAW, mon, null, monsters);
		assertEquals(otherPreHealth - 1, otherMon.getHealth());
		assertEquals(monPreHealth, mon.getHealth());

		// Monster rolled CLAWS
		gamePhase.setPhase(Phase.ATTACKING, mon, null, monsters);
		assertEquals(monPreMoreDamage + 1, mon.getMoreDamage());
		assertEquals(otherPreMoreDamage, otherMon.getMoreDamage());

		// Should not continue giving +1 attack
		gamePhase.setPhase(Phase.ATTACKING, mon, null, monsters);
		assertEquals(monPreMoreDamage + 1, mon.getMoreDamage());

		// Monster outside Tokyo and no CLAWS and no one in Tokyo
		mon.setInTokyo(gamePhase, false, monsters);
		gamePhase.setPhase(Phase.ATTACKING_NO_CLAW, mon, null, monsters);
		assertEquals(otherPreHealth - 1, otherMon.getHealth());

		// Monster outside Tokyo and no CLAWS and other monster in Tokyo
		otherMon.setInTokyo(gamePhase, true, monsters);
		gamePhase.setPhase(Phase.ATTACKING_NO_CLAW, mon, null, monsters);
		assertEquals(otherPreHealth - 2, otherMon.getHealth());
	}

	@Test
	public void alienMetabolism() {
		Player player = new Player();
		player.setMonster(new Kraken());
		Player otherPlayer = new Player();
		otherPlayer.setMonster(new Kong());
		Monster mon = player.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(mon);
		monsters.add(otherMon);

		mon.addStoreCard(new AlienMetabolism());
		
		GamePhase gamePhase = new GamePhase();

		int preCost = mon.getCostReduction();
		gamePhase.setPhase(Phase.BUYING, mon, null, monsters);
		assertEquals(preCost + 1, mon.getCostReduction());

		gamePhase.setPhase(Phase.BUYING, mon, null, monsters);
		assertEquals(preCost + 1, mon.getCostReduction());
	}

	@Test
	public void alphaMonster() {
		Player player = new Player();
		player.setMonster(new Kraken());
		Player otherPlayer = new Player();
		otherPlayer.setMonster(new Kong());
		Monster mon = player.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(mon);
		monsters.add(otherMon);

		mon.addStoreCard(new AlphaMonster());
		
		GamePhase gamePhase = new GamePhase();

		int preStars = mon.getStars();
		gamePhase.setPhase(Phase.ATTACKING, mon, null, monsters);
		assertEquals(preStars + 1, mon.getStars());

		gamePhase.setPhase(Phase.ATTACKING, mon, null, monsters);
		assertEquals(preStars + 2, mon.getStars());
	}

	@Test
	public void apartmentBuilding() {
		Player player = new Player();
		player.setMonster(new Kraken());
		Player otherPlayer = new Player();
		otherPlayer.setMonster(new Kong());
		Monster mon = player.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(mon);
		monsters.add(otherMon);

		mon.addStoreCard(new ApartmentBuilding());
		
		GamePhase gamePhase = new GamePhase();

		int preStars = mon.getStars();
		gamePhase.setPhase(Phase.DISCARDING, mon, null, monsters);
		assertEquals(preStars + 3, mon.getStars());
	}

	@Test
	public void armorPlating() {
		Player player = new Player();
		player.setMonster(new Kraken());
		Player otherPlayer = new Player();
		otherPlayer.setMonster(new Kong());
		Monster mon = player.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(mon);
		monsters.add(otherMon);

		mon.addStoreCard(new ArmorPlating());
		
		GamePhase gamePhase = new GamePhase();

		int preHealth = mon.getHealth();
		int preArmor = mon.getArmor();

		gamePhase.setPhase(Phase.TAKING_DAMAGE, mon, null, monsters);

		mon.decHealth(1);
		otherMon.decHealth(1);
		assertEquals(preHealth, mon.getHealth());
		assertEquals(preArmor + 1, mon.getArmor());
		assertEquals(9, otherMon.getHealth());

		gamePhase.setPhase(Phase.TAKING_DAMAGE, mon, null, monsters);
		assertEquals(preArmor + 1, mon.getArmor());

		mon.decHealth(2);
		assertEquals(preHealth - 2, mon.getHealth());
	}

	@Test
	public void comuterTrain() {
		Player player = new Player();
		player.setMonster(new Kraken());
		Player otherPlayer = new Player();
		otherPlayer.setMonster(new Kong());
		Monster mon = player.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(mon);
		monsters.add(otherMon);

		mon.addStoreCard(new ComuterTrain());
		
		GamePhase gamePhase = new GamePhase();

		int preStars = mon.getStars();
		gamePhase.setPhase(Phase.DISCARDING, mon, null, monsters);
		assertEquals(preStars + 2, mon.getStars());
	}

	@Test
	public void cornerStore() {
		Player player = new Player();
		player.setMonster(new Kraken());
		Player otherPlayer = new Player();
		otherPlayer.setMonster(new Kong());
		Monster mon = player.getMonster();
		Monster otherMon = otherPlayer.getMonster();

		ArrayList<Monster> monsters = new ArrayList<Monster>();
		monsters.add(mon);
		monsters.add(otherMon);

		mon.addStoreCard(new CornerStore());
		
		GamePhase gamePhase = new GamePhase();

		int preStars = mon.getStars();
		gamePhase.setPhase(Phase.DISCARDING, mon, null, monsters);
		assertEquals(preStars + 1, mon.getStars());
	}
}