public class StateNode implements StateNodeInterface{

    private StateNodeInterface father;
    private Move creationMove;
    private GameBoardInterface gameState;
    private int nodeDepth;

    public StateNode(StateNodeInterface fatherNode, Move creationMove, GameBoardInterface gameState, int nodeDepth){
        this.father = fatherNode;
        this.creationMove = creationMove;
        this.gameState = gameState;
        this.nodeDepth = nodeDepth;
    }

    @Override
    public StateNodeInterface getFather() {
        return father;
    }

    @Override
    public Move getCreationMove() {
        return creationMove;
    }

    @Override
    public StateNodeInterface createSuccessor(Move move) {
        if (this.gameState.isValidMove(move)){
            GameBoardInterface successorState = this.gameState.performMove(move);
            return new StateNode(this, move, successorState, this.nodeDepth+1);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean checkIfGoal(){
        return this.gameState.isGoalState();
    }

    @Override
    public int getDepth() {
        return this.nodeDepth;
    }
}
