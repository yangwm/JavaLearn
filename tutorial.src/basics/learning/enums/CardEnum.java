package basics.learning.enums;

public class CardEnum {
    private final Rank rank;
    private final Suit suit;

    public CardEnum(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public String toString() {
        return rank + " of " + suit;
    }
}
