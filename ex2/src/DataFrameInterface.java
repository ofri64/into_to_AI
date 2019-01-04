public interface DataFrameInterface<E> extends Iterable<SeriesInterface<E>>{
    int getNumRows();
    int getNumCols();
    E getElement(int rowIndex, int colIndex);
    SeriesInterface<E> getRow(int rowIndex);
    SeriesInterface<E> getCol(int columnIndex);
    DataFrameInterface<E> getSlice(int rowFrom, int rowTo);
    DataFrameInterface<E> filterRowsByColumnValue(int colNum, E value);
    boolean isEmpty();
    SeriesInterface<String> getHeaderLine();
    void setHeaderRow(SeriesInterface<String> headerRow);
    String printDataFrame();
}
