package othello;

/**
 * This is interface Player that declares three methods.
 * These methods allow for both types of players to have unique
 * method logic despite both being of type Player.
 */
public interface Player {

    /**
     * Setter that updates the player's color type.
     */
    void updateStatus(boolean whiteColor);

    /**
     * makeMove is the computer and human's method that
     * allows them to attempt a move. Since both types of players
     * are different functionally, the methods have different bodies
     * thanks to the interface.
     */
    void makeMove(Player currentPlayer, Player opponentPlayer);

    /**
     * Getter that returns that color type.
     */
    boolean isWhite();
}
