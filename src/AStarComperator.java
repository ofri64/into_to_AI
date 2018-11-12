import java.util.Comparator;

/**
 * A class representing a comperator for the A* algorithms nodes
 * Ranking the nodes as specified in the instruction - evaluation function, depth in the graph,
 * and lastly the move that created it (using moves natural order)
 */
public class AStarComperator implements Comparator<AStarManhattanDistanceNode> {

    @Override
    public int compare(AStarManhattanDistanceNode node_1, AStarManhattanDistanceNode node_2) {
        int node_1Score = node_1.getEvaluationFunctionScore();
        int node_2Score = node_2.getEvaluationFunctionScore();

        // in case of evaluation function equivalence check the node's depth
        if (node_1Score == node_2Score){
            int node1_Depth = node_1.getDepth();
            int node2_Depth = node_2.getDepth();

            // in case of equivalence also with the node's depth - check the creation move
            if (node1_Depth == node2_Depth){
                Move node_1CreationMove = node_1.getCreationMove();
                Move node_2CreationMove = node_2.getCreationMove();
                int node_1_move_index = -1;
                int node_2_move_index = -1;
                Move[] ordered_moves = Move.values();

                // the following piece of code essentially assigns integer values to Move Enums
                // matching their order - {UP:0, DOWN:1, LEFT:2, RIGHT:3}
                for (int i=0; i < ordered_moves.length; i++){
                    Move checked_move = ordered_moves[i];
                    if (node_1CreationMove == checked_move){
                        node_1_move_index = i;
                    }
                    if (node_2CreationMove == checked_move){
                        node_2_move_index = i;
                    }
                }

                // finally decided given the index of the creation moves
                return node_1_move_index - node_2_move_index;

            }
            else {
                return node1_Depth - node2_Depth; // positive value if node1_Depth > node2_Depth
            }
        }

        else {
            return node_1Score - node_2Score; // positive value if node_1Score > node_2Score
        }
    }
}
