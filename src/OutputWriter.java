import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class OutputWriter {
    private String filePath;

    public OutputWriter(String filePath){
        this.filePath = filePath;
    }

    public boolean writeOutput(String algorithmOutput){
        try {
            PrintWriter writer = new PrintWriter(this.filePath, "UTF-8");
            writer.print(algorithmOutput);
            writer.close();
        }
        catch (FileNotFoundException fnf){
            System.out.println("Problem with output file: ");
            System.out.println(fnf.getMessage());
            return false;
        }
        catch (UnsupportedEncodingException ue){
            System.out.println("Problem parsing output data as UTF-8");
            System.out.println(ue.getMessage());
            return false;
        }
        return true;
    }
}
