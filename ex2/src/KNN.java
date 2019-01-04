import java.util.*;

public class KNN extends AbstractClassifier {
    private int k;
    private DataFrameInterface trainingDataFrame;

    class KNNSampleNode {
        private int distanceToPoint;
        private String originalLabel;

        KNNSampleNode(int distanceToPoint, String originalLabel) {
            this.distanceToPoint = distanceToPoint;
            this.originalLabel = originalLabel;

        }

        public String getOriginalLabel() {
            return this.originalLabel;
        }
    }

    class KNNNodeComperator implements Comparator<KNNSampleNode> {
        @Override
        public int compare(KNNSampleNode o1, KNNSampleNode o2) {
            return o1.distanceToPoint - o2.distanceToPoint;
        }
    }

    KNN(int k) {
        this.k = k;
    }

    public int getK() {
        return k;
    }

    @Override
    public void fit(DataFrameInterface df) {
        this.trainingDataFrame = df;
    }

    @Override
    public SeriesInterface predict(DataFrameInterface df) {
        List<String> predictions = new LinkedList<>();
        for (SeriesInterface row : df) {
            SeriesInterface rowFeatures = row.getSlice(0, row.getLength() - 1);

            // init testRowDistances Vector
            List<KNNSampleNode> testRowDistances = new LinkedList<>();
            Map<String, Integer> labelsCounts = new HashMap<>();

            for (SeriesInterface trainingRow : trainingDataFrame) {
                int trainingRowLength = trainingRow.getLength();

                // divide to features and label
                SeriesInterface trainingRowFeatures = trainingRow.getSlice(0, trainingRowLength - 1);
                String trainingRowLabel = trainingRow.getElement(trainingRowLength - 1);

                // compute distance between two data rows
                List<Integer> distanceVector = trainingRowFeatures.compare(rowFeatures);


                // sum number of differences between the test data and the training data row
                int distance = 0;
                for (int featureDistace : distanceVector) {
                    distance += featureDistace;
                }

                // add node to this data row list
                KNNSampleNode node = new KNNSampleNode(distance, trainingRowLabel);
                testRowDistances.add(node);

            }

            // sort the list distance nodes using the predefined comperator object
            testRowDistances.sort(new KNNNodeComperator());

            // count how many accurrences of each class within the first k elements
            for (int i = 0; i < this.k; i++) {
                String label = testRowDistances.get(i).getOriginalLabel();
                Integer currentLabelCount = labelsCounts.get(label);
                if (currentLabelCount != null) {
                    labelsCounts.put(label, currentLabelCount + 1);
                } else {
                    labelsCounts.put(label, 1);
                }
            }
            // get label with maximum counts and add it to predictions list
            predictions.add(KNN.getKeyForMaxValue(labelsCounts));
        }
        this.predictions = new Series(predictions);
        return this.predictions;
    }
}
