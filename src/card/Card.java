package card;

/**
 * Interface of all the different card types in the game
 * @param <T> the card type
 */
public interface Card<T> {
	public T getEffect();

	public String toString();

	public String getName();
}