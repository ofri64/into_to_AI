public class java_ex2 {
    public static String INPUT_TRAIN = "files/train.txt";
    public static String INPUT_TEST = "files/test.txt";
    public static String OUTPUT_FILE = "files/my_output.txt";

    public static void main(String[] args) {
        DataFrameInterface<String> trainDf = new DataFrame<>(INPUT_TRAIN);
        DataFrameInterface<String> testDf = new DataFrame<>(INPUT_TEST);
        Classifier<String> KNN = new KNN<>(5);
        KNN.fit(trainDf);
        KNN.predict(testDf);
        System.out.println(KNN.getAccuracy(testDf));

        Classifier<String> bayes = new NaiveBayes<>();
        bayes.fit(trainDf);
        bayes.predict(testDf);
        System.out.println(bayes.getAccuracy(testDf));

    }
}


//        System.out.println(df.getNumRows());
//        System.out.println(df.getNumCols());
//        for (Series<String> row: df){
//            for (String element: row){
//                System.out.println(element);

