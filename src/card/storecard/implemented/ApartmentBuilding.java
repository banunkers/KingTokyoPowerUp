package card.storecard.implemented;

import java.util.ArrayList;

import card.storecard.StoreCard;
import card.storecard.implemented.effect.ApartmentBuildingEffect;
import monster.Monster;

public class ApartmentBuilding extends StoreCard {
	public ApartmentBuilding(ArrayList<Monster> monsters) {
		super("Apartment Building", 5, true, new ApartmentBuildingEffect(monsters), "+3 stars");
	}
}