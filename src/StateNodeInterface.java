public interface StateNodeInterface {
    StateNodeInterface getFather();
    Move getCreationMove();
    StateNodeInterface createSuccessor(Move move);
    boolean checkIfGoal();
}
