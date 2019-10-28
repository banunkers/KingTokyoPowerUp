package card.storecard.implemented;

import card.storecard.StoreCard;
import card.storecard.effect.implemented.ApartmentBuildingEffect;

public class ApartmentBuilding extends StoreCard {
	public ApartmentBuilding() {
		super("Apartment Building", 5, true, new ApartmentBuildingEffect(), "+3 stars");
	}
}