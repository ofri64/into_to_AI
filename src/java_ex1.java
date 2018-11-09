public class java_ex1 {
    public static void main(String[] args) {
        int n = 3;
        int[] initialArray = {1, 0, 3, 4, 2, 5, 7, 8, 6};
        GameBoardInterface initialBoard = new GameBoard(n, initialArray);
        StateNodeInterface initalState = new StateNode(null, null, initialBoard, 1);
        SearchAlgorithmInterface algorithm = new BreadthFirstSearch();
        boolean solution = algorithm.performSearch(initalState);
        if (solution){
            System.out.println(algorithm.getSolutionPath() + " " + algorithm.getNumSearchedNodes() + " " + algorithm.getSolutionCost());
        }

        n = 3;
        int[] initialArray2 = {1, 0, 3, 4, 2, 6, 7, 5, 8};
        initialBoard = new GameBoard(n, initialArray2);
        initalState = new StateNode(null, null, initialBoard, 1);
        algorithm = new IterativeDeepingSearch();
        solution = algorithm.performSearch(initalState);
        if (solution){
            System.out.println(algorithm.getSolutionPath() + " " + algorithm.getNumSearchedNodes() + " " + algorithm.getSolutionCost());
        }
    }
}
