import java.util.*;

public class NaiveBayes<E> extends AbstractClassifier<E> {
    private Map<E, Double> labelsPriorProb = new HashMap<>();
    private Map<String, Double> featuresGivenLabelsProb = new HashMap<>();

    @Override
    public void fit(DataFrameInterface<E> df) {
        this.initiateFeaturesAndLabelsValues(df);
        int numSamplesInDataFrame = df.getNumRows();
        int numFeatures = df.getNumCols() - 1;
        SeriesInterface<E> labelsColumn = df.getCol(numFeatures);

        // Compute prior probabilities
        Map<E, Integer> labelsCounts = labelsColumn.getValueCounts();

        for (E label: this.uniqueLabels){
            double labelPriorProb = (double) labelsCounts.get(label) / numSamplesInDataFrame;
            this.labelsPriorProb.put(label, labelPriorProb);
        }

        // Compute features given labels probabilities
        Map<String, Integer> featuresAndLabelsCounts = new HashMap<>();

        for (int j=0; j < numFeatures; j++){
            SeriesInterface<E> featureColumn = df.getCol(j);

            for (int i=0; i < numSamplesInDataFrame; i++){

                E featureValue = featureColumn.getElement(i);
                E labelValue = labelsColumn.getElement(i);
                String key = featureValue.toString() + "|" + labelValue.toString();
                int keyCurrentCount = featuresAndLabelsCounts.getOrDefault(key, 0);
                featuresAndLabelsCounts.put(key, keyCurrentCount + 1);
            }
        }

        // Compute probabilities from counts
        for (String key: featuresAndLabelsCounts.keySet()){
            E labelFromKey = (E) key.split("\\|")[1];

            int totalLabelCount = labelsCounts.get(labelFromKey);
            int featureLabelCount = featuresAndLabelsCounts.get(key);
            double featureGivenLabelProb = (double) featureLabelCount / totalLabelCount;

            featuresGivenLabelsProb.put(key, featureGivenLabelProb);
        }
    }

    @Override
    public SeriesInterface<E> predict(DataFrameInterface<E> df) {
        List<E> predictions = new LinkedList<>();

        for (SeriesInterface<E> row: df) {
            SeriesInterface<E> rowFeatures = row.getSlice(0, row.getLength() - 1);
            Map<E, Double> rowLabelsProb = new HashMap<>();

            for (E value: rowFeatures){

                for (E label: this.uniqueLabels){
                    double currentLabelProb = rowLabelsProb.getOrDefault(label, 1.0);
                    String key = value.toString() + "|" + label.toString();
                    double featureLabelProb = this.featuresGivenLabelsProb.get(key);

                    rowLabelsProb.put(label, currentLabelProb * featureLabelProb);
                }
            }

            // multiply also with prior probabilities
            for (E label: rowLabelsProb.keySet()){
                double currentLabelProb = rowLabelsProb.getOrDefault(label, 1.0);
                double priorProb = this.labelsPriorProb.get(label);
                rowLabelsProb.put(label, currentLabelProb * priorProb);
            }

            // find max label with max probability
            E labelWithMaxProb = null;
            double maxProb = 0;
            for (E label: rowLabelsProb.keySet()){
                double labelProb = rowLabelsProb.get(label);
                if (labelProb > maxProb){
                    labelWithMaxProb = label;
                    maxProb = labelProb;
                }
            }

            // add to predictions max prob label
            predictions.add(labelWithMaxProb);
        }
        this.predictions = new Series<>(predictions);
        return this.predictions;
    }

    private int countNumSampleWithLabel(SeriesInterface<E> labelsSeries, E label){
        int numSampleWithLabel = 0;
        for (E sampleLabel: labelsSeries){
            if (sampleLabel.equals(label)){
                numSampleWithLabel++;
            }
        }
        return numSampleWithLabel;
    }
}
