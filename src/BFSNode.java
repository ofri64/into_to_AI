public class BFSNode extends StateNodeAbstract {

    public BFSNode(StateNodeInterface fatherNode, Move creationMove, GameBoardInterface gameState){
        super(fatherNode, creationMove, gameState);
    }

    @Override
    public StateNodeInterface createSuccessor(Move move) {
        if (this.gameState.isValidMove(move)){
            GameBoardInterface successorState = this.gameState.performMove(move);
            return new BFSNode(this, move, successorState);
        }
        else {
            return null;
        }
    }
}
