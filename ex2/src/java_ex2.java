import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class java_ex2 {
    public static String INPUT_TRAIN = "files/train.txt";
    public static String INPUT_TEST = "files/test.txt";
    public static String OUTPUT_FILE = "files/output.txt";
    public static String OUTPUT_TREE = "files/output_tree.txt";

    public static void main(String[] args) {
        // read training and test data from files
        DataFrameInterface<String> trainDf = new DataFrame<>(INPUT_TRAIN);
        DataFrameInterface<String> testDf = new DataFrame<>(INPUT_TEST);

        // Run classifiers
        Classifier<String> KNN = new KNN<>(5);
        KNN.fit(trainDf);
        SeriesInterface<String> knnPredict = KNN.predict(testDf);

        Classifier<String> bayes = new NaiveBayes<>();
        bayes.fit(trainDf);
        SeriesInterface<String> naiveBayesPredict = bayes.predict(testDf);

        Classifier<String> decisionTree = new DecisionTreeClassifier<>();
        decisionTree.fit(trainDf);
        SeriesInterface<String> decisionTreePredict = decisionTree.predict(testDf);

        // Write decision tree structure to file
        String treeOutput = ((DecisionTreeClassifier<String>) decisionTree).outputTree();
        writeOutputToFile(OUTPUT_TREE, treeOutput);

        // Write results to file
        List<SeriesInterface<String>> predictions = new LinkedList<>(Arrays.asList(decisionTreePredict, knnPredict, naiveBayesPredict));
        DataFrameInterface<String> resultsDf = createResultsDataFrame(predictions, knnPredict.getLength());

        // add header line
        List<String> header = new LinkedList<>(Arrays.asList("Num", "DT", "KNN", "naiveBase"));
        resultsDf.setHeaderRow(new Series<>(header));
        String dfPrint = resultsDf.printDataFrame();

        double[] accuracies = {decisionTree.getAccuracy(testDf), KNN.getAccuracy(testDf), bayes.getAccuracy(testDf)};
        dfPrint += "\n" + printFormattedAccuracy(accuracies);

        writeOutputToFile(OUTPUT_FILE, dfPrint);
    }

    private static String printFormattedAccuracy(double[] accuracies){
        StringBuilder res = new StringBuilder();
        DecimalFormat f = new DecimalFormat("#.##");
        for (Double accuracy: accuracies){
            res.append("\t");
            res.append(f.format(accuracy));
        }
        return res.toString();
    }

    private static DataFrameInterface<String> createResultsDataFrame(List<SeriesInterface<String>> results, int numSamples){
        List<SeriesInterface<String>> df = new LinkedList<>();
        for (int i=0; i < numSamples; i++){
            List <String> row = new LinkedList<>();
            Integer i_output = i+1;
            row.add(i_output.toString());

            for (SeriesInterface<String> classifierPredictions: results){
                row.add(classifierPredictions.getElement(i));
            }

            df.add(new Series<>(row));
        }

        return new DataFrame<>(df);
    }

    private static boolean writeOutputToFile(String outputFilePath, String output){
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
