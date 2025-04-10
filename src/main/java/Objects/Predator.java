package Objects;

import GameActions.Coordinates;
import GameActions.Map;

public class Predator extends Creature {
    public Predator(int healthPoint) {
        setHealthPoints(healthPoint);
        nameOnMap = "\uD83E\uDD77";
    }

    @Override
    protected boolean isAvailableCoordinates(Coordinates coord, Map map) {
        return super.isAvailableCoordinates(coord, map) &&
                (!map.isFill(coord) || map.getEntity(coord) instanceof Herbivore || map.getEntity(coord) instanceof Grass);
    }

    @Override
    public Coordinates makeMove(Coordinates coordinates, Map map) {
        Coordinates herbivoreCoordinates = map.herbivoreCoordinates();
        if (!coordinates.equals(herbivoreCoordinates)) {
            return getTargetCoordinates(coordinates, herbivoreCoordinates, map);
        }
        return coordinates;
    }
}
