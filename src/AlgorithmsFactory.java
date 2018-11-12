public class AlgorithmsFactory {
    public static SearchAlgorithmInterface runSearchAlgorithm(int n, int[] initialBoard, int algorithmID){

        StateNodeInterface initialStateNode;
        SearchAlgorithmInterface algorithm;
        GameBoardInterface initialGameState = new GameBoard(n, initialBoard);
        if (algorithmID == 1){
            initialStateNode = new DFSNode(null, null, initialGameState, 1);
            algorithm = new IterativeDeepingSearch();
        }
        else if (algorithmID == 2){
            initialStateNode = new BFSNode(null, null, initialGameState);
            algorithm = new BreadthFirstSearch();
        }
        else {
            initialStateNode = new AStarManhattanDistanceNode(null, null, initialGameState, 1);
            algorithm = new AStarSearch();
        }
        algorithm.performSearch(initialStateNode);
        return algorithm;
    }

}
