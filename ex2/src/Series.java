import java.util.*;

public class Series implements SeriesInterface {
    protected List<String> series;

    public Series(String[] elements) {
        this.series = new LinkedList<>();
        this.series.addAll(Arrays.asList(elements));
    }

    public Series(List<String> elements) {
        this.series = new LinkedList<>();
        this.series.addAll(elements);
    }

    public Series(SeriesInterface s) {
        this.series = new LinkedList<>(s.getElement(0 ,s.getLength()));
    }

    @Override
    public String getElement(int position) {
        return this.series.get(position);
    }

    @Override
    public int getLength() {
        return this.series.size();
    }

    @Override
    public List<String> getElement(int from, int to) {
        return this.series.subList(from, to);
    }

    @Override
    public SeriesInterface getSlice(int from, int to) {
        return new Series(this.getElement(from, to));
    }

    @Override
    public Iterator<String> iterator() {
        return new SeriesIterator(this);
    }

    @Override
    public List<Integer> compare(SeriesInterface otherSeries) {
        List<Integer> comparison = new LinkedList<>();
        for (int i = 0; i < this.getLength(); i++) {
            if (otherSeries.getElement(i).equals(this.getElement(i))) {
                comparison.add(0);
            } else {
                comparison.add(1);
            }
        }
        return comparison;
    }

    @Override
    public Set<String> getUniqueValues() {
        Set<String> uniqueValues = new HashSet<>();
        for (String value : this.series) {
            uniqueValues.add(value);
        }
        return uniqueValues;
    }

    @Override
    public Map<String, Integer> getValueCounts() {
        Map<String, Integer> labelsCount = new HashMap<>();

        for (String label: this.series){
            Integer labelCountCurrentValue = labelsCount.getOrDefault(label, 0);
            labelsCount.put(label, labelCountCurrentValue + 1);
        }
        return labelsCount;
    }

    @Override
    public SeriesInterface filterByValues(String value) {
        List<String> newSeries = new LinkedList<>();
        for (String currentValue: this.series){
            if (currentValue.equals(value)){
                newSeries.add(currentValue);
            }
        }
        return new Series(newSeries);
    }

    @Override
    public String printSeries() {
        StringBuilder print = new StringBuilder();

        for (String value : this.series) {
            print.append(value);
            print.append("\t");
        }

        // delete last tab and add end of line char
        print.deleteCharAt(print.length() - 1);
        print.append("\n");
        return print.toString();
    }
}
