package othello;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * The board class handles a lot of the board-updating logic and
 * contains the copy constructors important for the minimax's
 * move evaluations. Most methods associated with piece and square
 * logic are contained here. Such as piece flipping, directional checking
 * and legal and valid move searching.
 */
public class Board {
    private final BorderPane gamePane;
    private final Square[][] board;

    /**
     * This is the default constructor, it takes in the gamePane,
     * to update pieces and squares graphically. It also sets gameOver to
     * false, and creates a new smart Square array for the entire board.
     * These squares are "smart" because they also may or may not contain
     * squares. The constructor also creates the board and assigns
     * the square weights for the minimax.
     */
    public Board(BorderPane gamePane) {
        this.gamePane = gamePane;
        this.board = new Square[Constants.SQUARE_AMOUNT][Constants.SQUARE_AMOUNT];
        this.createBoard();
        this.assignWeights();
    }

    /**
     * This is the copy constructor it is used in the minimax to
     * imitate a board evaluation without actually using
     * the game board. This is important for testing
     * out moves for high intelligence AI, where multiple
     * moves ahead are tested, as well as not changing thr original
     * board graphically.
     */
    public Board(Board originalBoard){
        this.gamePane = null;
        this.board = new Square[Constants.SQUARE_AMOUNT][Constants.SQUARE_AMOUNT];
        Square[][] oldBoard = originalBoard.getBoardArray();
        for(int i = 0; i < Constants.SQUARE_AMOUNT; i++) {
            for (int j = 0; j < Constants.SQUARE_AMOUNT; j++) {
                this.board[i][j] = new Square(oldBoard[i][j]);
            }
        }
    }

    /**
     * This method assigns custom board weights to each square,
     * these weights allow the minimax to analyze which moves
     * are better than others when planning ahead of the
     * opponent.
     */
    private void assignWeights() {
        for(int i = 1; i < Constants.SQUARE_AMOUNT-1; i++){
            for(int j = 1; j < Constants.SQUARE_AMOUNT-1; j++){
                this.board[i][j].assignWeight(i-1,j-1);
            }
        }
    }

    /**
     * This is a getter that returns the original board's
     * board array which is important for the copy constructor.
     */
    private Square[][] getBoardArray() {
        return this.board;
    }

    /**
     * This method creates the board used for othello, by using a couple for-loops
     * to create the squares on the border and in the center. It is
     * also in charge of creating the four starting pieces in the center.
     */
    private void createBoard() {
        for(int i = 0; i < Constants.SQUARE_AMOUNT; i++){
            for(int j = 0; j < Constants.SQUARE_AMOUNT; j++){
                if((i < 1 || i > Constants.INNER_SQUARE_AMOUNT)
                        || (j < 1 || j > Constants.INNER_SQUARE_AMOUNT)){
                    // Creates border blocks
                    this.board[i][j] = new Square(
                            this.gamePane,
                            j,
                            i,
                            Constants.BORDER_TILES_COLOR,
                            true,
                            true);
                }
                else if((i < Constants.START_PIECE_ONE && i > Constants.START_PIECE_TWO)
                        && (j < Constants.START_PIECE_ONE && j > Constants.START_PIECE_TWO)){
                    this.board[i][j] = new Square(
                            this.gamePane,
                            j,
                            i,
                            Constants.BOARD_TILES_COLOR,
                            true,
                            false);
                }
                else{
                    // Creates center null blocks
                    this.board[i][j] = new Square(
                            this.gamePane,
                            j,
                            i,
                            Constants.BOARD_TILES_COLOR,
                            false,
                            false);
                }
            }
        }
    }

    /**
     * This method tells the square at the given coordinate to
     * place a piece down both graphically and logically
     * and occupy that space on the board.
     */
    public void placePiece(int x, int y, Player current) {
        if(current.isWhite()){
            this.board[y][x].addPiece(x, y, Constants.WHITE);
        }
        else{
            this.board[y][x].addPiece(x, y, Constants.BLACK);
        }
        this.board[y][x].occupy();
    }

    /**
     * This getter returns the square at the given coordinates
     * in the board array.
     */
    public Square getSquare(int i, int j){
        return this.board[i][j];
    }

    /**
     * This method is used by the minimax to evaluate a move on
     * a testboard using the assigned board weights. It collects
     * the current player's and opponent's weights and subtracts them
     * to output a number responsible for a move's value
     */
    public int evaluateBoard(Player currentPlayer) {
        int allyValues = 0;
        int opValues = 0;
        for(int i = 1; i < Constants.SQUARE_AMOUNT-1; i++){
            for(int j = 1; j < Constants.SQUARE_AMOUNT-1; j++){
                if(this.board[i][j].pieceColor() == Constants.WHITE){
                    if(currentPlayer.isWhite()){
                        allyValues += this.board[i][j].getWeight();
                    }
                    else{
                        opValues += this.board[i][j].getWeight();
                    }
                }
                else if(this.board[i][j].pieceColor() == Constants.BLACK){
                    if(currentPlayer.isWhite()){
                        opValues += this.board[i][j].getWeight();
                    }
                    else{
                        allyValues += this.board[i][j].getWeight();
                    }
                }
            }
        }
        return allyValues - opValues;
    }

    /**
     * This method takes an arraylist for which pieces need to be
     * flipped, and swaps their color to simulate piece flipping.
     * That arraylist then gets cleared.
     */
    public void flipPieces(ArrayList<Square> piecesToFlip) {
        for(Square piece : piecesToFlip){
            piece.swapPieceColor();
        }
        piecesToFlip.clear();
    }

    /**
     * This method is used by the minimax to test out a move on the board
     * without calling the referee class. It uses a sin equation to call
     * checkOneDirection in all 8 directions from the placed, piece.
     * If a sandwich happened, it flips the pieces.
     */
    public void testMove(Move legalMove, Player currentPlayer) {
        this.placePiece(legalMove.getCol(),legalMove.getRow(), currentPlayer);
        this.board[legalMove.getRow()][legalMove.getCol()].occupy();
        Color color;
        if(currentPlayer.isWhite()){
            color = Constants.WHITE;
        } else {
            color = Constants.BLACK;
        }
        for(int k = 0; k < Constants.INNER_SQUARE_AMOUNT; k++){
            int row = (int)(Math.round(Math.sin(Constants.SIN_SCALE*k+Constants.SIN_OFFSET)));
            int col = (int)(Math.round(Math.sin(Constants.SIN_SCALE*k)));
            ArrayList<Square> piecesInDirection =
                    this.checkOneDirection(
                            legalMove.getRow() + row,
                            legalMove.getCol() + col,
                            row,
                            col,
                            color,
                            false);
            if(!piecesInDirection.isEmpty()){
                piecesInDirection.remove(piecesInDirection.size() - 1);
                this.flipPieces(piecesInDirection);
            }
        }
    }

    /**
     * This method is the makeHumanMove method that is called when a mouse is clicked.
     * By tracking the position of the click, the method determines what square
     * within the board's square array the player is trying to access.
     */
    public void makeHumanMove(MouseEvent e, Referee referee){
        for(int i = 0; i < Constants.SQUARE_AMOUNT; i++){
            for(int j = 0; j < Constants.SQUARE_AMOUNT; j++){
                if(e.getSceneX() > this.board[i][j].getX()
                        && e.getSceneX() < this.board[i][j].getX()
                        + Constants.SQUARE_WIDTH
                        && e.getSceneY() > this.board[i][j].getY()
                        && e.getSceneY() < this.board[i][j].getY()
                        + Constants.SQUARE_WIDTH ){
                    referee.refDecideifValidMove(i, j);
                }
            }
        }
    }

    /**
     * This method returns all the legal moves left for the given player.
     * It does this by scanning the entire board and determining if
     * an empty square is a valid move for the player to take. It
     * returns and arraylist of all the valid moves.
     */
    public ArrayList<Move> getAllLegalMoves(Player currentPlayer) {
        Color color;
        if(currentPlayer.isWhite()){
            color = Constants.WHITE;
        }
        else{
            color = Constants.BLACK;
        }
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for(int i = 1; i < Constants.SQUARE_AMOUNT-1; i++) {
            for (int j = 1; j < Constants.SQUARE_AMOUNT-1; j++) {
                if(this.validMove(i, j, false, color)){
                    possibleMoves.add(new Move(i, j));
                }
            }
        }
        return possibleMoves;
    }

    /**
     * This method returns a boolean depending on if the coordinates
     * of a position is valid for a piece to be place. It relies on color
     * to determine if the move is valid for the player. It also
     * checks in all directions for valid sandwiching. And if the scan
     * is for collecting pieces, it calls flipPieces.
     */
    public boolean validMove(int i, int j, boolean collectPieces, Color color) {
        if(this.board[i][j].isOccupied()){
            return false;
        }
        boolean canMove = false;
        for(int k = 0; k < Constants.INNER_SQUARE_AMOUNT; k++){
            int row = (int)(Math.round(Math.sin(Constants.SIN_SCALE*k+Constants.SIN_OFFSET)));
            int col = (int)(Math.round(Math.sin(Constants.SIN_SCALE*k)));
            ArrayList<Square> piecesInDirection =
                    this.checkOneDirection(
                            i + row,
                            j + col,
                            row,
                            col,
                            color,
                            false);
            if(!piecesInDirection.isEmpty()){
                canMove = true;
                if(collectPieces){
                    piecesInDirection.remove(piecesInDirection.size() - 1);
                    this.flipPieces(piecesInDirection);
                }
            }
        }
        return canMove;
    }

    /**
     * This method scans through the entire board to
     * see if the give color has valid moves that it can take.
     * It then sets the square's colors to display such legal moves.
     */
    public void displayPossibleMoves(Color color) {
        for(int i = 1; i < Constants.SQUARE_AMOUNT-1; i++) {
            for (int j = 1; j < Constants.SQUARE_AMOUNT-1; j++) {
                if(this.validMove(i, j, false, color)){
                    this.board[i][j].setColor(true);
                }
                else{
                    this.board[i][j].setColor(false);
                }
            }
        }
    }

    /**
     * Check One direction takes in the coordinates of a square as well as a
     * tested direction. The method than checks for "sandwiches" in each of these
     * directions which helps determine which pieces to flip and if a move is valid.
     * The method returns an arraylist of which squares should then be flipped.
     */
    public ArrayList<Square> checkOneDirection(
            int row,
            int col,
            int rowDir,
            int colDir,
            Color color,
            boolean seenOpponent){
        ArrayList<Square> piecesToFlip = new ArrayList<>();
        if((row > Constants.INNER_SQUARE_AMOUNT || row < 1)
                || (col > Constants.INNER_SQUARE_AMOUNT || col < 1)
                || !this.board[row][col].isOccupied()){
            // Returns empty if out of bounds or occupied
            return piecesToFlip;
        }
        if(this.board[row][col].pieceColor() != color){
            // Adds if not of the given color
            piecesToFlip.add(this.board[row][col]);
            // scans for further pieces through recursion
            ArrayList<Square> furtherPieces =
                    this.checkOneDirection(
                            row + rowDir,
                            col + colDir,
                            rowDir,
                            colDir,
                            color,
                            true);
            if(!furtherPieces.isEmpty()){
                piecesToFlip.addAll(furtherPieces);
            }
            else{
                piecesToFlip.clear();
            }
            return piecesToFlip;
        }
        // Adds if has already seen opponent
        if(this.board[row][col].pieceColor() == color && seenOpponent){
            piecesToFlip.add(this.board[row][col]);
            return piecesToFlip;
        }
        return new ArrayList<>();
    }

    /**
     * This method checks if game is over by checking if both players
     * have no more legal moves left.
     */
    public boolean checkGameOver(Player currentPlayer, Player otherPlayer) {
        ArrayList<Move> currentMoves = this.getAllLegalMoves(currentPlayer);
        ArrayList<Move> otherMoves = this.getAllLegalMoves(otherPlayer);
        return currentMoves.isEmpty() && otherMoves.isEmpty();
    }

    /**
     * This method scans the board to see how many pieces of
     * each color there are. It then determines who won, by comparing scores.
     * It returns an int corresponding with which color won or if there was a tie.
     */
    public int getWinStatus() {
        int whiteTempScore = 0;
        int blackTempScore = 0;
        for(int i = 1; i < Constants.SQUARE_AMOUNT-1; i++) {
            for (int j = 1; j < Constants.SQUARE_AMOUNT-1; j++) {
                if(this.board[i][j].pieceColor() == Constants.WHITE){
                    whiteTempScore++;
                }
                else if(this.board[i][j].pieceColor() == Constants.BLACK){
                    blackTempScore++;
                }
            }
        }
        if(whiteTempScore > blackTempScore){
            return 1;
        }
        else if(whiteTempScore < blackTempScore){
            return -1;
        }
        else{
            return 0;
        }
    }

    /**
     * This method applies the palette colors to each square and piece.
     */
    public void applyPaletteBoard() {
        for (int i = 0; i < Constants.SQUARE_AMOUNT; i++) {
            for (int j = 0; j < Constants.SQUARE_AMOUNT; j++) {
                this.board[i][j].setAllColor(true);
            }
        }
    }

    /**
     * This method removes each square and piece from the board,
     * and then creates the board again reassigning each respective
     * weight.
     */
    public void boardReset() {
        for (int i = 0; i < Constants.SQUARE_AMOUNT; i++) {
            for (int j = 0; j < Constants.SQUARE_AMOUNT; j++) {
                this.board[i][j].remove();
            }
        }
        this.createBoard();
        this.assignWeights();
    }

    /**
     * This method deletes displayed moves if the player decides to
     * implement computer play when a human player has already played.
     */
    public void deleteDisplayedMoves() {
        for(int i = 1; i < Constants.SQUARE_AMOUNT-1; i++) {
            for (int j = 1; j < Constants.SQUARE_AMOUNT-1; j++) {
                this.board[i][j].setColor(false);
            }
        }
    }
}
