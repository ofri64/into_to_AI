import java.util.HashSet;
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
        return null;
    }
}
