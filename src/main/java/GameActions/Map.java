package GameActions;

import Objects.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Map {
    public HashMap<Coordinates, Entity> gameMap = new HashMap<>();
    private Random random = new Random();

    public void setEntities() {
        gameMap.put(new Coordinates(0, 0), new Herbivore(100));
        gameMap.put(new Coordinates(8, 8), new Predator(100));

        for (int counter = 0; counter < AdditionalValues.GRASS_AMOUNT; counter++) {
            gameMap.put(setRandomCordinates(), new Grass());
        }

        for (int counter = 0; counter < AdditionalValues.ROCK_AND_TREE_AMOUNT; counter++) {
            gameMap.put(setRandomCordinates(), new Rock());
            gameMap.put(setRandomCordinates(), new Tree());
        }
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
        if (isFill(coordinates)) return gameMap.get(coordinates);
        else return null;
    }

    public void moveEntities() {
        HashMap<Coordinates, Entity> newGameMap = new HashMap<>();
        if (isHerbivoreExistsOnMap() && !getGrassOnMap().isEmpty()) {
            moveStaticEntities(newGameMap);
            moveHerbivore(newGameMap);
            movePredator(newGameMap);
            gameMap = newGameMap;
        } else AdditionalValues.isGameEnd = true;
    }

    private void moveStaticEntities(HashMap<Coordinates, Entity> newGameMap) {
        for (var entry : gameMap.entrySet()) {
            Coordinates coordinates = entry.getKey();
            Entity entity = getEntity(coordinates);
            if (!(entity instanceof Herbivore) && !(entity instanceof Predator)) {
                newGameMap.put(coordinates, entity);
            }
        }
    }

    private void movePredator(HashMap<Coordinates, Entity> newGameMap) {
        for (var entry : gameMap.entrySet()) {
            Coordinates coordinates = entry.getKey();
            Entity entity = getEntity(coordinates);
            if (entity instanceof Predator) {
                Coordinates newCoordinates = ((Predator) entity).makeMove(coordinates, this);
                if (isFill(newCoordinates) && getEntity(newCoordinates) instanceof Herbivore) {
                    Herbivore herbivore = (Herbivore) getEntity(newCoordinates);
                    herbivore.setHealthPoints(((Herbivore) entity).getHealthPoints() - 200);
                    if (herbivore.getHealthPoints() < 0) {
                        newGameMap.remove(newCoordinates);
                    }
                }
                newGameMap.put(newCoordinates, entity);
            }
        }
    }

    private void moveHerbivore(HashMap<Coordinates, Entity> newGameMap) {
        for (var entry : gameMap.entrySet()) {
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
    }

    public boolean isHerbivoreExistsOnMap() {
        return gameMap.values()
                .stream()
                .anyMatch(value -> value instanceof Herbivore);
    }

    public HashSet<Coordinates> getGrassOnMap() {
        HashSet<Coordinates> allGrassOnMap = new HashSet<>();
        for (java.util.Map.Entry<Coordinates, Entity> entry : gameMap.entrySet()) {
            if (entry.getValue() instanceof Grass) {
                allGrassOnMap.add(entry.getKey());
            }
        }
        return allGrassOnMap;
    }

    public Coordinates herbivoreCoordinates() {
        for (java.util.Map.Entry<Coordinates, Entity> entry : gameMap.entrySet()) {
            if (entry.getValue() instanceof Herbivore) {
                return entry.getKey();
            }
        }
        return new Coordinates(0, 0);
    }
}
