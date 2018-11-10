public class java_ex1 {
    public static void main(String[] args) {
//        String inputFilePath = args[0];
//        InputParser parser = new InputParser(inputFilePath);
//        parser.parseInputFile();
//
//        int algoType = parser.getAlgorithmType();
//        int n = parser.getBoardSize();
//        int[] initialArray = parser.getInitialState();
//
//        SearchAlgorithmInterface algorithm = AlgorithmsFactory.runSearchAlgorithm(n, initialArray, algoType);
//        System.out.println(algorithm.getSolutionPath() + " " + algorithm.getNumSearchedNodes() + " " + algorithm.getSolutionCost());
//
//        int[] initialArray2 = {1, 0, 3, 4, 2, 6, 7, 5, 8};
//        algorithm = AlgorithmsFactory.runSearchAlgorithm(n, initialArray2, 1);
//        System.out.println(algorithm.getSolutionPath() + " " + algorithm.getNumSearchedNodes() + " " + algorithm.getSolutionCost());

        int n = 3;
        int[] initialArray = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        int[] initialArray2 = {1, 0, 3, 4, 2, 6, 7, 5, 8};
        int score1 = PuzzleHeuristics.ManhattanDistanceScore(new BFSNode(null, null, new GameBoard(n, initialArray)));
        int score2 = PuzzleHeuristics.ManhattanDistanceScore(new BFSNode(null, null, new GameBoard(n, initialArray2)));
        System.out.println(score1);
        System.out.println(score2);
    }
}
