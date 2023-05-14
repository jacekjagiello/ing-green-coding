package jacekjagiello.inggreencoding.entities.onlinegame;

import builders.ClanBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClanTest {
    @Test
    void canBeInstantiatedCorrectly() {
        Clan clan = new Clan();
        clan.setPoints(60);
        clan.setNumberOfPlayers(4);

        assertEquals(60, clan.getPoints());
        assertEquals(4, clan.getNumberOfPlayers());
    }

    @Test
    void canBeCompared() {
        final List<Clan> clans = new ArrayList<>(List.of(new Clan[]{
            new ClanBuilder().withNumberOfPlayer(4).withPoints(50).build(),
            new ClanBuilder().withNumberOfPlayer(2).withPoints(70).build(),
            new ClanBuilder().withNumberOfPlayer(6).withPoints(60).build(),
            new ClanBuilder().withNumberOfPlayer(1).withPoints(15).build(),
            new ClanBuilder().withNumberOfPlayer(5).withPoints(40).build(),
            new ClanBuilder().withNumberOfPlayer(3).withPoints(45).build(),
            new ClanBuilder().withNumberOfPlayer(1).withPoints(12).build(),
            new ClanBuilder().withNumberOfPlayer(4).withPoints(40).build()
        }));

        final List<Clan> expectedClansOrder = new ArrayList<>(List.of(new Clan[]{
            clans.get(1), clans.get(2), clans.get(0), clans.get(5), clans.get(4), clans.get(7), clans.get(3), clans.get(6),
        }));

        Collections.sort(clans);

        assertEquals(expectedClansOrder, clans);
    }
}
