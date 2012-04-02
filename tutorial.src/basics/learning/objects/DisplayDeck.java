package basics.learning.objects;

public class DisplayDeck {
    public static void main(String[] args) {
        /*java.util.Date d = DateUtil.stringToDate("2008-03-01");
        System.out.println(d);
        System.out.println(new java.sql.Date(108,02,01));*/
        Deck deck = new Deck();
        int i = 0;
        for (int suit = Card.DIAMONDS; suit <= Card.SPADES; suit++) {
            for (int rank = Card.ACE; rank <= Card.KING; rank++) {
                Card card = deck.getCard(suit, rank);
                System.out.format("%d: %s of %s%n",
                    ++i,
                    Card.rankToString(card.getRank()),
                    Card.suitToString(card.getSuit()));
            }
        }
    }
}

