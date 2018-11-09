public class java_ex1 {
    public static void main(String[] args) {
        int n = 3;
        int[] initialArray = {1, 0, 3, 4, 2, 5, 7, 8, 6};
        GameBoardInterface initialBoard = new GameBoard(n, initialArray);
        StateNodeInterface initalState = new StateNode(null, null, initialBoard);
        SearchAlgorithmInterface algorithm = new BreadthFirstSearch();
        boolean solution = algorithm.performSearch(initalState);
        if (solution){
            System.out.println(algorithm.getSolutionPath() + " " + algorithm.getNumSearchedNodes() + " " + algorithm.getSolutionCost());
        }
    }
}
