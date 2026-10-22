package othello;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This is a class that represents each othello piece.
 *
 * It instantiates gamePane so that each piece can be added graphically.
 * It is added logically within the Square class because the board's
 * square array applies to both pieces and squares.
 */
public class Piece {
    private final BorderPane gamePane;
    private Color color;
    private final Circle piece;

    /**
     * This is the piece constructor, it instantiates each piece using
     * passed in parameters that make each piece unique. Namely, the color
     * and the coordinates responsible for the piece's position.
     */
    public Piece(BorderPane gamePane, int x, int y, Color color) {
        this.gamePane = gamePane;
        this.color = color;
        int xCoord = x * Constants.SQUARE_WIDTH
                + Constants.SQUARE_WIDTH/Constants.PIECE_DIVIDER;
        int yCoord = y * Constants.SQUARE_WIDTH
                + Constants.SQUARE_WIDTH/Constants.PIECE_DIVIDER;
        this.piece = new Circle(xCoord, yCoord, Constants.PIECE_RADIUS, color);
        this.piece.setStroke(this.color.darker());
        this.piece.setStrokeWidth(Constants.STROKE_WIDTH);
        this.gamePane.getChildren().add(this.piece);
    }

    /**
     * This method swaps each piece's color to the opposite color.
     * It is called when the pieces are being flipped due to
     * sandwiching.
     */
    public void swapColor() {
        if(this.color == Constants.WHITE){
            this.color = Constants.BLACK;
        }
        else{
            this.color = Constants.WHITE;
        }
        this.setColor();
    }

    /**
     * This method sets each pieces color and stroke color.
     */
    public void setColor() {
        this.piece.setFill(this.color);
        this.piece.setStroke(this.color.darker());
    }

    /**
     * This method graphically removes the piece when the
     * board resets.
     */
    public void remove() {
        this.gamePane.getChildren().remove(this.piece);
    }
}
