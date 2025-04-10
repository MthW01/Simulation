package GameActions;

public class Actions {
    private final Renderer renderer = new Renderer();
    Map map = new Map();

    public void initActions() {
        map.setEntities();
        renderer.render(map);
        System.out.println("Для симуляции напишите: \"start\"");
        String inputText = AdditionalValues.scanner.next().toLowerCase();
        if (inputText.equals(AdditionalValues.START_TEXT)) turnActions(map);
    }

    public void turnActions(Map map) {
        while (!AdditionalValues.isGameEnd) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            map.moveEntities();
            if (!map.isHerbivoreExistsOnMap()) {
                AdditionalValues.isGameEnd = true;
            }
            System.out.println();
            renderer.render(map);

        }
    }
}
