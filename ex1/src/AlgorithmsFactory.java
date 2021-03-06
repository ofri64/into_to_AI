/**
 * Class implementing the "Factory" design pattern as a static method.
 * Getting as input n, board data and the desired algorithm type and returning matching objects
 * according to the algorithm type specified
 */

public class AlgorithmsFactory {
    public static SearchAlgorithmInterface runSearchAlgorithm(int n, int[] initialBoard, int algorithmID){

        StateNodeInterface initialStateNode;
        SearchAlgorithmInterface algorithm;
        GameBoardInterface initialGameState = new GameBoard(n, initialBoard);
        if (algorithmID == 1){ // IDS algorithm - use DFS node
            initialStateNode = new DFSNode(null, null, initialGameState, 0);
            algorithm = new IterativeDeepingSearch();
        }
        else if (algorithmID == 2){ // BFS algorithm - use BFS node
            initialStateNode = new BFSNode(null, null, initialGameState);
            algorithm = new BreadthFirstSearch();
        }
        else { // A* algorithm - again use the matching graph node object
            initialStateNode = new AStarManhattanDistanceNode(null, null, initialGameState, 0);
            algorithm = new AStarSearch();
        }
        // call the perform search defined in the search algorithm interface
        // that updates the algorithm object data type at the end of the run
        algorithm.performSearch(initialStateNode);
        return algorithm;
    }

}
