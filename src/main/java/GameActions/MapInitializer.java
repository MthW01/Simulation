package GameActions;

import Objects.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

public class MapInitializer {
    public HashMap<Coordinates, Entity> gameMap = new HashMap<>();
    private Random random = new Random();

    public void setEntities() {
        gameMap.put(new Coordinates(0, 0), new Herbivore(2, 100));
        gameMap.put(new Coordinates(8,8), new Predator(2, 100));

        gameMap.put(setRandomCordinates(), new Grass());
        gameMap.put(setRandomCordinates(), new Grass());
        gameMap.put(setRandomCordinates(), new Grass());

        gameMap.put(setRandomCordinates(), new Rock());
        gameMap.put(setRandomCordinates(), new Rock());
        gameMap.put(setRandomCordinates(), new Tree());
        gameMap.put(setRandomCordinates(), new Rock());
        gameMap.put(setRandomCordinates(), new Rock());
        gameMap.put(setRandomCordinates(), new Tree());
    }

    public Coordinates getRandomCoordinate() {
        return new Coordinates(random.nextInt(AdditionalValues.MAP_ROW_SIZE), random.nextInt(AdditionalValues.MAP_COL_SIZE));
    }

    public Coordinates setRandomCordinates() {
        boolean isEmptyPlace = true;
        while (isEmptyPlace) {
            Coordinates coordinates = getRandomCoordinate();
            if (!isFill(coordinates)) {
                return coordinates;
            }
        }
        return new Coordinates(0, 0);
    }

    public boolean isFill(Coordinates coordinates) {
        return gameMap.containsKey(coordinates);
    }

    public Entity getEntity(Coordinates coordinates) {
        return gameMap.get(coordinates);
    }

    public void MoveEntities() {
        if (isHerbivoreOnMap() && !allGrassOnMap().isEmpty()) {
            HashMap<Coordinates, Entity> newGameMap = new HashMap<>();

            for (Map.Entry<Coordinates, Entity> entry : gameMap.entrySet()) {
                Coordinates coordinates = entry.getKey();
                Entity entity = getEntity(coordinates);
                if (!(entity instanceof Herbivore) && !(entity instanceof Predator)) {
                    newGameMap.put(coordinates, entity);
                }
            }
            for (Map.Entry<Coordinates, Entity> entry : gameMap.entrySet()) {
                Coordinates coordinates = entry.getKey();
                Entity entity = getEntity(coordinates);
                if (entity instanceof Herbivore) {
                    Coordinates newCoordinates = ((Herbivore) entity).makeMove(coordinates, this);
                    if (isFill(newCoordinates) && getEntity(newCoordinates) instanceof Grass) {
                        ((Herbivore) entity).setHealthPoints(((Herbivore) entity).getHealthPoints() + 50);
                        newGameMap.remove(newCoordinates);
                    }
                    newGameMap.put(newCoordinates, entity);
                }
            }

            for (Map.Entry<Coordinates, Entity> entry : gameMap.entrySet()) {
                Coordinates coordinates = entry.getKey();
                Entity entity = getEntity(coordinates);
                if (entity instanceof Predator) {
                    Coordinates newCoordinates = ((Predator) entity).makeMove(coordinates, this);
                    if (isFill(newCoordinates) && getEntity(newCoordinates) instanceof Herbivore) {
                        Herbivore herbivore = (Herbivore) getEntity(newCoordinates);
                        herbivore.setHealthPoints(((Herbivore) entity).getHealthPoints() - 200);
                        if (herbivore.getHealthPoints() < 0) {
                            gameMap.remove(newCoordinates);
                        }
                    }
                    newGameMap.put(newCoordinates, entity);
                }
            }

            gameMap = newGameMap;
        } else {
            AdditionalValues.isGameEnd = true;
        }
    }

    public boolean isHerbivoreOnMap() {
        return gameMap.values().stream().anyMatch(value -> value instanceof Herbivore);
    }

    public HashSet<Coordinates> allGrassOnMap() {
        HashSet<Coordinates> allGrassOnMap = new HashSet<>();
        for (Map.Entry<Coordinates, Entity> entry : gameMap.entrySet()) {
            if (entry.getValue() instanceof Grass) {
                allGrassOnMap.add(entry.getKey());
            }
        }
        return allGrassOnMap;
    }
    public Coordinates herbivoreCoordinates() {
        for (Map.Entry<Coordinates, Entity> entry : gameMap.entrySet()) {
            if (entry.getValue() instanceof Herbivore) {
                return entry.getKey();
            }
        }
        return new Coordinates(0,0);
    }
}
