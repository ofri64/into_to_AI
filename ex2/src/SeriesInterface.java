import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SeriesInterface extends Iterable{
    int getLength();
    String getElement(int position);
    List<String> getElement(int from, int to);
    List<Integer> compare(SeriesInterface otherSeries);
    SeriesInterface getSlice(int from, int to);
    Set<String> getUniqueValues();
    Map<String, Integer> getValueCounts();
    SeriesInterface filterByValues(String value);
    String printSeries();
}

