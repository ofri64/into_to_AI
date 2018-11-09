import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends SearchAlgorithmAbstract {
    private Queue<StateNodeInterface> queue;

    BreadthFirstSearch(){
        this.solutionCost = 0;
    }

    @Override
    public boolean performSearch(StateNodeInterface initialState){
        queue = new LinkedList<>();
        queue.add(initialState);

        while (queue.peek() != null){
            StateNodeInterface currentState = queue.poll();
            this.numSearchedNodes++;

            if (currentState.checkIfGoal()){
                this.computeSolutionPath(currentState);
                return true;
            }

            Move[] possibleMoves = {Move.UP, Move.DOWN, Move.LEFT, Move.RIGHT};
            for (Move move: possibleMoves){
                StateNodeInterface successor = currentState.createSuccessor(move);

                if (successor != null){
                    this.queue.add(successor);
                }
            }
        }
        return false;
    }
}
