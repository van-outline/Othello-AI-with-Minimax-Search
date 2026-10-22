package othello;

import javafx.scene.layout.BorderPane;

/**
 * This is the main class for the graphics of the game. It has an instance
 * variable for a BorderPane that contains all the graphical elements of the game.
 * That is the main root node, to which BorderPane gamePane is added to. gamePane
 * is the pane most graphical elements of the game get added to.
 */
public class PaneOrganizer {
    private final BorderPane root;
    private BorderPane gamePane;

    /**
     * This constructor sets up the main graphics of the game and also
     * creates a new SetupGame object. It passes the gamePane into SetupGame,
     * so that game can only have access to the graphical pane.
     */
    public PaneOrganizer(){
        this.root = new BorderPane();
        this.root.setStyle(Constants.ROOT_COLOR);

        this.createGamePane();
        new SetupGame(this.gamePane);
    }

    /**
     * This method creates GamePane, which is the pane that game has
     * access to. Game adds labels and javafx elements only to this pane.
     */
    private void createGamePane() {
        this.gamePane = new BorderPane();
        this.gamePane.setStyle(Constants.CONTROL_PANE_COLOR);
        this.gamePane.setFocusTraversable(true);
        this.root.setCenter(this.gamePane);
    }

    /**
     * This method returns the root Borderpane.
     */
    public BorderPane getRoot() {
        return this.root;
    }
}
