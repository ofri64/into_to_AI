import java.util.Comparator;

public class AStarComperator implements Comparator<AStarManhattanDistanceNode> {

    @Override
    public int compare(AStarManhattanDistanceNode node1, AStarManhattanDistanceNode node2) {
        int node1Score = node1.getEvaluationFunctionScore();
        int node2Score = node2.getEvaluationFunctionScore();

        if (node1Score == node2Score){
            int node1Depth = node1.getDepth();
            int node2Depth = node2.getDepth();

            if (node1Depth == node2Depth){
                return -99;
            }
            else {
                return node1Depth - node2Depth; // positive value if node1Depth > node2Depth
            }
        }

        else {
            return node1Score - node2Score; // positive value if node1Score > node2Score
        }
    }
}
