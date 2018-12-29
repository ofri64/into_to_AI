public class java_ex2 {
    public static String INPUT_TRAIN = "train.txt";
    public static String INPUT_TEST = "test.txt";
    public static String OUTPUT_FILE = "my_output.txt";

    public static void main(String[] args) {
        DataFrameInterface<String> df = new DataFrame<>(INPUT_TRAIN);
        System.out.println(df.getNumRows());
        System.out.println(df.getNumCols());
    }
}
