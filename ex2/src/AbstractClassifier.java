import java.util.Collections;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractClassifier implements Classifier{
    protected Map<Integer, String> featuresIndexToNameMapping;
    protected Set<String> uniqueLabels;
    protected Map<Integer, Set<String>> featuresUniqueValues;
    protected SeriesInterface predictions;

    public static <T> T getKeyForMaxValue(Map<T, Integer> countsMap){
        int maxValueInMap = Collections.max(countsMap.values());
        for (Map.Entry<T, Integer> mapEntry: countsMap.entrySet()){
            if (mapEntry.getValue() == maxValueInMap){
                return mapEntry.getKey();
            }
        }
        // should not happen!
        return null;
    }

    protected void initiateFeaturesAndLabelsValues(DataFrameInterface df){
        int dfNumCols = df.getNumCols();
        SeriesInterface labels = df.getCol(dfNumCols - 1);
        this.uniqueLabels = labels.getUniqueValues();

        this.featuresUniqueValues = new HashMap<>();
        for (int i = 0; i < dfNumCols - 1; i++){
            SeriesInterface featureColumn = df.getCol(i);
            Set<String> featureUniqueValues = featureColumn.getUniqueValues();

            this.featuresUniqueValues.put(i, featureUniqueValues);
        }

        this.featuresIndexToNameMapping = new HashMap<>();
        SeriesInterface header = df.getHeaderLine();
        for (int i=0; i < header.getLength() - 1; i++){
            this.featuresIndexToNameMapping.put(i, header.getElement(i));
        }
    }

    public double getAccuracy(DataFrameInterface df) {
        if (this.predictions == null){
            this.predict(df);
        }
        SeriesInterface dfLabels = df.getCol(df.getNumCols() - 1);
        List<Integer> wrongPredictionVector = this.predictions.compare(dfLabels);
        int numSamples = df.getNumRows();
        int numWrongClassified = 0;
        for (int comparedClassification: wrongPredictionVector){
            numWrongClassified += comparedClassification;
        }
        double errorRate = (double) numWrongClassified / numSamples;
        return 1 - errorRate;
    }
}
