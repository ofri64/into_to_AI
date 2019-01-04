import java.util.Iterator;
import java.util.List;

public class SeriesIterator implements Iterator<String>{
    private List<String> seriesList;
    private int currentPosition = 0;
    private int seriesLength;

    SeriesIterator(SeriesInterface series){
       this.seriesLength = series.getLength();
        this.seriesList = series.getElement(0, seriesLength);
    }

    @Override
    public boolean hasNext() {
        return this.currentPosition < this.seriesLength;
    }

    @Override
    public String next() {
        return seriesList.get(currentPosition++);
    }
}
