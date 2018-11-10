public class AStarManhattanDistanceNode extends StateNodeAbstract {

    private int nodeDepth;
    private int evaluationFunctionScore;

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
