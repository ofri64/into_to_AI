/**
 * Enum type representing possible game moves.
 * The values are ordered by their natural ordered as defines in the exercise description
 */

public enum Move {
    UP("U"),
    DOWN("D"),
    LEFT("L"),
    RIGHT("R");

    private final String name;

    private Move(String name){
        this.name = name;
    }

    /**
     * Getter method for one letter representation of a move, needed for path output description
     * @return one letter representation of a Move
     */
    public String getMove(){
        return this.name;
    }
}
