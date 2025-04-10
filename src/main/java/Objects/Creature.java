package Objects;

import GameActions.AdditionalValues;
import GameActions.Coordinates;
import GameActions.Map;

import java.util.*;

public abstract class Creature extends Entity {
    private int healthPoints;

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public abstract Coordinates makeMove(Coordinates coordinates, Map map);

    protected Coordinates getTargetCoordinates(Coordinates start, Coordinates goal, Map map) {
        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        HashMap<Coordinates, Coordinates> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);
        parent.put(start, null);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            if (current.equals(goal)) {
                return reconstructPath(parent, current).get(1);
            }

            for (int[] direction : AdditionalValues.directions) {
                Coordinates neighbor = new Coordinates(current.getRow() + direction[0], current.getCol() + direction[1]);

                if (isAvailableCoordinates(neighbor, map) && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    parent.put(neighbor, current);
                }
            }
        }
        return start;
    }

    protected boolean isAvailableCoordinates(Coordinates coord, Map map) {

        return coord.getRow() >= 0 && coord.getRow() < AdditionalValues.MAP_ROW_SIZE &&
                coord.getCol() >= 0 && coord.getCol() < AdditionalValues.MAP_COL_SIZE;
    }

    protected List<Coordinates> reconstructPath(java.util.Map<Coordinates, Coordinates> parent, Coordinates goal) {
        List<Coordinates> path = new ArrayList<>();
        for (Coordinates at = goal; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

}
