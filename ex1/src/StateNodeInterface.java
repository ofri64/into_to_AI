/**
 * An interface for an object representing a graph node
 * which holds vital data for running search algorithms and extracting their output
 * at the end of the run
 */
public interface StateNodeInterface {
    /**
     * Get a pointer to the father node of the current node
     * @return father node
     */
    StateNodeInterface getFather();

    /**
     * Get the move used to create the current node
     * @return move used to create current state from it's father node
     */
    Move getCreationMove();

    /**
     * Create a successor node by applying a given move on the current state
     * @param move - one of the following {up, down, left, right}
     * @return a new node object, holding the matching game board object
     * after performing the move
     */
    StateNodeInterface createSuccessor(Move move);

    /**
     * Check if the current node represents a goal state
     * @return true if goal state is reached, false otherwise
     */
    boolean checkIfGoal();

    /**
     * Getter for an underlying game board object
     * @return raw game board data of the node
     */
    GameBoardInterface getGameBoard();
}
