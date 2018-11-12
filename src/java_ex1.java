public class java_ex1 {

    public static void main(String[] args) {

        String inputFilePath = args[0];
        String outputFilePath = args[1];
        InputParser parser = new InputParser(inputFilePath);
        parser.parseInputFile();

        int algoType = parser.getAlgorithmType();
        int n = parser.getBoardSize();
        int[] initialArray = parser.getInitialState();

        SearchAlgorithmInterface algorithm = AlgorithmsFactory.runSearchAlgorithm(n, initialArray, algoType);
        String algorithmOutput = algorithm.getSolutionPath() + " " + algorithm.getNumSearchedNodes() + " " + algorithm.getSolutionCost();
        OutputWriter outputWriter = new OutputWriter(outputFilePath);
        outputWriter.writeOutput(algorithmOutput);
    }

}
