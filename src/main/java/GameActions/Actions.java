package GameActions;

import static java.lang.System.*;

public class Actions {
    private Renderer renderer = new Renderer();

    public void initActions() {
        MapInitializer map = new MapInitializer();
        map.setEntities();
        renderer.render(map);
        startSession(map);
    }

    private void startSession(MapInitializer map) {
        out.println("Для симуляции хода напишите: \"next\"");
        turnActions(map);
    }

    public void turnActions(MapInitializer map) {
        while (!AdditionalValues.isGameEnd) {
            String inputText = AdditionalValues.scanner.next().toLowerCase();
            if (inputText.equals("next")) {
                map.MoveEntities();
                if (!map.isHerbivoreOnMap()) {
                    AdditionalValues.isGameEnd = true;
                }
                out.println("");
                renderer.render(map);
            }
        }
    }
}
