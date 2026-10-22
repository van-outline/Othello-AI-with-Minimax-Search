package othello;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/*
 * Controls sets up the GUI for the game menu, allowing the user to pick the
 * game modes and to start and track games. Controls holds a one-way reference
 * to the Game, so it can control the Game's player settings.
 */
public class Controls {

  private final SetupGame game;

  private final VBox controlsPane;

  // Arrays for player buttons. Each button is checked to see if it is
  // selected when the user starts each game.
  private RadioButton[][] playerButtons;
  private Label score;
  private Label turn;

  /**
   * This is the controls constructor, it sets up all the components
   * of the controls pane such as the instructions, the labels, the menu,
   * the buttons, and the palettes buttons
   */
  public Controls(SetupGame othello) {
    this.game = othello;

    this.controlsPane = new VBox();
    this.controlsPane.setPadding(new Insets(Constants.CONTROLS_PANE_PADDING));
    this.controlsPane.setSpacing(Constants.CONTROLS_PANE_SPACING);
    this.controlsPane.setAlignment(Pos.CENTER);

    this.setupInstructions();
    
    this.setupLabels();
    this.setupMenu();
    this.setupGameButtons();
    this.createPaletteGenerator();
  }

  /**
   * This method creates the buttons for the palettes. For each palette
   * in the palette arraylist, the method creates a button that sets
   * all the colorful graphical elements of the game to those colors.
   */
  private void createPaletteGenerator() {
    Label paletteLabel = new Label(
            "Select a color palette:");
    this.controlsPane.getChildren().add(paletteLabel);

    TilePane palettePane = new TilePane();
    this.controlsPane.getChildren().add(palettePane);

    for(String[] palette : Constants.PALETTES){
      Button paletteButton = new Button(palette[0]);
      paletteButton.setOnAction((ActionEvent e) -> this.game.applyPalette(palette));
      paletteButton.setFocusTraversable(true);
      this.controlsPane.getChildren().add(paletteButton);
    }
}

  /**
   * This method sets up the labels of the game and adds them
   * to the control pane.
   */
  private void setupLabels() {
    this.score = new Label(
            "White: 0 Black: 0");
    this.controlsPane.getChildren().add(this.score);
    this.turn = new Label(
            "White to Move");
    this.controlsPane.getChildren().add(this.turn);
  }

  /**
   * Getter for the score label.
   */
  public Label getScoreLabel(){
    return this.score;
  }

  /**
   * Getter for the turn label.
   */
  public Label getTurnLabel(){
    return this.turn;
  }

  /**
   * Getter for the controls pane.
   */
  public Pane getPane() {
    return this.controlsPane;
  }

  /**
   * This method sets up the instructions text.
   */
  private void setupInstructions() {
    Label instructionsLabel = new Label(
        "Select options, then press Apply Settings");
    this.controlsPane.getChildren().add(instructionsLabel);
  }

  /*
   * Sets up the two halves of the player mode menu.
   */
  private void setupMenu() {
    this.playerButtons = new RadioButton[Constants.RADIO_ROW][Constants.RADIO_COL];

    HBox playersMenu = new HBox();
    playersMenu.setSpacing(Constants.MENU_SPACING);
    playersMenu.setAlignment(Pos.CENTER);
    playersMenu.getChildren().addAll(this.playerMenu(Constants.WHITE_INT),
        this.playerMenu(Constants.BLACK_INT));

    this.controlsPane.getChildren().add(playersMenu);
  }

  /*
   * Provides the menu for each player mode.
   */
  private VBox playerMenu(int player) {
    VBox playerMenu = new VBox();
    playerMenu.setPrefWidth(Constants.CONTROLS_PANE_WIDTH / Constants.PANE_DIVIDER);
    playerMenu.setSpacing(Constants.MENU_SPACING);
    playerMenu.setAlignment(Pos.CENTER);

    // Player color.
    String playerColor = "White";
    if (player == Constants.BLACK_INT) {
      playerColor = "Black";
    }
    Label playerName = new Label(playerColor);

    // Radio button group for player mode.
    ToggleGroup toggleGroup = new ToggleGroup();

    // Human player.
    RadioButton humanButton = new RadioButton("Human         ");
    humanButton.setToggleGroup(toggleGroup);
    humanButton.setSelected(true);
    this.playerButtons[player][0] = humanButton;

    // Computer Players.
    for (int i = 1; i < Constants.PLAYERS; i++) {
      RadioButton computerButton = new RadioButton("Computer " + i + "  ");
      computerButton.setToggleGroup(toggleGroup);
      this.playerButtons[player][i] = computerButton;

      // Enables deterministic button when Computer player selected.

    }

    // Checkbox for deterministic play. Only enabled when computer player
    // selected. This is ONLY for Bells&Whistles

    // Visually add the player mode menu.
    playerMenu.getChildren().add(playerName);
    for (RadioButton rb : this.playerButtons[player]) {
      playerMenu.getChildren().add(rb);
    }

    return playerMenu;
  }

  /**
   * This method sets up all the buttons of the game and sets
   * their actions events to trigger certain methods upon being pressed.
   */
  private void setupGameButtons() {
    Button applySettingsButton = new Button("Apply Settings");
    applySettingsButton.setOnAction((ActionEvent e)->this.applySettings());
    applySettingsButton.setFocusTraversable(false);

    Button resetButton = new Button("Reset");
    resetButton.setOnAction((ActionEvent e)-> this.resetHandler());
    resetButton.setFocusTraversable(false);

    Button quitButton = new Button("Quit");
    quitButton.setOnAction((ActionEvent e)->Platform.exit());
    quitButton.setFocusTraversable(false);

    this.controlsPane.getChildren().addAll(applySettingsButton, resetButton,
        quitButton);
  }

  /*
   * Handler for Apply Settings button.
   */

    public void applySettings() {

      // Determine game play mode for each player.
      int whitePlayerMode = 0;
      int blackPlayerMode = 0;
      for (int player = 0; player < Constants.COLOR_TYPES; player++) {
        for (int mode = 0; mode < Constants.PLAYERS; mode++) {
          if (this.playerButtons[player][mode].isSelected()) {
            if (player == Constants.WHITE_INT) {
              whitePlayerMode = mode;
            } else {
              blackPlayerMode = mode;
            }
          }
        }
      }

      this.game.updatePlayerTypes(whitePlayerMode, true);
      this.game.updatePlayerTypes(blackPlayerMode, false);
      this.game.startGame();
  }

  /**
   * This method resets the game.
   */
    public void resetHandler(){
      this.game.reset();
    }

}
