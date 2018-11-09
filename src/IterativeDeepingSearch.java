import java.util.Stack;

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

    private boolean performLimitedDFS(StateNodeInterface initialState, int depthLimit){
        this.stack = new Stack<>();
        this.stack.push(initialState);

        while (!this.stack.empty()){
            StateNodeInterface currentState = this.stack.pop();
            this.numSearchedNodes++;

            if (currentState.checkIfGoal()){
                this.computeSolutionPath(currentState);
                this.solutionCost = currentState.getDepth();
                return true;
            }
            Move[] possibleMoves = {Move.RIGHT, Move.LEFT, Move.DOWN, Move.UP};
            for (Move move: possibleMoves){
                StateNodeInterface successor = currentState.createSuccessor(move);

                if (successor != null && successor.getDepth() <= depthLimit){
                    this.stack.push(successor);
                }
            }
        }
        return false;
    }
}
