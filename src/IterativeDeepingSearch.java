import java.util.Stack;

/**
 * Implementation of the "Vanila" IDS algorithm
 * (no closed list / duplicate pruning implementation)
 * Uses a LIFO stack
 */
public class IterativeDeepingSearch extends SearchAlgorithmAbstract {

    private Stack<StateNodeInterface> stack;

    @Override
    public boolean performSearch(StateNodeInterface initialState) {
        int currentDepth = 1;
        while (!performLimitedDFS(initialState, currentDepth)){
            this.numSearchedNodes = 0;
            currentDepth++;
        }
        return true;
    }

    /**
     * Perform one iteration of "limited" DFS
     * @param initialState - state to start from
     * @param depthLimit - depth limit
     * @return true if a goal state was reached, false if no goal state was obtained
     * within the current depth limitation
     */
    private boolean performLimitedDFS(StateNodeInterface initialState, int depthLimit){
        this.stack = new Stack<>();
        this.stack.push(initialState);

        while (!this.stack.empty()){
            DFSNode currentState = (DFSNode) this.stack.pop();
            this.numSearchedNodes++;

            if (currentState.checkIfGoal()){
                this.computeSolutionPath(currentState);
                this.solutionCost = currentState.getDepth();
                return true;
            }
            Move[] possibleMoves = {Move.RIGHT, Move.LEFT, Move.DOWN, Move.UP};
            for (Move move: possibleMoves){
                DFSNode successor = (DFSNode) currentState.createSuccessor(move);

                // here check that the move is "valid" by combining two conditions:
                // the original valid check (not out of board)
                // and depth limitation of the node
                if (successor != null && successor.getDepth() <= depthLimit){
                    this.stack.push(successor);
                }
            }
        }
        return false;
    }
}
