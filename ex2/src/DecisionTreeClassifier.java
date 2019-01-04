import java.util.LinkedList;
import java.util.List;
//
public class DecisionTreeClassifier extends AbstractClassifier {
    private DecisionTree DT;

    @Override
    public void fit(DataFrameInterface df) {
        this.initiateFeaturesAndLabelsValues(df);
        DT = new DecisionTree(df, this.featuresUniqueValues);
    }

    @Override
    public SeriesInterface predict(DataFrameInterface df) {
        List<String> predictions = new LinkedList<>();

        for (SeriesInterface row: df) {
            SeriesInterface rowFeatures = row.getSlice(0, row.getLength() - 1);
            predictions.add(this.DT.predictForDataSample(rowFeatures));
        }

        this.predictions = new Series(predictions);
        return this.predictions;
    }

    public String outputTree(){
        return this.DT.outputTreeRepresentation(this.featuresIndexToNameMapping);
    }
}
