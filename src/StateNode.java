public class StateNode implements StateNodeInterface{

    private StateNodeInterface father;
    private Move creationMove;
    private GameBoardInterface gameState;

    public StateNode(StateNodeInterface fatherNode, Move creationMove, GameBoardInterface gameState){
        this.father = fatherNode;
        this.creationMove = creationMove;
        this.gameState = gameState;
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
            return new StateNode(this, move, successorState);
        }
        else{
            return null;
        }
    }

    @Override
    public boolean checkIfGoal(){
        return this.gameState.isGoalState();
    }
}
