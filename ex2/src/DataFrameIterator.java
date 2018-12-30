import java.util.Iterator;

public class DataFrameIterator<E> implements Iterator<Series<E>> {
    private DataFrameInterface<E> rows;
    private int currentRow = 0;
    private int numberOfRows;

    public DataFrameIterator(DataFrameInterface<E> df){
        this.numberOfRows = df.getNumRows();
        this.rows = df.getSlice(0, this.numberOfRows);
    }

    @Override
    public boolean hasNext() {
        return this.currentRow < this.numberOfRows;
    }

    @Override
    public Series<E> next() {
        return rows.getRow(currentRow++);
    }
}
