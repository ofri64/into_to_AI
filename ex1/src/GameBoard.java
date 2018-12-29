/**
 * Game board class implementing game board interface.
 * Responsible for handling board data representation
 */

public class GameBoard implements GameBoardInterface {
    private int n;
    private int[] board;
    private int arrayLength;
    private int zeroPosition;

    /**
     * Constructor
     * @param n board vertical/horizontal size
     * @param initialState array of integer representing the initial state
     */
    public GameBoard(int n, int[] initialState) {
        // initiate object data, including zero (empty) position
        this.n = n;
        this.arrayLength = initialState.length;
        this.board = new int[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            this.board[i] = initialState[i];
            if (initialState[i] == 0) {
                zeroPosition = i;
            }
        }
    }

    /**
     * Copy constructor.
     * Uses to generate game moves
     * @param gameToCopy existing game board object
     */
    private GameBoard(GameBoard gameToCopy){
        this.n = gameToCopy.n;
        this.arrayLength = gameToCopy.arrayLength;
        this.board = new int[this.arrayLength];
        System.arraycopy(gameToCopy.board, 0, this.board, 0, this.arrayLength);
        this.zeroPosition = gameToCopy.zeroPosition;

    }

    @Override
    public int[] getGameBoardArray() {
        return board;
    }

    /**
     * Actual implementation of checking goal states
     * @return true if goal state, false otherwise
     */
    @Override
    public Boolean isGoalState() {
        for (int i = 0; i < this.arrayLength; i++) {

            if (i != arrayLength - 1) { // if it is not the empty position
                if (this.board[i] != i + 1) return false;

            } else { // the last position of the board suppose to be the empty position
                if (this.board[i] != 0) return false;
            }
        }
        // if all number are in the matching position - it's a goal!
        return true;
    }

    /**
     * Check that we don't exit the board position
     * @param move - one of the following {up, down, left, right}
     * @return true if the move is valid, false otherwise
     */
    @Override
    public Boolean isValidMove(Move move) {
        switch (move) {
            case UP: // last row of board matrix
                if (zeroPosition >= this.arrayLength - this.n) return false;
                break;
            case DOWN: // fist row of board matrix
                if (zeroPosition < this.n) return false;
                break;
            case LEFT: // rightmost column of board matrix
                if (zeroPosition % this.n == this.n-1) return false;
                break;
            case RIGHT: // leftmost column of board matrix
                if (zeroPosition % this.n == 0) return false;
                break;
        }
        // all other cases are legal
        return true;
    }

    /**
     * Implementing the perform move and returning a new board object
     * @param move - one of the following {up, down, left, right}
     * @return a new object after performing the desired move
     */
    @Override
    public GameBoardInterface performMove(Move move) {
        // First use the Copy constructor to obtain a copy of the current state
        GameBoard newBoard = new GameBoard(this);
        int positionToSwitch = -1;
        // compute to which position the zero (empty) should move
        switch (move) {
            case UP:
                positionToSwitch = this.zeroPosition + this.n;
                break;
            case DOWN:
                positionToSwitch = this.zeroPosition - this.n;
                break;
            case LEFT:
                positionToSwitch = this.zeroPosition + 1;
                break;
            case RIGHT:
                positionToSwitch = this.zeroPosition - 1;
                break;
        }
        // perform the actual replacement given the desired index
        // also update new zero position
        newBoard.board[positionToSwitch] = this.board[this.zeroPosition];
        newBoard.board[this.zeroPosition] = this.board[positionToSwitch];
        newBoard.zeroPosition = positionToSwitch;

        return newBoard;
    }

}