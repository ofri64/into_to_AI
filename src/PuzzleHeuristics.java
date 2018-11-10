public class PuzzleHeuristics {

    public static int ManhattanDistanceScore(StateNodeInterface currentState){
        int totalDistance = 0;
        GameBoardInterface gameBoard = currentState.getGameBoard();
        int[] gameRawArray = gameBoard.getGameBoardArray();

        int arrayLength = gameRawArray.length;
        int n = (int) (Math.sqrt(arrayLength));

        for (int i=0; i < arrayLength; i++){
            int currentPosition = i;
            int goalPosition = PuzzleHeuristics.getNumberGoalPosition(arrayLength, gameRawArray[i]);
            totalDistance += getDistanceForPosition(n, currentPosition, goalPosition);
        }

        return totalDistance;
    }

    private static int getNumberGoalPosition(int arrayLength, int numberToCheck){
        if (numberToCheck == 0){
            return arrayLength-1;
        }
        else {
            return numberToCheck - 1;
        }
    }

    private static int getDistanceForPosition(int n, int currentPosition, int goalPosition){
        int lineDistance = Math.abs(goalPosition - currentPosition);
        int rowDistance = lineDistance / n;
        int colDistance = lineDistance % n;
        return rowDistance + colDistance;
    }
}
