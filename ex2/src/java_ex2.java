import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class java_ex2 {
    public static String INPUT_TRAIN = "train.txt";
    public static String INPUT_TEST = "test.txt";
    public static String OUTPUT_FILE = "output.txt";
    public static String OUTPUT_TREE = "output_tree.txt";

    public static void main(String[] args) {
        // read training and test data from files
        DataFrameInterface trainDf = new DataFrame(INPUT_TRAIN);
        DataFrameInterface testDf = new DataFrame(INPUT_TEST);

        // Run classifiers
        Classifier KNN = new KNN(5);
        KNN.fit(trainDf);
        SeriesInterface knnPredict = KNN.predict(testDf);

        Classifier bayes = new NaiveBayes();
        bayes.fit(trainDf);
        SeriesInterface naiveBayesPredict = bayes.predict(testDf);

        Classifier decisionTree = new DecisionTreeClassifier();
        decisionTree.fit(trainDf);
        SeriesInterface decisionTreePredict = decisionTree.predict(testDf);

        // Write decision tree structure to file
        String treeOutput = ((DecisionTreeClassifier) decisionTree).outputTree();
        OutputHelper.writeOutputToFile(OUTPUT_TREE, treeOutput);

        // Write results to file
        List<SeriesInterface> predictions = new LinkedList<>(Arrays.asList(decisionTreePredict, knnPredict, naiveBayesPredict));
        DataFrameInterface resultsDf = OutputHelper.createResultsDataFrame(predictions, knnPredict.getLength());

        // add header line
        List<String> header = new LinkedList<>(Arrays.asList("Num", "DT", "KNN", "naiveBase"));
        resultsDf.setHeaderRow(new Series(header));
        String dfPrint = resultsDf.printDataFrame();

        double[] accuracies = {decisionTree.getAccuracy(testDf), KNN.getAccuracy(testDf), bayes.getAccuracy(testDf)};
        dfPrint += "\n" + OutputHelper.printFormattedAccuracy(accuracies);

        OutputHelper.writeOutputToFile(OUTPUT_FILE, dfPrint);
    }
}
