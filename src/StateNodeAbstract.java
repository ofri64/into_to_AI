public abstract class StateNodeAbstract implements StateNodeInterface {
    protected StateNodeInterface father;
    protected Move creationMove;
    protected GameBoardInterface gameState;

    public StateNodeAbstract(StateNodeInterface fatherNode, Move creationMove, GameBoardInterface gameState){
        this.father = fatherNode;
        this.creationMove = creationMove;
        this.gameState = gameState;
    }

    @Override
    public GameBoardInterface getGameBoard() { return this.gameState; }

    @Override
    public StateNodeInterface getFather() {
        return father;
    }

    @Override
    public Move getCreationMove() {
        return creationMove;
    }

    @Override
    public boolean checkIfGoal(){
        return this.gameState.isGoalState();
    }
}
