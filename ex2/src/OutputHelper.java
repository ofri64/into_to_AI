import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

public class OutputHelper {
    public static String printFormattedAccuracy(double[] accuracies){
        StringBuilder res = new StringBuilder();
        for (Double accuracy: accuracies){
            res.append("\t");
            double roundedAccuracy = Math.ceil(accuracy * 100.0) / 100.0;
            res.append(roundedAccuracy);
        }
        return res.toString();
    }

    public static DataFrameInterface createResultsDataFrame(List<SeriesInterface> results, int numSamples){
        List<SeriesInterface> df = new LinkedList<>();
        for (int i=0; i < numSamples; i++){
            List <String> row = new LinkedList<>();
            Integer i_output = i+1;
            row.add(i_output.toString());

            for (SeriesInterface classifierPredictions: results){
                row.add(classifierPredictions.getElement(i));
            }

            df.add(new Series(row));
        }

        return new DataFrame(df);
    }

    public static boolean writeOutputToFile(String outputFilePath, String output){
        try {
            PrintWriter writer = new PrintWriter(outputFilePath, "UTF-8");
            writer.print(output);
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
