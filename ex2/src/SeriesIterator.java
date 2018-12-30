import java.util.Iterator;
import java.util.List;

public class SeriesIterator<E> implements Iterator<E>{
    private List<E> seriesList;
    private int currentPosition = 0;
    private int seriesLength;

    SeriesIterator(SeriesInterface<E> series){
       this.seriesLength = series.getLength();
        this.seriesList = series.getElement(0, seriesLength);
    }

    @Override
    public boolean hasNext() {
        return this.currentPosition < this.seriesLength;
    }

    @Override
    public E next() {
        return seriesList.get(currentPosition++);
    }
}
