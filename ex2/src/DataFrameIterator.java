import java.util.Iterator;

public class DataFrameIterator implements Iterator<SeriesInterface> {
    private DataFrameInterface rows;
    private int currentRow = 0;
    private int numberOfRows;

    public DataFrameIterator(DataFrameInterface df){
        this.numberOfRows = df.getNumRows();
        this.rows = df.getSlice(0, this.numberOfRows);
    }

    @Override
    public boolean hasNext() {
        return this.currentRow < this.numberOfRows;
    }

    @Override
    public SeriesInterface next() {
        return rows.getRow(currentRow++);
    }
}
