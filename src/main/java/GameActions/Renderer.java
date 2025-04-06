package GameActions;

public class Renderer {
    public void render(MapInitializer map) {
        for (int row = 0; row < AdditionalValues.MAP_ROW_SIZE; row++) {
            String line = "";
            for (int col = 0; col < AdditionalValues.MAP_COL_SIZE; col++) {
                Coordinates coordinates = new Coordinates(row, col);
                if (map.isFill(coordinates)) {
                    String entityName = map.getEntity(coordinates).nameOnMap;
                    line += entityName;
                } else {
                    line +=  "⬛";
                }
            }
            System.out.println(line);
        }
    }
}
