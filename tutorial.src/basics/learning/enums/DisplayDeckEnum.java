package basics.learning.enums;

public class DisplayDeckEnum {
    public static void main(String[] args) {
        DeckEnum deck = new DeckEnum();
        int i = 0;
        for (CardEnum d : deck.getCards()) {
            System.out.printf("%d: %s %n", ++i,d);
        }
    }
}
