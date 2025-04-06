package GameActions;

import java.util.Scanner;

public class AdditionalValues {
    public static boolean isGameEnd = false;
    public static final int MAP_ROW_SIZE = 10;
    public static final int MAP_COL_SIZE = 10;
    public static Scanner scanner = new Scanner(System.in);
    public static int[][] directions = {
            {-1, 0},
            {0, -1},
            {1, 0},
            {0, 1}
    };
}
