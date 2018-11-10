public class AlgorithmsFactory {
    public static SearchAlgorithmInterface runSearchAlgorithm(int n, int[] initialBoard, int algorithmID){

        SearchAlgorithmInterface algorithm;
        GameBoardInterface initialGameState = new GameBoard(n, initialBoard);
        if (algorithmID == 1){
            StateNodeInterface initialStateNode = new DFSNode(null, null, initialGameState, 1);
            algorithm = new IterativeDeepingSearch();
            algorithm.performSearch(initialStateNode);
        }
        else if (algorithmID == 2){
            StateNodeInterface initialStateNode = new BFSNode(null, null, initialGameState);
            algorithm = new BreadthFirstSearch();
            algorithm.performSearch(initialStateNode);
        }
        else {
            algorithm = null;
        }
        return algorithm;
    }

}
