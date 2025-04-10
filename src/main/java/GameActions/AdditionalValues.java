package GameActions;

import java.util.Scanner;

public class AdditionalValues {
    public static boolean isGameEnd = false;
    public static final int MAP_ROW_SIZE = 10;
    public static final int MAP_COL_SIZE = 10;
    private static final int ALL_SIZE_MAP = AdditionalValues.MAP_COL_SIZE * AdditionalValues.MAP_ROW_SIZE;
    public static final int GRASS_AMOUNT = (int) (ALL_SIZE_MAP * 0.05);
    public static final int ROCK_AND_TREE_AMOUNT = (int) (ALL_SIZE_MAP * 0.125);
    public static Scanner scanner = new Scanner(System.in);
    public static final String START_TEXT = "start";
    public static int[][] directions = {
            {-1, 0},
            {0, -1},
            {1, 0},
            {0, 1}
    };
}
