package othello;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is a class that represents each Square and piece.
 *
 * Instead of using two arraylists to represent the two
 * objects separately, this class is essentially a
 * "smart" square class that contains several identifiers
 * at once to account for both pieces and squares
 * such as color and square occupancy.
 */
public class Square {
    private final BorderPane gamePane;
    private Rectangle square;
    private boolean occupied;
    private Color pieceColor;
    private Piece piece;
    private int weight;
    private final boolean copy;
    private final boolean border;

    /**
     * This is the Square's default constructor, it instantiates each
     * square and makes it occupied (such as in the case of the border
     * squares). This constructor also generates all the default pieces
     * in the center of the board.
     */
    public Square(BorderPane gamePane, int x, int y,
                  Color color, boolean occupied, boolean border) {
        this.copy = false;
        this.gamePane = gamePane;
        this.occupied = occupied;
        this.border = border;
        int xCoord = x * Constants.SQUARE_WIDTH;
        int yCoord = y * Constants.SQUARE_WIDTH;
        this.square = new Rectangle(xCoord, yCoord,
                Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);
        this.square.setFill(color);
        this.square.setStroke(color.darker());
        this.gamePane.getChildren().add(this.square);
        // Center-pieces generation.
        if(occupied && !border){
            if(x == Constants.STARTING_PIECE_ONE && y == Constants.STARTING_PIECE_ONE
                    || x == Constants.STARTING_PIECE_TWO && y == Constants.STARTING_PIECE_TWO){
                this.pieceColor = Constants.BLACK;
            }
            else{
                this.pieceColor = Constants.WHITE;
            }
            this.piece = new Piece(this.gamePane, x, y, this.pieceColor);
        }
    }

    /**
     * This is a copy constructor for the square. It is used
     * by the test-board to create squares that are not
     * graphically represented and exist solely for the
     * minimax to calculate future moves.
     */
    public Square(Square originalSquare) {
        this.copy = true;
        this.gamePane = null;
        this.occupied = originalSquare.occupied;
        this.pieceColor = originalSquare.pieceColor;
        this.weight = originalSquare.weight;
        this.border = originalSquare.border;
    }

    /**
     * This is the addPiece method which actually adds pieces to the board,
     * by determining that it isn't a copy constructor piece, a new instance
     * of the piece class is instantiated and its color is assigned to
     * the pieceColor instance variable.
     */
    public void addPiece(int x, int y, Color color){
        this.pieceColor = color;
        if(!this.copy){
            this.piece = new Piece(this.gamePane, x, y, this.pieceColor);

        }
        this.pieceColor = color;
    }

    /**
     * Getter that return's the square's Y coord.
     */
    public double getY() {
        return this.square.getY();
    }

    /**
     * Getter that return's the square's X coord.
     */
    public double getX() {
        return this.square.getX();
    }

    /**
     * Setter that occupies the square.
     */
    public void occupy(){
        this.occupied = true;
    }

    /**
     * Boolean getter that returns if a square is occupied.
     */
    public boolean isOccupied(){
        return this.occupied;
    }

    /**
     * Getter that return's a pieces color.
     */
    public Color pieceColor(){
        return this.pieceColor;
    }

    /**
     * This method swaps a piece's color. It is used during
     * sandwiching when pieces need to be flipped.
     */
    public void swapPieceColor(){
        if(this.piece != null){
            this.piece.swapColor();
        }
        if(this.pieceColor == Constants.WHITE){
            this.pieceColor = Constants.BLACK;
        }
        else{
            this.pieceColor = Constants.WHITE;
        }
    }

    /**
     * This method sets a square's color to be a projected move's
     * color. This is done when it's a human player's turn to play.
     */
    public void setColor(boolean projected) {
        if(projected){
            this.square.setFill(Constants.PROJECTED_MOVE_COLOR);
        }
        else{
            this.square.setFill(Constants.BOARD_TILES_COLOR);
        }
    }

    /**
     * Setter that assigns a weight to a square from the weight
     * 2D array.
     */
    public void assignWeight(int i, int j) {
        this.weight = Constants.WEIGHTS[i][j];
    }

    /**
     * Getter that returns the weight of a square.
     */
    public int getWeight(){
        return this.weight;
    }

    /**
     * Used when resetting the board to remove the square
     * and piece graphically.
     */
    public void remove() {
        this.gamePane.getChildren().remove(this.square);
        if(this.occupied && !this.border){
            this.piece.remove();
        }
    }

    /**
     * Sets all the square and piece color's to their constant's color,
     * this is done for palette generation.
     */
    public void setAllColor(boolean white) {
        if(this.border){
            this.square.setFill(Constants.BORDER_TILES_COLOR);
        }
        else{
            this.square.setFill(Constants.BOARD_TILES_COLOR);
            if (this.piece != null) {
                if(white){
                    this.pieceColor = Constants.WHITE;
                }
                else{
                    this.pieceColor = Constants.BLACK;
                }
                this.piece.setColor();
            }
        }
    }
}
