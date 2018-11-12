import java.util.Comparator;

public class AStarComperator implements Comparator<AStarManhattanDistanceNode> {

    @Override
    public int compare(AStarManhattanDistanceNode node_1, AStarManhattanDistanceNode node_2) {
        int node_1Score = node_1.getEvaluationFunctionScore();
        int node_2Score = node_2.getEvaluationFunctionScore();

        if (node_1Score == node_2Score){
            int node1_Depth = node_1.getDepth();
            int node2_Depth = node_2.getDepth();

            if (node1_Depth == node2_Depth){
                Move node_1CreationMove = node_1.getCreationMove();
                Move node_2CreationMove = node_2.getCreationMove();
                int node_1_move_index = -1;
                int node_2_move_index = -1;
                Move[] ordered_moves = Move.values();

                for (int i=0; i < ordered_moves.length; i++){
                    Move checked_move = ordered_moves[i];
                    if (node_1CreationMove == checked_move){
                        node_1_move_index = i;
                    }
                    if (node_2CreationMove == checked_move){
                        node_2_move_index = i;
                    }
                }

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
