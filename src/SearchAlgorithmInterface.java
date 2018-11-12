/**
 * Interface defining the desired functionality from a search algorithm
 */
public interface SearchAlgorithmInterface {
    /**
     * Performs the search for a goal state given an initial state
     * @param initialState state to start the search from
     * @return true if a goal state was reached, and false (or doesn't stop) otherwise
     */
    boolean performSearch(StateNodeInterface initialState);

    /**
     * Returns a string representing the sequence of moves performed to reach
     * the goal state from the initial state
     * @return
     */
    String getSolutionPath();

    /**
     * Cost of solution - algorithm specific
     * @return the cost of the goal state reached
     */
    int getSolutionCost();

    /**
     *
     * @return number of nodes polled out from the algorithm's data structure
     */
    int getNumSearchedNodes();
}
