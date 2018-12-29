/***
 * Interface defining services requested from an object representing
 * the game board it self
 */

public interface GameBoardInterface {

    /***
     * Check is a given move is valid for the current state of the game board
     * @param move - one of the following {up, down, left, right}
     * @return true if the move is possible within the game borders, false otherwise
     */

    Boolean isValidMove(Move move);

    /***
     * Perform a given move and change the game state, assuming it is valid
     * @param move - one of the following {up, down, left, right}
     * @return a new game board object, after performing the needed move
     *
     */
    GameBoardInterface performMove(Move move);

    /***
     * Check whether the current state ends the game
     * @return true if the current game board represents a goal state
     */
    Boolean isGoalState();

    /**
     * Getter method for the actual array object for the game board
     * @return actual game board
     */
    int[] getGameBoardArray();

}
