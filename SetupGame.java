package othello;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * This class sets-up the game by creating the board, referee, and controls.
 * It sets the controls to the gamePane and instantiates the referee and
 * board as instance variable. This class is in charge of initial setup,
 * such as assigning players from the controls output, starting the game,
 * resetting the game, and applying palettes.
 */
public class SetupGame {
    private final BorderPane gamePane;
    private final Referee referee;
    private final Board board;

    /**
     * The constructor sets up all the instance variables, and takes in a
     * parameter for the main gamePane of the project, which is used for
     * all the Squares, pieces and labels.
     *
     * It also instantiates the ref and the board.
     */
    public SetupGame(BorderPane gamePane){
        this.gamePane = gamePane;
        this.board = new Board(this.gamePane);
        Controls controls = new Controls(this);
        this.gamePane.setRight(controls.getPane());
        this.referee = new Referee(
                this.gamePane,
                this.board,
                controls.getScoreLabel(),
                controls.getTurnLabel());
    }

    /**
     * This method is called twice from controls to instantiate
     * the white and black players and determine if they should be
     * instantiated as human or computer, based on the control's int
     * argument.
     */
    public void updatePlayerTypes(int playerMode, boolean whitePlayer) {
        Player player;
        switch(playerMode){
            case Constants.COMPUTER_DIFF_ONE:
                player = new ComputerPlayer(
                        this.board, this.referee,Constants.COMPUTER_DIFF_ONE);
                break;
            case Constants.COMPUTER_DIFF_TWO:
                player = new ComputerPlayer(
                        this.board, this.referee,Constants.COMPUTER_DIFF_TWO);
                break;
            case Constants.COMPUTER_DIFF_THREE:
                player = new ComputerPlayer(
                        this.board, this.referee, Constants.COMPUTER_DIFF_THREE);
                break;
            default:
                player = new HumanPlayer(this.gamePane, this.board, this.referee);
                break;
        }
        // Determines if player is white.
        if(whitePlayer){
            player.updateStatus(true);
            this.referee.updateRefereePlayers(player, true);
        }
        else{
            player.updateStatus(false);
            this.referee.updateRefereePlayers(player, false);
        }
    }

    /**
     * This method tells the ref to start the game. (Since refs start games!)
     */
    public void startGame() {
        this.referee.decideMove();
    }

    /**
     * This method applies palettes by changing various constants that
     * use colors.
     */
    public void applyPalette(String[] palette) {
        Constants.WHITE =
                Color.web(palette[Constants.PALETTE_ASSIGNMENT_ONE]);
        Constants.BLACK =
                Color.web(palette[Constants.PALETTE_ASSIGNMENT_TWO]);
        Constants.BOARD_TILES_COLOR =
                Color.web(palette[Constants.PALETTE_ASSIGNMENT_THREE]);
        Constants.BORDER_TILES_COLOR =
                Color.web(palette[Constants.PALETTE_ASSIGNMENT_FOUR]);
        Constants.PROJECTED_MOVE_COLOR =
                Color.web(palette[Constants.PALETTE_ASSIGNMENT_FIVE]);
        Constants.CONTROL_PANE_COLOR = "-fx-background-color: " +
                palette[Constants.PALETTE_ASSIGNMENT_SIX];
        this.board.applyPaletteBoard();
        this.gamePane.setStyle(Constants.CONTROL_PANE_COLOR);
        this.reset();
    }

    /**
     * This method tells the ref and the board to reset the game.
     */
    public void reset() {
        this.board.boardReset();
        this.referee.refReset();
    }
}
