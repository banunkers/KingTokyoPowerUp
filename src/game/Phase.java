package game;

public enum Phase {
	START,
	ROLLING,
	RESOLVING,
	ATTACKING,
	ATTACKING_NO_CLAW,	// Some effects makes the monster deal damage without having to roll CLAW
	BUYING,
	YIELDING_TOKYO,
	TAKING_TOKYO,
	DISCARDING, 
	TAKING_DAMAGE,
}