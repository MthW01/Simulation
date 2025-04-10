package Objects;

import GameActions.Coordinates;
import GameActions.Map;

import java.util.*;

public class Herbivore extends Creature {

    public Herbivore(int healthPoints) {
        setHealthPoints(healthPoints);
        nameOnMap = "\uD83D\uDC3B";
    }

    @Override
    public Coordinates makeMove(Coordinates coordinates, Map map) {
        HashSet<Coordinates> allGrassOnMap = map.getGrassOnMap();
        for (int grassCounter = 0; grassCounter < allGrassOnMap.size(); grassCounter++) {
            Coordinates grassCoords = allGrassOnMap.stream().iterator().next();
            if (!grassCoords.equals(coordinates)) {
                return getTargetCoordinates(coordinates, grassCoords, map);
            }
        }
        return coordinates;
    }

    @Override
    protected boolean isAvailableCoordinates(Coordinates coord, Map map) {
        return super.isAvailableCoordinates(coord, map) && (!map.isFill(coord) || (map.getEntity(coord) instanceof Grass));
    }
}
