import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SeriesInterface<E> extends Iterable<E>{
    int getLength();
    E getElement(int position);
    List<E> getElement(int from, int to);
    List<Integer> compare(SeriesInterface<E> otherSeries);
    SeriesInterface<E> getSlice(int from, int to);
    Set<E> getUniqueValues();
    Map<E, Integer> getValueCounts();
    SeriesInterface<E> filterByValues(E value);
    String printSeries();
}

