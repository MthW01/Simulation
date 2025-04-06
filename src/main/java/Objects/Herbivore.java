package Objects;

import GameActions.Coordinates;
import GameActions.MapInitializer;

import java.util.*;

public class Herbivore extends Creature {

    public Herbivore(int speed, int healthPoints) {
        setSpeed(speed);
        setHealthPoints(healthPoints);
        nameOnMap = "\uD83D\uDC3B";
    }

    @Override
    public Coordinates makeMove(Coordinates coordinates, MapInitializer map) {
        HashSet<Coordinates> allGrassOnMap = map.allGrassOnMap();
        List<Coordinates> currWay = new ArrayList<>();
        for (int grassCounter = 0; grassCounter < allGrassOnMap.size(); grassCounter++) {
            Coordinates grassCoords = allGrassOnMap.stream().iterator().next();
            if (!grassCoords.equals(coordinates)) {
                currWay = bfs(coordinates, grassCoords, map);
            }
        }
        return currWay.get(1);
    }

    @Override
    protected boolean isValid(Coordinates coord, MapInitializer map) {
        return super.isValid(coord, map) && (!map.isFill(coord) || (map.getEntity(coord) instanceof Grass));
    }
}
