public class GameBoard implements GameBoardInterface {
    private int n;
    private int[] board;
    private int arrayLength;
    private int zeroPosition;

    public GameBoard(int n, int[] initialState) {
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
    private GameBoard(GameBoard gameToCopy){
        this.n = gameToCopy.n;
        this.arrayLength = gameToCopy.arrayLength;
        this.board = new int[this.arrayLength];
        System.arraycopy(gameToCopy.board, 0, this.board, 0, this.arrayLength);
        this.zeroPosition = gameToCopy.zeroPosition;

    }

    @Override
    public Boolean isGoalState() {
        for (int i = 0; i < this.arrayLength; i++) {

            if (i != arrayLength - 1) {
                if (this.board[i] != i + 1) return false;

            } else {
                if (this.board[i] != 0) return false;
            }
        }
            return true;
    }

    @Override
    public Boolean isValidMove(Move move) {
        switch (move) {
            case UP:
                if (zeroPosition >= this.arrayLength - this.n) return false;
                break;
            case DOWN:
                if (zeroPosition < this.n) return false;
                break;
            case LEFT:
                if (zeroPosition % this.n == this.n) return false;
                break;
            case RIGHT:
                if (zeroPosition % this.n == 0) return false;
                break;
        }
        return true;
    }

    @Override
    public GameBoardInterface performMove(Move move) {
        GameBoard newBoard = new GameBoard(this);
        int positionToSwitch = -1;
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
        newBoard.board[positionToSwitch] = this.board[this.zeroPosition];
        newBoard.board[this.zeroPosition] = this.board[positionToSwitch];
        newBoard.zeroPosition = positionToSwitch;

        return newBoard;
    }

}