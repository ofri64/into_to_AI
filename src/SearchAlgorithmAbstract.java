/**
 * Abstract class implementing common getters/methods used by all implementation algorithms
 */
public abstract class SearchAlgorithmAbstract implements  SearchAlgorithmInterface{
    protected String solutionPath;
    protected int solutionCost;
    protected int numSearchedNodes;

    /**
     * Defines a default constructor initiating the solution data items
     */
    public SearchAlgorithmAbstract(){
        this.solutionPath = "";
        this.numSearchedNodes = 0;
    }

    @Override
    public int getSolutionCost() {
        return solutionCost;
    }

    @Override
    public int getNumSearchedNodes() {
        return numSearchedNodes;
    }

    @Override
    public String getSolutionPath() {
        return solutionPath;
    }

    /**
     * Implementation of performing backtrack
     * @param goalState goal state to perform backtrack until reaching initial state
     */
    protected void computeSolutionPath(StateNodeInterface goalState){
        StateNodeInterface currentState = goalState;

        while (currentState.getFather() != null) {
            // concatenate creating moves to create a string representing sequence of moves
            solutionPath = currentState.getCreationMove().getMove() + solutionPath;
            currentState = currentState.getFather();
        }
    }
}
