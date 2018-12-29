/**
 * Implementation of a node for A* algorithm use
 * Adds node's depth in the graph and a data item of the node's evaluation function (f) score
 * Uses the Manhattan distance static method for calculation of the heuristic
 */
public class AStarManhattanDistanceNode extends StateNodeAbstract {

    private int nodeDepth;
    private int evaluationFunctionScore;

    /**
     * Constructor for the class.
     * Performs the calculation the evaluation function (as it is efficient so the constructor
     * doesn't suppose to run for a long time)
     */
    public AStarManhattanDistanceNode(StateNodeInterface fatherNode, Move creationMove, GameBoardInterface gameState, int nodeDepth){
        super(fatherNode, creationMove, gameState);
        this.nodeDepth = nodeDepth;
        int gScore = nodeDepth;
        int hScore = PuzzleHeuristics.ManhattanDistanceScore(this);
        this.evaluationFunctionScore = gScore + hScore;
    }

    public int getEvaluationFunctionScore() {
        return evaluationFunctionScore;
    }

    @Override
    public StateNodeInterface createSuccessor(Move move) {
        if (this.gameState.isValidMove(move)){
            GameBoardInterface successorState = this.gameState.performMove(move);
            return new AStarManhattanDistanceNode(this, move, successorState, this.nodeDepth+1);
        }
        else{
            return null;
        }
    }

    public int getDepth() {
        return this.nodeDepth;
    }
}
