public class java_ex1 {
    public static void main(String[] args) {
        int n = 3;
        int[] initialState = {1, 0, 3, 4, 2, 5, 7, 8, 6};
        GameBoardInterface game = new GameBoard(n, initialState);
        System.out.println("U move is valid? " + game.isValidMove(Move.UP));
        System.out.println("D move is valid? " + game.isValidMove(Move.DOWN));
        System.out.println("L move is valid? " + game.isValidMove(Move.LEFT));
        System.out.println("R move is valid? " + game.isValidMove(Move.RIGHT));
        System.out.println(game.isGoalState());
        game = game.performMove(Move.UP);
        System.out.println("U move is valid?" + game.isValidMove(Move.UP));
        game = game.performMove(Move.LEFT);
        game = game.performMove(Move.UP);
        System.out.println(game.isGoalState());
    }
}
