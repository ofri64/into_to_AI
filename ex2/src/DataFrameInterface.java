public interface DataFrameInterface<E> extends Iterable<Series<E>>{
    int getNumRows();
    int getNumCols();
    E getElement(int rowIndex, int colIndex);
    Series<E> getRow(int rowIndex);
    Series<E> getCol(int columnIndex);
    DataFrameInterface<E> getSlice(int rowFrom, int rowTo);
    DataFrameInterface<E> filterRowsByColumnValue(int colNum, E value);
    boolean isEmpty();
}
