package Objects;

import GameActions.Coordinates;
import GameActions.MapInitializer;

import java.util.ArrayList;
import java.util.List;

public class Predator extends Creature {
    public Predator(int speed, int healthPoint) {
        setSpeed(speed);
        setHealthPoints(healthPoint);
        nameOnMap = "\uD83E\uDD77";
    }

    @Override
    protected boolean isValid(Coordinates coord, MapInitializer map) {
        return super.isValid(coord, map) &&
                (!map.isFill(coord) || map.getEntity(coord) instanceof Herbivore || map.getEntity(coord) instanceof Grass);
    }

    @Override
    public Coordinates makeMove(Coordinates coordinates, MapInitializer map) {
        List<Coordinates> currWay = new ArrayList<>();
        Coordinates herbivoreCoordinates = map.herbivoreCoordinates();
        if (!coordinates.equals(herbivoreCoordinates)) {
            currWay = bfs(coordinates, herbivoreCoordinates, map);
        }
        return currWay.get(1);
    }
}
