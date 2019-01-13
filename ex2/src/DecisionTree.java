import java.util.*;

public class DecisionTree{
    public DecisionTreeNode root;

    public DecisionTree(DataFrameInterface df, Map<Integer, Set<String>> possibleAttributes){
        this.root = new DecisionTreeNode();
        this.DecisionTreeLearning(this.root, df, possibleAttributes);
    }

    private void DecisionTreeLearning(DecisionTree.DecisionTreeNode node, DataFrameInterface df, Map<Integer, Set<String>> possibleAttributes){
        if (node.checkIfLeafNode(df) || possibleAttributes.isEmpty()){ // stopping criterion
            node.isLeaf = true;

            if (!df.isEmpty()){
                node.prediction = node.getNodePrediction(df);
            }
            else {
                node.prediction = node.defaultPrediction;
            }

        }

        else {

            // Choose best attribute for split
            Map<Integer, Double> attributesInfoGainMap = new HashMap<>();
            double currentEntropy = getLabelsEntropy(df);

            for (int attribute: possibleAttributes.keySet()){
                Set<String> attributeValues = possibleAttributes.get(attribute);
                double attributeGain = this.getAttributeInformationGain(df, attribute, attributeValues, currentEntropy);

                attributesInfoGainMap.put(attribute, attributeGain);
            }

            int bestAttribute = -1;
            double bestInfoGain = -1.0;
            for (int attribute: attributesInfoGainMap.keySet()){
                double attributeGain = attributesInfoGainMap.get(attribute);
                if (attributeGain > bestInfoGain){
                    bestInfoGain = attributeGain;
                    bestAttribute = attribute;
                }
            }

            // split using the best attribute
            Set<String> attributeValues = possibleAttributes.get(bestAttribute);
            for (String value: attributeValues){
                DataFrameInterface splitDf = df.filterRowsByColumnValue(bestAttribute, value);
                Map<Integer, Set<String>> splitPossibleAttributes = new HashMap<>(possibleAttributes);
                splitPossibleAttributes.remove(bestAttribute);
                String childDefaultPrediction = node.getNodePrediction(df);
                DecisionTree.DecisionTreeNode splitNode = new DecisionTreeNode(bestAttribute, value, node.depth + 1, childDefaultPrediction);

                // continue recursively and append branch to tree
                DecisionTreeLearning(splitNode, splitDf, splitPossibleAttributes);
                node.children.add(splitNode);
            }
        }
    }

    private double getAttributeInformationGain(DataFrameInterface df, int attribute, Set<String> attributePossibleValues, double currentEntropy){
        double attributeGain = currentEntropy;
        for (String value: attributePossibleValues){
            DataFrameInterface attributeDf = df.filterRowsByColumnValue(attribute, value);
            if (!attributeDf.isEmpty()){
                double valueEntropy = this.getLabelsEntropy(attributeDf);
                double valueProportion = (double) attributeDf.getNumRows() / df.getNumRows();
                attributeGain -= valueProportion * valueEntropy;
            }
        }
        return attributeGain;
    }

    public String predictForDataSample(SeriesInterface row){
        boolean stopAtCurrentNode = false;
        DecisionTreeNode currentNode = this.root;
        while(!stopAtCurrentNode){

            if (currentNode.isLeaf){
                stopAtCurrentNode = true;
            }

            else {
                boolean traversedToNextNode = false;
                for (DecisionTreeNode childNode: currentNode.children){
                    int childNodeIndex = childNode.attributeIndex;
                    String childNodeValue = childNode.attributeValue;
                    if (row.getElement(childNodeIndex).equals(childNodeValue)){ // continue traversing the tree
                        currentNode = childNode;
                        traversedToNextNode = true;
                        break;
                    }
                }
                if (!traversedToNextNode){
                    stopAtCurrentNode = true;
                }
            }
        }
        return currentNode.prediction;
    }

    private double getLabelsEntropy(DataFrameInterface df){
        SeriesInterface labelColumn = df.getCol(df.getNumCols() - 1);
        double entropy = 0;
        int numSamples = labelColumn.getLength();
        Map<String, Integer> labelsCount = labelColumn.getValueCounts();
        for (String label: labelsCount.keySet()){
            int labelCount = labelsCount.get(label);
            double labelProb = (double) labelCount / numSamples;
            entropy += -1 * labelProb * (Math.log(labelProb) / Math.log(2.0));
        }
        return entropy;
    }

    public String outputTreeRepresentation(Map<Integer, String> featuresIndexToNameMapping){
        StringBuilder treeRepr = new StringBuilder();
        List<DecisionTreeNode> rootChildren = new LinkedList<>(this.root.children);
        this.root.sortTreeNodesListLexi(rootChildren);

        for (DecisionTreeNode child: rootChildren){
            treeRepr.append(child.outputNodeRepresentation(featuresIndexToNameMapping));
        }

        // deleter last \n to avoid empty line at the end of file
        treeRepr.deleteCharAt(treeRepr.length() - 1);
        return treeRepr.toString();
    }


    public class DecisionTreeNode{
        private int attributeIndex;
        private String attributeValue;
        private boolean isLeaf;
        private String prediction;
        private String defaultPrediction;
        private List<DecisionTreeNode> children;
        private int depth;

        DecisionTreeNode(int attributeIndex, String attributeValue, int depth, String defaultPrediction){
            this.attributeIndex = attributeIndex;
            this.attributeValue = attributeValue;
            this.isLeaf = false;
            this.children = new LinkedList<>();
            this.depth = depth;
            this.defaultPrediction = defaultPrediction;
        }

        DecisionTreeNode(){
            this.isLeaf = false;
            this.children = new LinkedList<>();
            this.depth = -1;
        }

        private boolean checkIfLeafNode(DataFrameInterface df){
            if (df.isEmpty()){
                return true;
            }

            SeriesInterface labelsColumn = df.getCol(df.getNumCols() - 1);
            Set<String> uniqueLabels = labelsColumn.getUniqueValues();

            if (uniqueLabels.size() == 1){
                return true;
            }

            return false;
        }

        private String getNodePrediction(DataFrameInterface df){
            SeriesInterface labelsColumn = df.getCol(df.getNumCols() - 1);
            Map<String, Integer> labelsCount = labelsColumn.getValueCounts();

            return AbstractClassifier.getKeyForMaxValue(labelsCount);
        }

        private String outputNodeRepresentation(Map<Integer, String> featuresIndexToNameMapping){
            String attributeName = featuresIndexToNameMapping.get(this.attributeIndex);
            StringBuilder attributeDataPrefix = new StringBuilder();

            for (int i=0; i < Math.max(0, this.depth); i++){
                attributeDataPrefix.append("\t");
            }

            if (this.depth > 0){
                attributeDataPrefix.append("|");
            }

            attributeDataPrefix.append(attributeName);
            attributeDataPrefix.append("=");
            attributeDataPrefix.append(this.attributeValue);

            if (this.isLeaf){
                attributeDataPrefix.append(":");
                attributeDataPrefix.append(this.prediction);
                attributeDataPrefix.append("\n");
                return attributeDataPrefix.toString();
            }

            attributeDataPrefix.append("\n");
            List<DecisionTreeNode> nodesChildren = new LinkedList<>(this.children);
            sortTreeNodesListLexi(nodesChildren);

            for (DecisionTreeNode child: nodesChildren){
                attributeDataPrefix.append(child.outputNodeRepresentation(featuresIndexToNameMapping));
            }

            return attributeDataPrefix.toString();
        }

        private void sortTreeNodesListLexi(List<DecisionTreeNode> nodes){
            nodes.sort((DecisionTreeNode o1, DecisionTreeNode o2) -> o1.attributeValue.compareTo(o2.attributeValue));
        }
    }
}
