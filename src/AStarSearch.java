import java.util.PriorityQueue;

public class AStarSearch extends SearchAlgorithmAbstract {
    private PriorityQueue<AStarManhattanDistanceNode> queue;
    static int INITIAL_CAPACITY = 30;

    @Override
    public boolean performSearch(StateNodeInterface initialState) {
        queue = new PriorityQueue<AStarManhattanDistanceNode>(INITIAL_CAPACITY, new AStarComperator());
        AStarManhattanDistanceNode initAStarNode = (AStarManhattanDistanceNode) initialState;
        queue.add(initAStarNode);
        while (queue.peek() != null) {
            AStarManhattanDistanceNode currentState = queue.poll();
            this.numSearchedNodes++;

            if (currentState.checkIfGoal()) {
                this.computeSolutionPath(currentState);
                this.solutionCost = currentState.getEvaluationFunctionScore();
                return true;
            }

            Move[] possibleMoves = {Move.UP, Move.DOWN, Move.LEFT, Move.RIGHT};
            for (Move move : possibleMoves) {
                AStarManhattanDistanceNode successor = (AStarManhattanDistanceNode) currentState.createSuccessor(move);

                if (successor != null) {
                    this.queue.add(successor);
                }
            }
        }
        return false;
    }
}



