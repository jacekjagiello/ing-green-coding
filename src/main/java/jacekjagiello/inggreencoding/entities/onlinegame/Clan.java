package jacekjagiello.inggreencoding.entities.onlinegame;

import jakarta.annotation.Nonnull;

import java.util.Comparator;

public class Clan implements Comparable<Clan> {
    private int numberOfPlayers;
    private int points;

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int compareTo(@Nonnull Clan o) {
        return Comparator.comparing(Clan::getPoints, Comparator.reverseOrder())
                .thenComparing(Clan::getNumberOfPlayers, Comparator.reverseOrder())
                .compare(this, o);
    }
}
