package builders;

import jacekjagiello.inggreencoding.entities.onlinegame.Clan;
import utils.RandomUtils;

public class ClanBuilder extends RandomUtils {
    private int points;
    private int numberOfPlayers;

    public ClanBuilder withNumberOfPlayer(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        return this;
    }

    public ClanBuilder withPoints(int points) {
        this.points = points;
        return this;
    }

    public ClanBuilder withRandomNumberOfPlayer() {
        this.numberOfPlayers = this.randomNumber(1, 10);
        return this;
    }

    public ClanBuilder withRandomPoints() {
        this.points = this.randomNumber(10, 100);
        return this;
    }

    public Clan build() {
        Clan newClan = new Clan();
        newClan.setNumberOfPlayers(this.numberOfPlayers);
        newClan.setPoints(this.points);
        return newClan;
    }
}