/**
 * Abstract class implementing common getters/methods used by all sub nodes objects
 */
public abstract class StateNodeAbstract implements StateNodeInterface {
    protected StateNodeInterface father;
    protected Move creationMove;
    protected GameBoardInterface gameState;

    /**
     * Create a common constructor, initiating data items shared across all node's implementations
     * @param fatherNode pointer to father node object
     * @param creationMove move used to create this object
     * @param gameState raw game board object
     */
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

    /**
     * Delegate the check for the raw game state object
     * @return true for goal state, false otherwise
     */
    @Override
    public boolean checkIfGoal(){
        return this.gameState.isGoalState();
    }
}
