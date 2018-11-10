import java.io.BufferedReader;
import java.io.FileReader;

public class InputParser {

    private String inputFilePath;
    private int algorithmType;
    private int boardSize;
    private int[] initialState;

    public InputParser(String inputFilePath){
        this.inputFilePath = inputFilePath;
    }

    public int getAlgorithmType() {
        return algorithmType;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int[] getInitialState() {
        return initialState;
    }

    public void parseInputFile() {
        try(BufferedReader br = new BufferedReader(new FileReader(this.inputFilePath))) {
            this.algorithmType = Integer.parseInt(br.readLine());
            this.boardSize = Integer.parseInt(br.readLine());
            String initialStateRaw = br.readLine();
            String[] stateTokens = initialStateRaw.split("-");
            this.initialState = new int[boardSize*boardSize];

            for(int i=0; i<stateTokens.length; i++){
                this.initialState[i] = Integer.parseInt(stateTokens[i]);
            }
        }
        catch (Exception e){
            System.out.println("The following exception occurred" + e.getMessage());
        }
    }
}