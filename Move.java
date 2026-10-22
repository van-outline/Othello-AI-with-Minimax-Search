package othello;

/**
 * This is the move class. Move is essentially a container for many important
 * variables that need to be grouped into one. It is crucial for the minimax,
 * as many instances of move are created to determine which one is that best for
 * the computer's play.
 */
public class Move {

    private final int row;
    private final int col;
    private int value;

    /**
     * Because move is a simple class, its constructor only assigns
     * the three variables contained within move: row, col, and value.
     */
    public Move(int row, int col, int value){
        this.row = row;
        this.col = col;
        this.value = value;
    }

    /**
     * This is an overloaded constructor that doesn't take in a value parameter.
     * It is used in the getAllLegalMoves method, since their values are not
     * yet established.
     */
    public Move(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Getter for the move's Row.
     */
    public int getRow(){
        return this.row;
    }

    /**
     * Getter for the move's Col.
     */
    public int getCol(){
        return this.col;
    }

    /**
     * Getter for the move's Value.
     */
    public int getValue(){
        return this.value;
    }

    /**
     * Setter for the move's Value.
     */
    public void setValue(int value) {
        this.value = value;
    }
}
