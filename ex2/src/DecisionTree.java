import java.util.List;
import java.util.Map;
import java.util.Set;

public class DecisionTree<E>{
    public DecisionTreeNode<E> root;

    public static <T> double getLabelsEntorpy(SeriesInterface<T> labelColumn){
        double entropy = 0;
        int numSamples = labelColumn.getLength();
        Map<T, Integer> labelsCount = labelColumn.getValueCounts();
        for (T label: labelsCount.keySet()){
            int labelCount = labelsCount.get(label);
            double labelProb = (double) labelCount / numSamples;
            entropy += -1 * labelProb * (Math.log(labelProb) / Math.log(2.0));
        }
        return entropy;
    }


    public class DecisionTreeNode<E>{
        private int attributeIndex;
        private E attributeValue;
        private boolean isLeaf;
        private E prediction;
        private List<DecisionTreeNode<E>> childs;

        public DecisionTreeNode(int attributeIndex, E attributeValue, DataFrameInterface<E> df){
            this.attributeIndex = attributeIndex;
            this.attributeValue = attributeValue;
            this.isLeaf = this.checkIfLeafNode(df);

            if (this.isLeaf){
                this.prediction = this.getNodePrediction(df);
            }

        }

        private boolean checkIfLeafNode(DataFrameInterface<E> df){
            if (df.getNumRows() == 0){
                return true;
            }

            SeriesInterface<E> labelsColumn = df.getCol(df.getNumCols() - 1);
            Set<E> uniqueLabels = labelsColumn.getUniqueValues();

            if (uniqueLabels.size() == 1){
                return true;
            }

            return false;
        }

        private E getNodePrediction(DataFrameInterface<E> df){
            SeriesInterface<E> labelsColumn = df.getCol(df.getNumCols() - 1);
            Map<E, Integer> labelsCount = labelsColumn.getValueCounts();

            return AbstractClassifier.getKeyForMaxValue(labelsCount);
        }
    }

}
