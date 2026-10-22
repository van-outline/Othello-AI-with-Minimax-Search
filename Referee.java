package othello;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * This is the referee class, it is used to
 * manage the players and turn-taking logic, as well as
 * update labels and reset the game.
 *
 * Most things in this class center around the current
 * and non-current player instance variables which identify
 * who's turn it is, and whether a computer or human is to
 * play.
 */
public class Referee {

    private Player currentPlayer;
    private Player nonCurrentPlayer;
    private final Board board;
    private Timeline timeline;
    private final BorderPane gamePane;
    private final Label score;
    private final Label turn;
    private int whiteScore;
    private int blackScore;

    /**
     * This is the referee constructor, it instantiates several variables,
     * such as white and black score. It also assigns several passed through
     * arguments, like labels, the gamePane, and the board. It also calls
     * method startTimeline to create a timeline.
     */
    public Referee(BorderPane gamePane, Board board, Label score, Label turn) {
        this.gamePane = gamePane;
        this.whiteScore = 0;
        this.blackScore = 0;
        this.score = score;
        this.turn = turn;
        this.board = board;
        this.startTimeline();
    }

    /**
     * This method is called at the start of the game to determine who the
     * first player is.
     */
    public void updateRefereePlayers(Player player, boolean whitePlayer) {
        if (whitePlayer) {
            this.currentPlayer = player;
        } else {
            this.nonCurrentPlayer = player;
        }
    }

    /**
     * This method is the first stage in the turn-taking process,
     * it contains several if-then-else statements to determines what action
     * should take place. The inline comments dive into each check deeper.
     */
    public void decideMove() {
        // Checks if the game is over, if yes, calls ref's gameOver method
        if (this.board.checkGameOver(this.currentPlayer, this.nonCurrentPlayer)) {
            this.board.deleteDisplayedMoves();
            this.gameOver(this.board.getWinStatus());
        } else {
            // Checks if current player has moves, if yes, skips their turn
            if (this.board.getAllLegalMoves(this.currentPlayer).isEmpty()) {
                this.switchPlayers();
                this.decideMove();
            } else {
                // Checks if current is a computer, if yes, starts timeline.
                if (this.currentPlayer instanceof ComputerPlayer) {
                    this.board.deleteDisplayedMoves();
                    this.timeline.play();
                } else {
                    // Stops timeline and let's human move.
                    this.board.displayPossibleMoves(this.getColor());
                    this.timeline.stop();
                    this.currentPlayer.makeMove(this.currentPlayer, this.nonCurrentPlayer);
                }
            }
        }
    }

    /**
     * This is the ref's gameOver method that exists solely to update
     * the game over labels depending on the result of the game.
     */
    public void gameOver(int winResult) {
        this.timeline.stop();
        switch (winResult) {
            case 1:
                this.turn.setText("GAME OVER! White Wins");
                break;
            case -1:
                this.turn.setText("GAME OVER! Black Wins");
                break;
            default:
                this.turn.setText("GAME OVER! It's a tie!");
                break;
        }
    }

    /**
     * When a player takes a move, this method is called for the ref to
     * decide if the move is valid. It calls several methods from the board
     * class to determine if the move was valid and then switches players.
     * Then the ref calls decideMove again to restart the turn pattern.
     */
    public void refDecideifValidMove(int i, int j) {
        if (this.board.validMove(i, j, true, this.getColor())) {
            this.board.placePiece(j, i, this.currentPlayer);
            this.updateScore();
            this.gamePane.setOnMouseClicked(null);
            this.switchPlayers();
            this.decideMove();
        }
    }

    /**
     * This method returns the color of the current player
     * depending on if they are white or black.
     */
    private Color getColor() {
        if (this.currentPlayer.isWhite()) {
            return Constants.WHITE;
        } else {
            return Constants.BLACK;
        }
    }

    /**
     * This method sets the turn label depending on whose
     * turn it is to play.
     */
    private void changeTurnLabel(Player currentPlayer) {
        if (currentPlayer.isWhite()) {
            this.turn.setText("Black to Move");
        } else {
            this.turn.setText("White to Move");
        }
    }

    /**
     * This method updates the score by counting how pieces of
     * each player's color on the screen.
     */
    public void updateScore() {
        int whiteTempScore = 0;
        int blackTempScore = 0;
        for (int i = 1; i < Constants.SQUARE_AMOUNT-1; i++) {
            for (int j = 1; j < Constants.SQUARE_AMOUNT-1; j++) {
                if (this.board.getSquare(i, j).pieceColor() == Constants.WHITE) {
                    whiteTempScore++;
                } else if (this.board.getSquare(i, j).pieceColor() == Constants.BLACK) {
                    blackTempScore++;
                }
            }
        }
        this.blackScore = blackTempScore;
        this.whiteScore = whiteTempScore;
        this.score.setText("White: " + this.whiteScore +
                " Black: " + this.blackScore);
    }

    /**
     * This method creates the timeline that the computer player uses
     * to create a duration between each of its moves.
     */
    private void startTimeline() {
        this.timeline = new Timeline();
        this.timeline.getKeyFrames().add(new KeyFrame(
                Duration.seconds(Constants.COMPUTER_DURATION),
                ((ActionEvent e) -> this.currentPlayer.makeMove(
                        this.currentPlayer, this.nonCurrentPlayer))));
        this.timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * This method swaps the player's statuses to allow the
     * non-current player to become the current player and vice-versa.
     */
    private void switchPlayers() {
        this.changeTurnLabel(this.currentPlayer);
        Player tempPlayer = this.currentPlayer;
        this.currentPlayer = this.nonCurrentPlayer;
        this.nonCurrentPlayer = tempPlayer;
    }

    /**
     * This method resets all the elements of the ref. This includes scores,
     * playing timelines, and label text.
     */
    public void refReset() {
        this.gamePane.setOnMouseClicked(null);
        this.timeline.stop();
        this.whiteScore = 0;
        this.blackScore = 0;
        this.turn.setText("White to Move");
        this.score.setText("White: 0 Black: 0");
    }
}
