public class DFSNode extends StateNodeAbstract{

    private int nodeDepth;

    public DFSNode(StateNodeInterface fatherNode, Move creationMove, GameBoardInterface gameState, int nodeDepth){
        super(fatherNode, creationMove, gameState);
        this.nodeDepth = nodeDepth;
    }

    @Override
    public StateNodeInterface createSuccessor(Move move) {
        if (this.gameState.isValidMove(move)){
            GameBoardInterface successorState = this.gameState.performMove(move);
            return new DFSNode(this, move, successorState, this.nodeDepth+1);
        }
        else{
            return null;
        }
    }

    public int getDepth() {
        return this.nodeDepth;
    }
}
