import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementation of the "Vanila" BFS algorithm
 * (no closed list / duplicate pruning implementation)
 * Uses a FIFO queue
 */
public class BreadthFirstSearch extends SearchAlgorithmAbstract {
    private Queue<StateNodeInterface> queue;

    BreadthFirstSearch(){
        this.solutionCost = 0;
    }

    @Override
    public boolean performSearch(StateNodeInterface initialState){
        // initiate queue with initial state
        queue = new LinkedList<>();
        queue.add(initialState);

        while (queue.peek() != null){
            // pull head from queue
            StateNodeInterface currentState = queue.poll();
            this.numSearchedNodes++; // update number of nodes polled from data structure

            // stop when reaching goal state
            if (currentState.checkIfGoal()){
                this.computeSolutionPath(currentState);
                return true;
            }

            // create successor nodes by performing moves
            // and add the created nodes to the queue
            Move[] possibleMoves = {Move.UP, Move.DOWN, Move.LEFT, Move.RIGHT};
            for (Move move: possibleMoves){
                StateNodeInterface successor = currentState.createSuccessor(move);

                if (successor != null){ // case the move is not valid
                    this.queue.add(successor);
                }
            }
        }
        return false;
    }
}
