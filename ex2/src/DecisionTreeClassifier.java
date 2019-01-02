import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
//
public class DecisionTreeClassifier<E> extends AbstractClassifier<E> {
    private DecisionTree<E> DT;

    @Override
    public void fit(DataFrameInterface<E> df) {
        this.initiateFeaturesAndLabelsValues(df);
        DT = new DecisionTree<>(df, this.featuresUniqueValues);
    }

    @Override
    public SeriesInterface<E> predict(DataFrameInterface<E> df) {
        List<E> predictions = new LinkedList<>();

        for (SeriesInterface<E> row: df) {
            SeriesInterface<E> rowFeatures = row.getSlice(0, row.getLength() - 1);
            predictions.add(this.DT.predictForDataSample(rowFeatures));
        }

        this.predictions = new Series<>(predictions);
        return this.predictions;
    }
}
