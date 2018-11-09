public abstract class SearchAlgorithmAbstract implements  SearchAlgorithmInterface{
    protected String solutionPath;
    protected int solutionCost;
    protected int numSearchedNodes;

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

    protected void computeSolutionPath(StateNodeInterface goalState){
        StateNodeInterface currentState = goalState;
        while (currentState.getFather() != null){
            solutionPath = currentState.getCreationMove().getMove() + solutionPath;
            currentState = currentState.getFather();
        }
    }
}
