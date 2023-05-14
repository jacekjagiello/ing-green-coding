package jacekjagiello.inggreencoding.usecase;

import jacekjagiello.inggreencoding.entities.onlinegame.Clan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GenerateEventQueue {
    public List<List<Clan>> run(int maxGroupSize, List<Clan> clanList) {
        List<List<Clan>> eventQueue = new ArrayList<>();

        Collections.sort(clanList);

        while (!clanList.isEmpty()) {
            Iterator<Clan> clans = clanList.iterator();
            List<Clan> groupOfClans = new ArrayList<>();
            int groupSize = 0;

            if (groupSize > maxGroupSize) {
                continue;
            }

            while (clans.hasNext()) {
                Clan clan = clans.next();

                int newGroupSize = groupSize + clan.getNumberOfPlayers();
                if (newGroupSize > maxGroupSize) {
                    continue;
                }

                clans.remove();
                groupSize = newGroupSize;
                groupOfClans.add(clan);
            }

            eventQueue.add(groupOfClans);
        }

        return eventQueue;
    }
}
