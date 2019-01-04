import java.util.*;

public class NaiveBayes extends AbstractClassifier {
    public double lambda;
    private int numTotalSample = 0;
    private Map<String, Integer> labelsPriorCounts = new HashMap<>();
    private Map<String, Integer> featuresGivenLabelCounts = new HashMap<>();
    private Map<Integer, Integer> featuresNumOfUniqueValues = new HashMap<>();

    public NaiveBayes(double lambda){
        this.lambda = lambda;
    }

    public NaiveBayes(){
        this(1);
    }

    @Override
    public void fit(DataFrameInterface df) {
        this.initiateFeaturesAndLabelsValues(df);
        int numFeatures = df.getNumCols() - 1;

        // Compute labels prior Counter and number of total samples
        SeriesInterface labelsColumn = df.getCol(numFeatures);
        this.labelsPriorCounts = labelsColumn.getValueCounts();
        this.numTotalSample = df.getNumRows();

        // Compute features unique values
        for (int i = 0; i < numFeatures; i++) {
            Set featureUniqueValues = df.getCol(i).getUniqueValues();
            featuresNumOfUniqueValues.put(i, featureUniqueValues.size());
        }

        // Compute features given labels probabilities
        for (int j = 0; j < numFeatures; j++) {
            SeriesInterface featureColumn = df.getCol(j);

            for (int i = 0; i < this.numTotalSample; i++) {

                String featureValue = featureColumn.getElement(i);
                String labelValue = labelsColumn.getElement(i);
                String key = j + "|" + featureValue + "|" + labelValue;
                int keyCurrentCount = this.featuresGivenLabelCounts.getOrDefault(key, 0);
                this.featuresGivenLabelCounts.put(key, keyCurrentCount + 1);
            }
        }
    }

    @Override
    public SeriesInterface predict(DataFrameInterface df) {
        List<String> predictions = new LinkedList<>();

        for (SeriesInterface row: df) {

            SeriesInterface rowFeatures = row.getSlice(0, row.getLength() - 1);
            Map<String, Double> rowLabelsProb = new HashMap<>();

            for (int i=0; i < rowFeatures.getLength(); i++){
                String featureValue = rowFeatures.getElement(i);

                for (String label: this.uniqueLabels){
                    double currentProb = rowLabelsProb.getOrDefault(label, 1.0);

                    double ProbValueForGivenLabel = this.predictProbForValueGivenLabel(i, featureValue, label);
                    rowLabelsProb.put(label, ProbValueForGivenLabel * currentProb);
                }
            }

            // multiply also with prior probabilities
            for (String label: rowLabelsProb.keySet()){
                double currentLabelProb = rowLabelsProb.getOrDefault(label, 1.0);

                double priorProb = this.predictPriorProbForLabel(label);
                rowLabelsProb.put(label, currentLabelProb * priorProb);
            }

            // find max label with max probability
            String labelWithMaxProb = null;
            double maxProb = 0;
            for (String label: rowLabelsProb.keySet()){
                double labelProb = rowLabelsProb.get(label);
                if (labelProb > maxProb){
                    labelWithMaxProb = label;
                    maxProb = labelProb;
                }
            }

            // add to predictions max prob label
            predictions.add(labelWithMaxProb);
        }

        this.predictions = new Series(predictions);
        return this.predictions;
    }

    private double predictPriorProbForLabel(String label){
        return (double) this.labelsPriorCounts.get(label) / numTotalSample;
    }

    private double predictProbForValueGivenLabel(int featureIndex, String featureValue, String givenLabel){
        int featureNumUniqueValues = this.featuresNumOfUniqueValues.get(featureIndex);
        String key = featureIndex + "|" + featureValue.toString() + "|" + givenLabel;
        int featureGivenValueCount = this.featuresGivenLabelCounts.getOrDefault(key, 0);
        int labelPriorCount = this.labelsPriorCounts.get(givenLabel);

        return (featureGivenValueCount + this.lambda) / (labelPriorCount + featureNumUniqueValues * this.lambda);
    }
}
