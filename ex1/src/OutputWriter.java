import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Output writer class, responsible for writing the algorithm output to a file.
 * The output file format matches the one specified in the exercise instructions.
 */
public class OutputWriter {
    private String filePath;

    public OutputWriter(String filePath){
        this.filePath = filePath;
    }

    /**
     *
     * @param algorithmOutput output String to write
     * @return true if writing was successful, false otherwise
     */
    public boolean writeOutput(String algorithmOutput){
        try {
            PrintWriter writer = new PrintWriter(this.filePath, "UTF-8");
            writer.print(algorithmOutput);
            writer.close();
        }
        catch (FileNotFoundException fnf){ // handle exception in file path
            System.out.println("Problem with output file: ");
            System.out.println(fnf.getMessage());
            return false;
        }
        catch (UnsupportedEncodingException ue){ // handle exception in data to write format
            System.out.println("Problem parsing output data as UTF-8");
            System.out.println(ue.getMessage());
            return false;
        }
        return true;
    }
}
