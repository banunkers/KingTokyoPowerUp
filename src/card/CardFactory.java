package card;

import java.util.ArrayList;
import java.util.Collections;

import card.evolutioncard.EvolCard;
import card.storecard.StoreCard;
import card.storecard.implemented.*;
import card.evolutioncard.implemented.alienoid.AlienScourge;
import card.evolutioncard.implemented.alienoid.FunnyLookingButDangerous;
import card.evolutioncard.implemented.gigazaur.DefenderOfTokyo;
import card.evolutioncard.implemented.gigazaur.RadioactiveWaste;
import card.evolutioncard.implemented.kong.IronCurtain;
import card.evolutioncard.implemented.kong.RedDawn;
import card.evolutioncard.implemented.kraken.EaterOfSouls;
import card.evolutioncard.implemented.kraken.HealingRain;
import monster.Monster;

/**
 * Creates all of the implemented store cards
 */
public class CardFactory {
	private ArrayList<Monster> monsters;

	public CardFactory(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}

	/**
	 * Creates a shuffled array list containing all of the currently implemented store cards
	 * @return the shuffled store cards
	 */
	public ArrayList<StoreCard> getStoreCards() {
		ArrayList<StoreCard> cards = new ArrayList<StoreCard>();
		cards.add(new AcidAttack(monsters));
		cards.add(new AlienMetabolism(monsters));
		cards.add(new AlphaMonster(monsters));
		cards.add(new ApartmentBuilding(monsters));
		cards.add(new ArmorPlating(monsters));
		cards.add(new ComuterTrain(monsters));
		cards.add(new CornerStore(monsters));
		Collections.shuffle(cards);
		return cards;
	}

	/**
	 * Creates a shuffled array list containing all of the currently implemented evolution cards
	 * for the specified monster
	 * @param monster the monster to generate evolution cards for
	 * @return the shuffled array list of evolution cards
	 */
	public ArrayList<EvolCard> getEvolCards(Monster monster) {
		ArrayList<EvolCard> cards = new ArrayList<EvolCard>();
		switch (monster.getName()) {
			case "Alienoid":
				cards.add(new AlienScourge(monsters));
				cards.add(new FunnyLookingButDangerous(monsters));
				break;
			case "Gigazaur":
				cards.add(new DefenderOfTokyo(monsters));
				cards.add(new RadioactiveWaste(monsters));
				break;
			case "Kong":
				cards.add(new IronCurtain(monsters));
				cards.add(new RedDawn(monsters));
				break;
			case "Kraken":
				cards.add(new EaterOfSouls(monsters));
				cards.add(new HealingRain(monsters));
				break;
		}
		Collections.shuffle(cards);
		return cards;
	}
}