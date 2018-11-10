public class AStarNode extends DFSNode {

    private int evaluationFunctionScore;

    public AStarNode(StateNodeInterface fatherNode, Move creationMove, GameBoardInterface gameState, int nodeDepth){
        super(fatherNode, creationMove, gameState, nodeDepth);
        int gScore = nodeDepth;
        int hScore = PuzzleHeuristics.ManhattanDistanceScore(this);
        this.evaluationFunctionScore = gScore + hScore;
    }

    public int getEvaluationFunctionScore() {
        return evaluationFunctionScore;
    }
}
