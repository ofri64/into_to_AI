public enum Move {
    UP("U"),
    DOWN("D"),
    LEFT("L"),
    RIGHT("R");

    private final String name;

    private Move(String name){
        this.name = name;
    }

    public String getMove(){
        return this.name;
    }
}
