import java.util.*;

public class KNN<E> implements Classifier<E> {
    private int k;
    private DataFrameInterface<E> trainingDataFrame;
    private SeriesInterface<E> predictions;

    class KNNSampleNode<T>{
        private int distanceToPoint;
        private T originalLabel;

        KNNSampleNode(int distanceToPoint, T originalLabel){
            this.distanceToPoint = distanceToPoint;
            this.originalLabel = originalLabel;

        }
        public T getOriginalLabel(){
            return this.originalLabel;
        }
    }

    class KNNNodeComperator implements Comparator<KNNSampleNode>{
        @Override
        public int compare(KNNSampleNode o1, KNNSampleNode o2) {
            return o1.distanceToPoint - o2.distanceToPoint;
        }
    }

    public E getKeyForMaxValue(Map<E, Integer> countsMap){
        int maxValueInMap = Collections.max(countsMap.values());
        for (Map.Entry<E, Integer> mapEntry: countsMap.entrySet()){
            if (mapEntry.getValue() == maxValueInMap){
                return mapEntry.getKey();
            }
        }
        // should not happen!
        return null;
    }

    KNN(int k){
        this.k = k;
    }

    public int getK() {
        return k;
    }

    @Override
    public void fit(DataFrameInterface<E> df) {
        this.trainingDataFrame = df;
    }

    @Override
    public SeriesInterface<E> predict(DataFrameInterface<E> df) {
        List<E> predictions = new LinkedList<>();
        for (SeriesInterface<E> row: df){
            SeriesInterface<E> rowFeatures = row.getSlice(0, row.getLength()-1);

            // init testRowDistances Vector
            List<KNNSampleNode<E>> testRowDistances = new LinkedList<>();
            Map<E, Integer> labelsCounts = new HashMap<>();

            for (SeriesInterface<E> trainingRow:trainingDataFrame){
                int trainingRowLength = trainingRow.getLength();

                // divide to features and label
                SeriesInterface<E> trainingRowFeatures = trainingRow.getSlice(0, trainingRowLength-1);
                E trainingRowLabel = trainingRow.getElement(trainingRowLength-1);

                // compute distance between two data rows
                List<Integer> distanceVector = trainingRowFeatures.compare(rowFeatures);


                // sum number of differences between the test data and the training data row
                int distance = 0;
                for (int featureDistace: distanceVector){
                    distance += featureDistace;
                }

                // add node to this data row list
                KNNSampleNode<E> node = new KNNSampleNode<>(distance, trainingRowLabel);
                testRowDistances.add(node);

            }

            // sort the list distance nodes using the predefined comperator object
            Collections.sort(testRowDistances, new KNNNodeComperator());

            // count how many accurrences of each class within the first k elements
            for (int i=0; i < this.k; i++){
                E label = testRowDistances.get(i).getOriginalLabel();
                Integer currentLabelCount = labelsCounts.get(label);
                if (currentLabelCount != null){
                    labelsCounts.put(label, currentLabelCount + 1);
                }
                else {
                    labelsCounts.put(label, 1);
                }
            }
            // get label with maximum counts and add it to predictions list
            predictions.add(this.getKeyForMaxValue(labelsCounts));
        }
        this.predictions = new Series<>(predictions);
        return this.predictions;
    }

    @Override
    public double getAccuracy(DataFrameInterface<E> df) {
        if (this.predictions == null){
            this.predict(df);
        }
        Series<E> dfLabels = df.getCol(df.getNumCols() - 1);
        List<Integer> worngPredictionVector = this.predictions.compare(dfLabels);
        int numSamples = df.getNumRows();
        int numWrongClassified = 0;
        for (int comparedClassificaton: worngPredictionVector){
            numWrongClassified += comparedClassificaton;
        }
        double errorRate = (double) numWrongClassified / numSamples;
        return 1 - errorRate;
    }
}
