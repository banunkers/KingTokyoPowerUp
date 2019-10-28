package card.storecard.implemented;

import card.storecard.StoreCard;
import card.storecard.effect.implemented.AcidAttackEffect;

public class AcidAttack extends StoreCard {

	public AcidAttack() {
		super("Acid Attack", 6, false, new AcidAttackEffect(), "Deal 1 extra damage each turn");
	}
}