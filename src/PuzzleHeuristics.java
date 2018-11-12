/**
 * Class defining static function of computing the heuristics needed
 * for informed searching methods
 */

public class PuzzleHeuristics {


    /**
     * Compute the current state score, for the Manhattan distance heuristic
     * @param currentState the state to evaluate it's heuristic score
     * @return h(n) the heuristic score
     */
    public static int ManhattanDistanceScore(StateNodeInterface currentState){
        int totalDistance = 0;
        GameBoardInterface gameBoard = currentState.getGameBoard();
        int[] gameRawArray = gameBoard.getGameBoardArray();

        int arrayLength = gameRawArray.length;
        int n = (int) (Math.sqrt(arrayLength));

        // compute for each item on the board it's distance from it's goal position
        // sum all of them to get the total score for the state
        for (int i=0; i < arrayLength; i++){
            int currentPosition = i;
            int goalPosition = PuzzleHeuristics.getNumberGoalPosition(arrayLength, gameRawArray[i]);
            totalDistance += getDistanceForPosition(n, currentPosition, goalPosition);
        }

        return totalDistance;
    }

    /**
     * Compute what is the goal position of a given value of the board
     * @param arrayLength length of the board data object
     * @param numberToCheck current number to check for poistion
     * @return
     */
    private static int getNumberGoalPosition(int arrayLength, int numberToCheck){
        if (numberToCheck == 0){ // the empty must be last for goal position
            return arrayLength-1;
        }
        else {
            return numberToCheck - 1;
        }
    }

    /**
     * Compute the distance of a given element from it's goal position
     * @param n number of elements in a game board column/row
     * @param currentPosition current position of a given value on the board
     * @param goalPosition the goal position of the same value
     * @return the manhattan distance from current position to the goal for the given value
     */
    private static int getDistanceForPosition(int n, int currentPosition, int goalPosition){
        int lineDistance = Math.abs(goalPosition - currentPosition);
        int rowDistance = lineDistance / n;
        int colDistance = lineDistance % n;
        return rowDistance + colDistance;
    }
}
