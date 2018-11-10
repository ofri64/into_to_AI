public interface GameBoardInterface {
    Boolean isValidMove(Move move);
    GameBoardInterface performMove(Move move);
    Boolean isGoalState();
    int[] getGameBoardArray();
}
