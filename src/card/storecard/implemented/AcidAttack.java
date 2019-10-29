package card.storecard.implemented;

import java.util.ArrayList;

import card.storecard.StoreCard;
import card.storecard.implemented.effect.AcidAttackEffect;
import monster.Monster;

public class AcidAttack extends StoreCard {

	public AcidAttack(ArrayList<Monster> monsters) {
		super("Acid Attack", 6, false, new AcidAttackEffect(monsters), "Deal 1 extra damage each turn (even when you don't otherwise attack)");
	}
}