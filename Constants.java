package othello;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {

    public static final int WHITE_INT = 0;
    public static final int BLACK_INT = 1;
    public static final int SCENE_HEIGHT = 1080;
    public static final int SQUARE_WIDTH = SCENE_HEIGHT/10;
    public static final int CONTROLS_PANE_WIDTH = 250;
    public static final int SCENE_WIDTH = CONTROLS_PANE_WIDTH
            + SCENE_HEIGHT + 40;
    public static final int SQUARE_AMOUNT = 10;
    public static final int INNER_SQUARE_AMOUNT = 8;
    public static final int START_PIECE_ONE = 6;
    public static final int START_PIECE_TWO = 3;
    public static final double SIN_SCALE = 0.75;
    public static final double SIN_OFFSET = 4.8;
    public static final int CONTROLS_PANE_PADDING = 10;
    public static final int CONTROLS_PANE_SPACING = 30;
    public static final int RADIO_ROW = 2;
    public static final int RADIO_COL = 4;
    public static final int MENU_SPACING = 10;
    public static final double PANE_DIVIDER = 2.0;
    public static final int PLAYERS = 4;
    public static final int COLOR_TYPES = 2;
    public static final int PIECE_DIVIDER = 2;
    public static final double PIECE_RADIUS = Constants.SQUARE_WIDTH/2.0-2.5;
    public static final int STROKE_WIDTH = 5;
    public static final double COMPUTER_DURATION = 0.01;
    public static final int COMPUTER_DIFF_ONE = 1;
    public static final int COMPUTER_DIFF_TWO = 2;
    public static final int COMPUTER_DIFF_THREE = 3;
    public static final int PALETTE_ASSIGNMENT_ONE = 1;
    public static final int PALETTE_ASSIGNMENT_TWO = 2;
    public static final int PALETTE_ASSIGNMENT_THREE = 3;
    public static final int PALETTE_ASSIGNMENT_FOUR = 4;
    public static final int PALETTE_ASSIGNMENT_FIVE = 5;
    public static final int PALETTE_ASSIGNMENT_SIX = 6;
    public static final int STARTING_PIECE_ONE = 4;
    public static final int STARTING_PIECE_TWO = 5;
    public static final String ROOT_COLOR = "-fx-background-color: #ffffff";
    public static  Color WHITE = Color.web("#FFFFFF");
    public static  Color BLACK = Color.web("#000000");
    public static  Color BOARD_TILES_COLOR = Color.web("#247543");
    public static  Color BORDER_TILES_COLOR = Color.web("#000000");
    public static  Color PROJECTED_MOVE_COLOR = Color.web("#E15C5C");
    public static  String CONTROL_PANE_COLOR = "-fx-background-color: #FFFFFF";
    public static final String[] PALETTE_CLASSIC = {
            "Classic",
            "#FFFFFF",
            "#000000",
            "#247543",
            "#000000",
            "#E15C5C",
            "#FFFFFF"
    };
    public static final String[] PALETTE_EARTHY = {
            "Earthy",
            "#DCE2BD",
            "#9D977B",
            "#B6C4A2",
            "#8E9B90",
            "#93C0A4",
            "#8E9B90"
    };

    public static final String[] PALETTE_AUTUMNAL = {
            "Autumnal",
            "#F9EA9A",
            "#F98948",
            "#9B8816",
            "#684E32",
            "#93C0A4",
            "#5D3A00"
    };

    public static final String[] PALETTE_STEEL = {
            "Victorian Steel",
            "#F5F5F5",
            "#3C3C3C",
            "#C1839F",
            "#D2D5D3",
            "#087E8B",
            "#D2D5D3"
    };

    public static final String[] PALETTE_TROPICAL = {
            "Tropical Isle",
            "#FFA630",
            "#611C35",
            "#D7E8BA",
            "#4DA1A9",
            "#5FDBB2",
            "#2E5077"
    };

    public static final String[] PALETTE_GARDEN = {
            "Rose Garden",
            "#FCF0CC",
            "#BBDBB4",
            "#A64253",
            "#861657",
            "#D56AA0",
            "#861657"
    };

    public static final String[] PALETTE_PRUSSIA = {
            "Prussian",
            "#E7DECD",
            "#804E49",
            "#698F3F",
            "#1E2D5B",
            "#FBFAF8",
            "#4E5E8F"
    };

    public static final String[] PALETTE_CAKE = {
            "Cake",
            "#FFF3F0",
            "#FDE12D",
            "#CC59D2",
            "#9046CF",
            "#F487B6",
            "#CC59D2"
    };

    public static final ArrayList<String[]> PALETTES = new ArrayList<>(
            Arrays.asList(
                    PALETTE_CLASSIC,
                    PALETTE_EARTHY,
                    PALETTE_AUTUMNAL,
                    PALETTE_STEEL,
                    PALETTE_TROPICAL,
                    PALETTE_GARDEN,
                    PALETTE_PRUSSIA,
                    PALETTE_CAKE
            )
    );
    public static final int[][] WEIGHTS = {
            { 200,  -70,   30,   25,   25,   30,  -70,  200 },
            { -70, -100,  -10,  -10,  -10,  -10, -100,  -70 },
            {  30,  -10,    2,    2,    2,    2,  -10,   30 },
            {  25,  -10,    2,    2,    2,    2,  -10,   25 },
            {  25,  -10,    2,    2,    2,    2,  -10,   25 },
            {  30,  -10,    2,    2,    2,    2,  -10,   30 },
            { -70, -100,  -10,  -10,  -10,  -10, -100,  -70 },
            { 200,  -70,   30,   25,   25,   30,  -70,  200 }
    };
}
