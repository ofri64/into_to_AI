public interface DataFrameInterface extends Iterable<SeriesInterface>{
    int getNumRows();
    int getNumCols();
    String getElement(int rowIndex, int colIndex);
    SeriesInterface getRow(int rowIndex);
    SeriesInterface getCol(int columnIndex);
    DataFrameInterface getSlice(int rowFrom, int rowTo);
    DataFrameInterface filterRowsByColumnValue(int colNum, String value);
    boolean isEmpty();
    SeriesInterface getHeaderLine();
    void setHeaderRow(SeriesInterface headerRow);
    String printDataFrame();
}
