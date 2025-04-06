package Objects;
import GameActions.AdditionalValues;
import GameActions.Coordinates;
import GameActions.MapInitializer;

import java.util.*;

public abstract class Creature extends Entity {
    private int speed;
    private int healthPoints;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healtPoints) {
        this.healthPoints = healtPoints;
    }

    public abstract Coordinates makeMove(Coordinates coordinates, MapInitializer map);

    protected List<Coordinates> bfs(Coordinates start, Coordinates goal, MapInitializer map) {
        Queue<Coordinates> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();
        Map<Coordinates, Coordinates> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);
        parent.put(start, null);

        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();

            if (current.equals(goal)) {
                return reconstructPath(parent, current);
            }

            for (int[] direction : AdditionalValues.directions) {
                Coordinates neighbor = new Coordinates(current.getRow() + direction[0], current.getCol() + direction[1]);

                if (isValid(neighbor, map) && !visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    parent.put(neighbor, current);
                }
            }
        }
        return null;
    }

    protected boolean isValid(Coordinates coord, MapInitializer map) {

        return coord.getRow() >= 0 && coord.getRow() < AdditionalValues.MAP_ROW_SIZE &&
                coord.getCol() >= 0 && coord.getCol() < AdditionalValues.MAP_COL_SIZE;
    }

    protected List<Coordinates> reconstructPath(Map<Coordinates, Coordinates> parent, Coordinates goal) {
        List<Coordinates> path = new ArrayList<>();
        for (Coordinates at = goal; at != null; at = parent.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

}
