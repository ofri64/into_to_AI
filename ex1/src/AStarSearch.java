import java.util.PriorityQueue;

/**
 * Implementation of the "Vanila" A* algorithm
 * (no closed list / duplicate pruning implementation)
 * Uses a Priority queue with a special comperator object
 */
public class AStarSearch extends SearchAlgorithmAbstract {
    private PriorityQueue<AStarManhattanDistanceNode> queue;
    static int INITIAL_CAPACITY = 30;

    @Override
    public boolean performSearch(StateNodeInterface initialState) {
        // create the object using the comperator and an initial capacity
        queue = new PriorityQueue<>(INITIAL_CAPACITY, new AStarComperator());

        // initiate A* node for initial state and add it to Priority queue
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
                    // adding to queue according to the priority defined by the comperator object
                    this.queue.add(successor);
                }
            }
        }
        return false;
    }
}



