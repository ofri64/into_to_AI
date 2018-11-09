public interface SearchAlgorithmInterface {
    boolean performSearch(StateNodeInterface initialState);
    String getSolutionPath();
    int getSolutionCost();
    int getNumSearchedNodes();
}
