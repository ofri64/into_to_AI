public interface SearchAlgorithmInterface {
    void pefromSearch(StateNodeInterface initialState);
    String getSoultionPath();
    int getSoultionCost();
    int getNumSearchedNodes();
}
