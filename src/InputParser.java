import java.io.BufferedReader;
import java.io.FileReader;

/***
 * Input reader class, designed to read input files in the format specified by the exercise instruction
 * and parse it's content into a useful data variables
 */
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

    /***
     * Read the content of the input file
     * update the object data members and allow an outside client to consume them
     * using the defined getters
     *
     * the method assumes the file is in the right format, and terminates the process
     * if it encounters an exception during the read
     */
    public void parseInputFile() {
        try(BufferedReader br = new BufferedReader(new FileReader(this.inputFilePath))) {
            this.algorithmType = Integer.parseInt(br.readLine()); // first line is algorithm type
            this.boardSize = Integer.parseInt(br.readLine()); // second line is the parameter n
            String initialStateRaw = br.readLine();
            String[] stateTokens = initialStateRaw.split("-"); // split board state token
            this.initialState = new int[boardSize*boardSize];

            for(int i=0; i<stateTokens.length; i++){ // parse the string token as integers
                this.initialState[i] = Integer.parseInt(stateTokens[i]);
            }
        }
        catch (Exception e){
            System.out.println("The following exception occurred" + e.getMessage());
            System.exit(-1); // terminate process
        }
    }
}