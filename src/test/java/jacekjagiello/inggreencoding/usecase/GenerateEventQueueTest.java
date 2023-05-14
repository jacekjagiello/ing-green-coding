package jacekjagiello.inggreencoding.usecase;

import builders.ClanBuilder;
import jacekjagiello.inggreencoding.entities.onlinegame.Clan;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

class GenerateEventQueueTest {
    @Test
    void run() {
        GenerateEventQueue calculateClansEntryOrder = new GenerateEventQueue();

        final Clan[] clans = {
            new ClanBuilder().withNumberOfPlayer(4).withPoints(50).build(),
            new ClanBuilder().withNumberOfPlayer(2).withPoints(70).build(),
            new ClanBuilder().withNumberOfPlayer(6).withPoints(60).build(),
            new ClanBuilder().withNumberOfPlayer(1).withPoints(15).build(),
            new ClanBuilder().withNumberOfPlayer(5).withPoints(40).build(),
            new ClanBuilder().withNumberOfPlayer(3).withPoints(45).build(),
            new ClanBuilder().withNumberOfPlayer(1).withPoints(12).build(),
            new ClanBuilder().withNumberOfPlayer(4).withPoints(40).build()
        };

        List<List<Clan>> clansOrder = calculateClansEntryOrder.run(6, new ArrayList<>(List.of(clans)));

        assertEquals(5, clansOrder.size());

        final List<Clan> group1 = clansOrder.get(0);
        final List<Clan> group2 = clansOrder.get(1);
        final List<Clan> group3 = clansOrder.get(2);
        final List<Clan> group4 = clansOrder.get(3);
        final List<Clan> group5 = clansOrder.get(4);

        assertEquals(2, group1.size());
        assertThat(group1.get(0)).isEqualTo(clans[1]);
        assertThat(group1.get(1)).isEqualTo(clans[0]);

        assertEquals(1, group2.size());
        assertThat(group2.get(0)).isEqualTo(clans[2]);

        assertEquals(3, group3.size());
        assertThat(group3.get(0)).isEqualTo(clans[5]);
        assertThat(group3.get(1)).isEqualTo(clans[3]);
        assertThat(group3.get(2)).isEqualTo(clans[6]);

        assertEquals(1, group4.size());
        assertThat(group4.get(0)).isEqualTo(clans[4]);

        assertEquals(1, group5.size());
        assertThat(group5.get(0)).isEqualTo(clans[7]);
    }
}