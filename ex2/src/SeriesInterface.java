import java.util.List;

public interface SeriesInterface<E> extends Iterable<E>{
    int getLength();
    E getElement(int position);
    List<E> getElement(int from, int to);
    List<Integer> compare(SeriesInterface<E> otherSeries);
    SeriesInterface<E> getSlice(int from, int to);
}

