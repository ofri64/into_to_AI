import java.util.*;

public class Series<E> implements SeriesInterface<E> {
    protected List<E> series;

    public Series(E[] elements) {
        this.series = new LinkedList<>();
        this.series.addAll(Arrays.asList(elements));
    }

    public Series(List<E> elements) {
        this.series = new LinkedList<>();
        this.series.addAll(elements);
    }

    public Series(Series<E> s) {
        this(s.series);
    }

    @Override
    public E getElement(int position) {
        return this.series.get(position);
    }

    @Override
    public int getLength() {
        return this.series.size();
    }

    @Override
    public List<E> getElement(int from, int to) {
        return this.series.subList(from, to);
    }

    @Override
    public SeriesInterface<E> getSlice(int from, int to) {
        return new Series<>(this.getElement(from, to));
    }

    @Override
    public Iterator<E> iterator() {
        return new SeriesIterator<>(this);
    }

    @Override
    public List<Integer> compare(SeriesInterface<E> otherSeries) {
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
    public Set<E> getUniqueValues() {
        Set<E> uniqueValues = new HashSet<>();
        for (E value : this.series) {
            uniqueValues.add(value);
        }
        return uniqueValues;
    }

    @Override
    public Map<E, Integer> getValueCounts() {
        Map<E, Integer> labelsCount = new HashMap<>();

        for (E label: this.series){
            Integer labelCountCurrentValue = labelsCount.getOrDefault(label, 0);
            labelsCount.put(label, labelCountCurrentValue + 1);
        }
        return labelsCount;
    }
}
