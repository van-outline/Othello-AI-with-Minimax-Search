package othello;

import java.util.ArrayList;

/**
 * This is the ComputerPlayer class. It implements interface player,
 * as it is a type of player that can only make computer moves. This class
 * is instantiated when a human is playing and implements getBestMove, which is
 * used for the minimax AI that calculates the computer's best move.
 */
public class ComputerPlayer implements Player{
    private final Board board;
    private final Referee referee;
    private boolean white;
    private final int difficultyDepth;

    /**
     * This is the ComputerPlayer constructor. It assigns three instance variables:
     * board, referee, and difficulty. The variables allow the class to make moves,
     * and to see if they those moves are valid. Difficulty accounts for the minimax's
     * depth, making higher difficultly search for further moves.
     */
    public ComputerPlayer(Board board, Referee referee, int difficulty){
        this.board = board;
        this.referee = referee;
        this.difficultyDepth = difficulty;
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
     * implements. This method allows for a computer player to make a move by
     * calling getBestMove. That method returns a move that then gets passed to the
     * referee to determine if it is valid.
     */
    @Override
    public void makeMove(Player currentPlayer, Player opponentPlayer){
        Move bestMove = this.getBestMove(
                currentPlayer,
                opponentPlayer,
                this.board,
                this.difficultyDepth);
        // Some methods are passed outside the board on purpose.
        if (bestMove.getRow() >= 0 && bestMove.getCol() >= 0) {
            this.referee.refDecideifValidMove(bestMove.getRow(),
                    bestMove.getCol());
        }
    }

    /**
     * This is the method in charge of the computer player's move making.
     * Using recursion, it determines the best move for the computer to make based
     * off of an intelligence level. This intelligence level is known as depth, it
     * determines how many moves ahead the computer will project using recursion.
     * The method also creates testboards and tests moves out on those boards
     * using the board's copy constructor. Using weights assigned to each square
     * on the board, the method evaluates whether the move is of high or low value.
     * This section of the method is known as a minimax, as it maximizing or minimizes
     * a move depending on if it is calculating from the perspective of the current
     * player or the opponent.
     */
    public Move getBestMove(
            Player currentPlayer,
            Player otherPlayer,
            Board originalBoard,
            int depth){
        // Creates two arraylists that gather the current and non-current player's moves
        ArrayList<Move> allLegalMoves = originalBoard.getAllLegalMoves(currentPlayer);
        ArrayList<Move> otherMoves = originalBoard.getAllLegalMoves(otherPlayer);
        // If game is over within the move sim, returns a very high or low value move.
        if(originalBoard.checkGameOver(currentPlayer, otherPlayer)){
            int value;
            int gameResult = originalBoard.getWinStatus();
            if(!currentPlayer.isWhite()){
                gameResult *= -1;
            }
            switch (gameResult) {
                case 1:
                    value = Integer.MAX_VALUE;
                    break;
                case -1:
                    value = Integer.MIN_VALUE;
                    break;
                default:
                    value = 0;
                    break;
            }
            return new Move(-1, -1, value);
        }
        // If there are no turns left for the current player, return a low or opponent move.
        else if(allLegalMoves.isEmpty() && !otherMoves.isEmpty()){
            if(depth == 1){
                return new Move(-1, -1, -Integer.MAX_VALUE);
            }
            else{
                Move opponentMove = this.getBestMove(
                        otherPlayer,
                        currentPlayer,
                        originalBoard,
                        depth-1);
                int negatedValue = -opponentMove.getValue();
                return new Move(
                        opponentMove.getRow(),
                        opponentMove.getCol(),
                        negatedValue);
            }
        }
        // Use the minimax to find the best move.
        else{
            Move bestMove = null;
            int bestValue = Integer.MIN_VALUE;
            for(Move legalMove : originalBoard.getAllLegalMoves(currentPlayer)){
                // Create "fake" board.
                Board testBoard = new Board(originalBoard);
                testBoard.testMove(legalMove, currentPlayer);
                // Evaluate if at base case
                if(depth == 1){
                    legalMove.setValue(testBoard.evaluateBoard(currentPlayer));
                }
                // Or recurse from the opponent's perspective.
                else{
                    Move opMove = this.getBestMove(
                            otherPlayer,
                            currentPlayer,
                            testBoard,
                            depth-1);
                    legalMove.setValue(-1 * opMove.getValue());
                }
                // Determine which move is best.
                if(legalMove.getValue() > bestValue || bestMove == null){
                    bestMove = new Move(
                            legalMove.getRow(),
                            legalMove.getCol(),
                            legalMove.getValue());
                    bestValue = legalMove.getValue();
                }
            }
            // Return the best move.
            return bestMove;
        }
    }
}
