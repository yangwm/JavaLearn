package basics.learning.enums;

public class DeckEnum {
    private static CardEnum[] cards = new CardEnum[52];
    public DeckEnum() {
        int i = 0;
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards[i++] = new CardEnum(rank, suit);
            }
        }
    }
    
    public CardEnum[] getCards()  {
        return cards;
    }
}
