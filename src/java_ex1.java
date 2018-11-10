public class java_ex1 {
    public static void main(String[] args) {
        int n = 3;
        int[] initialArray = {1, 0, 3, 4, 2, 5, 7, 8, 6};
        SearchAlgorithmInterface algorithm = AlgorithmsFactory.runSearchAlgorithm(n, initialArray, 2);
        System.out.println(algorithm.getSolutionPath() + " " + algorithm.getNumSearchedNodes() + " " + algorithm.getSolutionCost());

        int[] initialArray2 = {1, 0, 3, 4, 2, 6, 7, 5, 8};
        algorithm = AlgorithmsFactory.runSearchAlgorithm(n, initialArray2, 1);
        System.out.println(algorithm.getSolutionPath() + " " + algorithm.getNumSearchedNodes() + " " + algorithm.getSolutionCost());
    }
}
