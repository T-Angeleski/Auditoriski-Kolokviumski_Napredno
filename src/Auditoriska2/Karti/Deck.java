package Auditoriska2.Karti;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Deck {
    private PlayingCard[] cards;
    private boolean[] isDealt; //save value for every card
    private int totalDealt;

    public Deck() {
        cards = new PlayingCard[52];
        isDealt = new boolean[52];
        totalDealt = 0;

        for (int i = 0; i < CardType.values().length; i++) {
            for (int j = 0; j < 13; j++) {
                cards[i * 13 + j] = new PlayingCard(CardType.values()[i], j + 1);
            }
        }
    }

    public PlayingCard[] getCards() {
        return cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return totalDealt == deck.totalDealt && Arrays.equals(cards, deck.cards) && Arrays.equals(isDealt, deck.isDealt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(totalDealt);
        result = 31 * result + Arrays.hashCode(cards);
        result = 31 * result + Arrays.hashCode(isDealt);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(PlayingCard card : cards) {
            stringBuilder.append(card.toString());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public boolean hasCardsLeft() {
        return (cards.length - totalDealt) > 0;
    }

    public PlayingCard deal() {
        if (!hasCardsLeft()) return null;

        int cardToDeal = (int) (Math.random() * 52);

        if(!isDealt[cardToDeal]) {
            isDealt[cardToDeal] = true;
            totalDealt++;
            return cards[cardToDeal];
        }
        return deal();
    }

    public PlayingCard[] shuffle() {
        List<PlayingCard> cardsList = Arrays.asList(cards);
        Collections.shuffle(cardsList);
        return cards;
    }
}
