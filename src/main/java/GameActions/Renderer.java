package GameActions;

public class Renderer {
    public void render(Map map) {
        for (int row = 0; row < AdditionalValues.MAP_ROW_SIZE; row++) {
            StringBuilder line = new StringBuilder();
            for (int col = 0; col < AdditionalValues.MAP_COL_SIZE; col++) {
                Coordinates coordinates = new Coordinates(row, col);
                if (map.isFill(coordinates)) {
                    line.append(map.getEntity(coordinates).nameOnMap);
                } else {
                    line.append("⬛");
                }
            }
            System.out.println(line);
        }
    }
}
