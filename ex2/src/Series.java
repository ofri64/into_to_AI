import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Series<E> implements SeriesInterface<E> {
    protected List<E> series;

    public Series(E[] elements){
        this.series = new LinkedList<>();
        this.series.addAll(Arrays.asList(elements));
    }

    public Series(List<E> elements){
        this.series = new LinkedList<>();
        this.series.addAll(elements);
    }

    public Series(Series<E> s){
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

}
