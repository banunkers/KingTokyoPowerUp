package card;

public interface Card<T> {
	public T getEffect();

	public String toString();

	public String getName();
}