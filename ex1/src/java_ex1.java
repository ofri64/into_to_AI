public class java_ex1 {
    public static String INPUT_FILE = "input.txt";
    public static String OUTPUT_FILE = "output.txt";

    public static void main(String[] args) {

        // parse program arguments to input and output files
        String inputFilePath = INPUT_FILE;
        String outputFilePath = OUTPUT_FILE;

        // parse the input file and extract initial arguments for the specified search algorithm
        InputParser parser = new InputParser(inputFilePath);
        parser.parseInputFile();

        int algoType = parser.getAlgorithmType();
        int n = parser.getBoardSize();
        int[] initialArray = parser.getInitialState();

        // send data parsed from file to the Factory class and run the desired search algorithm
        SearchAlgorithmInterface algorithm = AlgorithmsFactory.runSearchAlgorithm(n, initialArray, algoType);

        // consolidate algorithm output and write it the output file
        String algorithmOutput = algorithm.getSolutionPath() + " " + algorithm.getNumSearchedNodes() + " " + algorithm.getSolutionCost();
        OutputWriter outputWriter = new OutputWriter(outputFilePath);
        outputWriter.writeOutput(algorithmOutput);
    }

}
