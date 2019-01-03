import java.util.*;

public class NaiveBayes<E> extends AbstractClassifier<E> {
    public double lambda;
    private int numTotalSample = 0;
    private Map<E, Integer> labelsPriorCounts = new HashMap<>();
    private Map<String, Integer> featuresGivenLabelCounts = new HashMap<>();
    private Map<Integer, Integer> featuresNumOfUniqueValues = new HashMap<>();

    public NaiveBayes(double lambda){
        this.lambda = lambda;
    }

    public NaiveBayes(){
        this(1);
    }

    @Override
    public void fit(DataFrameInterface<E> df) {
        this.initiateFeaturesAndLabelsValues(df);
        int numFeatures = df.getNumCols() - 1;

        // Compute labels prior Counter and number of total samples
        SeriesInterface<E> labelsColumn = df.getCol(numFeatures);
        this.labelsPriorCounts = labelsColumn.getValueCounts();
        this.numTotalSample = df.getNumRows();

        // Compute features unique values
        for (int i = 0; i < numFeatures; i++) {
            Set<E> featureUniqueValues = df.getCol(i).getUniqueValues();
            featuresNumOfUniqueValues.put(i, featureUniqueValues.size());
        }

        // Compute features given labels probabilities
        for (int j = 0; j < numFeatures; j++) {
            SeriesInterface<E> featureColumn = df.getCol(j);

            for (int i = 0; i < this.numTotalSample; i++) {

                E featureValue = featureColumn.getElement(i);
                E labelValue = labelsColumn.getElement(i);
                String key = j + "|" + featureValue.toString() + "|" + labelValue.toString();
                int keyCurrentCount = this.featuresGivenLabelCounts.getOrDefault(key, 0);
                this.featuresGivenLabelCounts.put(key, keyCurrentCount + 1);
            }
        }
    }

    @Override
    public SeriesInterface<E> predict(DataFrameInterface<E> df) {
        List<E> predictions = new LinkedList<>();

        for (SeriesInterface<E> row: df) {

            SeriesInterface<E> rowFeatures = row.getSlice(0, row.getLength() - 1);
            Map<E, Double> rowLabelsProb = new HashMap<>();

            for (int i=0; i < rowFeatures.getLength(); i++){
                E featureValue = rowFeatures.getElement(i);

                for (E label: this.uniqueLabels){
                    double currentProb = rowLabelsProb.getOrDefault(label, 1.0);

                    double ProbValueForGivenLabel = this.predictProbForValueGivenLabel(i, featureValue, label);
                    rowLabelsProb.put(label, ProbValueForGivenLabel * currentProb);
                }
            }

            // multiply also with prior probabilities
            for (E label: rowLabelsProb.keySet()){
                double currentLabelProb = rowLabelsProb.getOrDefault(label, 1.0);

                double priorProb = this.predictPriorProbForLabel(label);
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

    private double predictPriorProbForLabel(E label){
        return (double) this.labelsPriorCounts.get(label) / numTotalSample;
    }

    private double predictProbForValueGivenLabel(int featureIndex, E featureValue, E givenLabel){
        int featureNumUniqueValues = this.featuresNumOfUniqueValues.get(featureIndex);
        String key = featureIndex + "|" + featureValue.toString() + "|" + givenLabel.toString();
        int featureGivenValueCount = this.featuresGivenLabelCounts.getOrDefault(key, 0);
        int labelPriorCount = this.labelsPriorCounts.get(givenLabel);

        return (featureGivenValueCount + this.lambda) / (labelPriorCount + featureNumUniqueValues * this.lambda);
    }
}
