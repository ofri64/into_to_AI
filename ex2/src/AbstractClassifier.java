import java.util.Collections;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;


public abstract class AbstractClassifier<E> implements Classifier<E>{
    protected Set<E> uniqueLabels;
    protected Map<String, Set<E>> featuresUniqueValues;

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

    protected void initiateFeaturesAndLabelsVales(DataFrameInterface<E> df){
        int dfNumCols = df.getNumCols();
        SeriesInterface<E> labels = df.getCol(dfNumCols - 1);
        this.uniqueLabels = this.seriesUniqueValues(labels);

        this.featuresUniqueValues = new HashMap<>();
        for (int i = 0; i < dfNumCols - 1; i++){
            SeriesInterface<E> featureColumn = df.getCol(i);
            Set<E> featureUniqueValues = this.seriesUniqueValues(featureColumn);

            this.featuresUniqueValues.put(Integer.toString(i), featureUniqueValues);
        }
    }

    private Set<E> seriesUniqueValues(SeriesInterface<E> series){
        Set<E> uniqueValues = new HashSet<>();
        for (E value: series){
            uniqueValues.add(value);
        }
        return uniqueValues;
    }

}
