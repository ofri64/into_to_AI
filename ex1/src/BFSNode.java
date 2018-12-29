/**
 * Implementation of a node for BFS algorithm use
 * no additional data items are required besides those defined in the abstract class
 */
public class BFSNode extends StateNodeAbstract {

    public BFSNode(StateNodeInterface fatherNode, Move creationMove, GameBoardInterface gameState){
        super(fatherNode, creationMove, gameState);
    }

    /**
     *
     * @param move - one of the following {up, down, left, right}
     * @return new BFS node, a successor of the current one
     */
    @Override
    public StateNodeInterface createSuccessor(Move move) {
        // return null if the move is not valid 
        if (this.gameState.isValidMove(move)){
            GameBoardInterface successorState = this.gameState.performMove(move);
            return new BFSNode(this, move, successorState);
        }
        else {
            return null;
        }
    }
}
