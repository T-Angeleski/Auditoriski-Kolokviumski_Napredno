package Auditoriska2.Karti;

import java.util.Objects;

public class PlayingCard {
    private CardType cardType;
    private int rank; // 1 to 13

    public PlayingCard(CardType cardType, int rank) {
        this.cardType = cardType;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return String.format("%d %s",rank, cardType.name());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayingCard that = (PlayingCard) o;
        return rank == that.rank && cardType == that.cardType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardType, rank);
    }
}
