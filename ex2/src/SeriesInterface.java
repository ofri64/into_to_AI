import java.util.List;

public interface SeriesInterface<E> {
    int getLength();
    E getElement(int position);
    List<E> getElement(int from, int to);
}

