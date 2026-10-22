package othello;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * This is the HumanPlayer class. It implements interface player,
 * as it is a type of player that can only make human moves. This class
 * is instantiated when a human is playing and relying on mouse action
 * events to determine if a piece should be placed.
 */
public class HumanPlayer implements Player{
    private final BorderPane gamePane;
    private final Board board;
    private final Referee referee;
    private boolean white;

    /**
     * This is the HumanPlayer constructor. It assigns three instance variables:
     * board, gamePane, and referee. The variables allow the class to make moves,
     * and to see if they those moves are valid.
     */
    public HumanPlayer(BorderPane gamePane, Board board, Referee referee){
        this.board = board;
        this.gamePane = gamePane;
        this.referee = referee;
    }

    /**
     * This method updates the boolean white, which determines of a player is playing
     * with white or black stones.
     */
    @Override
    public void updateStatus(boolean whiteColor){
        if(whiteColor){
            this.white = true;
        }
        else{
            this.white = false;
        }
    }

    /**
     * This getter returns a Boolean of whether a player is playing for white.
     */
    @Override
    public boolean isWhite(){
        return this.white;
    }

    /**
     * This method is overriden as it is declared in the interface that this class
     * implements. This method allows for a human player to make a move by enabling
     * mouse action events. Everytime this method is called, a player is able to click
     * call the makeHumanMove method.
     */
    @Override
    public void makeMove(Player currentPlayer, Player opponentPlayer){
        this.gamePane.setFocusTraversable(true);
        this.gamePane.setOnMouseClicked((MouseEvent e)
                -> this.board.makeHumanMove(e, this.referee));
    }
}
