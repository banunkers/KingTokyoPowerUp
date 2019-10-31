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
 * Creates all of the implemented store cards and evolution cards
 */
public class CardFactory {

	/**
	 * Creates a shuffled array list containing all of the currently implemented store cards
	 * @return the shuffled store cards
	 */
	public ArrayList<StoreCard> getStoreCards() {
		ArrayList<StoreCard> cards = new ArrayList<StoreCard>();
		cards.add(new AcidAttack());
		cards.add(new AlienMetabolism());
		cards.add(new AlphaMonster());
		cards.add(new ApartmentBuilding());
		cards.add(new ArmorPlating());
		cards.add(new ComuterTrain());
		cards.add(new CornerStore());
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
				cards.add(new AlienScourge());
				cards.add(new FunnyLookingButDangerous());
				break;
			case "Gigazaur":
				cards.add(new DefenderOfTokyo());
				cards.add(new RadioactiveWaste());
				break;
			case "Kong":
				cards.add(new IronCurtain());
				cards.add(new RedDawn());
				break;
			case "Kraken":
				cards.add(new EaterOfSouls());
				cards.add(new HealingRain());
				break;
		}
		Collections.shuffle(cards);
		return cards;
	}
}