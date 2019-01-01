import java.util.Collections;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractClassifier<E> implements Classifier<E>{
    protected Set<E> uniqueLabels;
    protected Map<Integer, Set<E>> featuresUniqueValues;
    protected SeriesInterface<E> predictions;

    protected E getKeyForMaxValue(Map<E, Integer> countsMap){
        int maxValueInMap = Collections.max(countsMap.values());
        for (Map.Entry<E, Integer> mapEntry: countsMap.entrySet()){
            if (mapEntry.getValue() == maxValueInMap){
                return mapEntry.getKey();
            }
        }
        // should not happen!
        return null;
    }

    protected void initiateFeaturesAndLabelsValues(DataFrameInterface<E> df){
        int dfNumCols = df.getNumCols();
        SeriesInterface<E> labels = df.getCol(dfNumCols - 1);
        this.uniqueLabels = this.seriesUniqueValues(labels);

        this.featuresUniqueValues = new HashMap<>();
        for (int i = 0; i < dfNumCols - 1; i++){
            SeriesInterface<E> featureColumn = df.getCol(i);
            Set<E> featureUniqueValues = this.seriesUniqueValues(featureColumn);

            this.featuresUniqueValues.put(i, featureUniqueValues);
        }
    }

    private Set<E> seriesUniqueValues(SeriesInterface<E> series){
        Set<E> uniqueValues = new HashSet<>();
        for (E value: series){
            uniqueValues.add(value);
        }
        return uniqueValues;
    }

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
